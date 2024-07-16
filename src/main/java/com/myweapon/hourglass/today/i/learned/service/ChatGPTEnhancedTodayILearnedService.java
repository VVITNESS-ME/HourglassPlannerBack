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
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatGPTEnhancedTodayILearnedService implements TodayILearnedService{
    private static String GPT_SYSTEM_MESSAGE =
            "[?] ?은 카테고리, 각 카테고리 별로 기록한 내용을 today i learned 형식으로 종합. 한국어로 응답. 제목과 내용 두가지로 나눠서 응답." +
                    " 제목은 카테고리로 만드는 것이 아니라 모든 내용을 구체적으로 종합. 제목은 처음으로 응답. 제목은 %%% 사이에 있어야 한다." +
                    "예를 들어 %%%제목%%% 내용 형식으로 응답해야함.";
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
    public Boolean updateDocumentContent(DocumentContentDto documentContentDtoUser, LocalDate dateTodo, User user) {
        Optional<TodayILearned> optionalTodayILearned = todayILearnedRepository.findByDateTodo(dateTodo,user);
        if(optionalTodayILearned.isEmpty()){
            DocumentContent documentContent = createDocumentContentFrom(documentContentDtoUser);
            user = entityManager.merge(user);
            TodayILearned todayILearned = TodayILearned.of(documentContent,dateTodo,user);
            todayILearnedRepository.save(todayILearned);
            return true;
        }

        TodayILearned todayILearned = optionalTodayILearned.get();
        todayILearned.setTitle(documentContentDtoUser.getTitle());
        todayILearned.setContent(documentContentDtoUser.getContent());
        return true;
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
