package com.myweapon.hourglass.today.i.learned.service;

import com.myweapon.hourglass.common.exception.RestApiException;
import com.myweapon.hourglass.gpt.dto.ChatMsg;
import com.myweapon.hourglass.gpt.dto.response.ChatResponseDto;
import com.myweapon.hourglass.gpt.service.ChatGPTService;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.security.enumset.ErrorType;
import com.myweapon.hourglass.timer.contants.HourglassConstant;
import com.myweapon.hourglass.timer.service.HourglassService;
import com.myweapon.hourglass.today.i.learned.dto.DocumentContentDto;
import com.myweapon.hourglass.today.i.learned.entity.DocumentContent;
import com.myweapon.hourglass.today.i.learned.entity.TodayILearned;
import com.myweapon.hourglass.today.i.learned.repository.TodayILearnedRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatGPTEnhancedTodayILearnedService implements TodayILearnedService{
    private static String GPT_SYSTEM_MESSAGE =
            "오늘 학습한 내용을 종합해서 \"today i learned\" 형식으로 한국어로 응답해줘. 응답은 제목과 내용 두 가지로 나눠서 작성해야 해. 각각의 항목은 다른 분야의 업무, 작업 또는 학습 내용일 수 있어. 각 항목에 대한 요약을 누락하지 말고 작성해줘.\n" +
                    "\n" +
                    "1. 항목들을 적절한 분류로 묶어줘. 다른 분류로 묶일 수 있는 작업들은 소제목으로 분류해줘. 소제목은 -기호를 사용해.\n" +
                    "2. 각 분류 별로 나중에 읽어도 오늘이 상기될 수 있게 각각의 활동에서 겪었을 것이라 예상되는 어려움과 알게된 사실에대해 최대한 구체적으로 정리해줘.\n" +
                    "3. 전체적인 응답을 요약할 수 있는 대제목을 응답의 최상단에 붙여줘. 대제목은 %%%대제목%%% 형식으로 작성하고, 대제목은 하나만 존재해야 해." +
                    "응답 형식:\n" +
                    "%%%대제목%%%\n" +
                    "- [소제목 1]\n" +
                    "  - 제목: 내용 요약\n" +
                    "  - 제목: 내용 요약\n" +
                    "- [소제목 2]\n" +
                    "  - 제목: 내용 요약\n" +
                    "  - 제목: 내용 요약\n" +
                    "\n" +
                    "이 형식을 유지하며 응답해줘.";
    private static String ENTER_TITLE = "제목을 입력해주세요.";
    private static String ENTER_CONTENT = "내용을 입력해주세요.";

    private final TodayILearnedRepository todayILearnedRepository;
    private final HourglassService hourglassService;
    private final ChatGPTService chatGPTService;
    private final EntityManager entityManager;
    @Override
    public DocumentContentDto getDocumentContent(LocalDate dateTodo,User user) {
        DocumentContent documentContent = todayILearnedRepository
                .findDocumentContentByDateTodo(dateTodo,user)
                .orElseGet(this::createDocumentEmptyContent);
        return createDocumentContentDtoFrom(documentContent);
    }

    public DocumentContentDto getDocumentContentByChatGPT(LocalDate dateTodo,User user){
        List<String> contents = hourglassService.getContentOf(dateTodo,user);
        contents = contents.stream().filter((e)->{
            if (e.equals(HourglassConstant.EMPTY)){
                return false;
            }
            return true;
        }).toList();

        List<ChatMsg> chatMsgList = ChatGPTService.createChatRequestMsgDtoListFrom(contents);
        chatMsgList.add(ChatGPTService.createSystemChatRequestMsgDtoFrom(GPT_SYSTEM_MESSAGE));

        ChatResponseDto result =  chatGPTService.prompt(ChatGPTService.createChatCompletionDtoFrom(chatMsgList));

        return ChatResponseToDocumentContentDto(result);
    }

    @Override
    public DocumentContentDto convertTilWithChatGPT(DocumentContentDto requestContentDto, User user) {
        List<String> contents = List.of(requestContentDto.getContent().split("\n"));
        contents = contents.stream().filter((e)->{
            return !e.equals(HourglassConstant.EMPTY);
        }).toList();
        List<ChatMsg> chatMsgList = ChatGPTService.createChatRequestMsgDtoListFrom(contents);
        chatMsgList.add(ChatGPTService.createSystemChatRequestMsgDtoFrom(GPT_SYSTEM_MESSAGE));

        ChatResponseDto result =  chatGPTService.prompt(ChatGPTService.createChatCompletionDtoFrom(chatMsgList));

        return ChatResponseToDocumentContentDto(result);
    }

    public DocumentContentDto ChatResponseToDocumentContentDto(ChatResponseDto chatResponseDto){
        String gptContent = chatResponseDto.getChoices().get(0).getMessage().getContent();
        System.out.println(gptContent);
        String[] response = gptContent.split("%%%");
        if(response.length<=2){
            throw new RestApiException(ErrorType.GPT_RESPONSE_ERROR);
        }
        return DocumentContentDto.builder().title(response[1]).content(response[2]).build();
    }

    @Override
    @Transactional
    public void updateDocumentContent(DocumentContentDto documentContentDtoUser, LocalDate dateTodo, User user) {
        Optional<TodayILearned> optionalTodayILearned = todayILearnedRepository.findByDateTodo(dateTodo,user);
        if(optionalTodayILearned.isEmpty()){
            DocumentContent documentContent = createDocumentContentFrom(documentContentDtoUser);
            user = entityManager.merge(user);
            TodayILearned todayILearned = TodayILearned.of(documentContent,dateTodo,user);
            todayILearnedRepository.save(todayILearned);
            return;
        }

        TodayILearned todayILearned = optionalTodayILearned.get();
        todayILearned.setTitle(documentContentDtoUser.getTitle());
        todayILearned.setContent(documentContentDtoUser.getContent());
    }

    public DocumentContent createDocumentEmptyContent(){
        return DocumentContent.of(ENTER_TITLE,ENTER_CONTENT);
    }

    public DocumentContent createDocumentContentFrom(DocumentContentDto documentContentDto){
        return DocumentContent.of(documentContentDto.getTitle(), documentContentDto.getContent());
    }

    public DocumentContentDto createDocumentContentDtoFrom(DocumentContent documentContent){
        return DocumentContentDto.builder()
                .content(documentContent.getContent())
                .title(documentContent.getTitle())
                .build();
    }
}
