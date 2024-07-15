package com.myweapon.hourglass.today.i.learned.service;

import com.myweapon.hourglass.gpt.dto.ChatRequestMsgDto;
import com.myweapon.hourglass.gpt.service.ChatGPTService;
import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.statistics.repository.StudyStaticsViewRepository;
import com.myweapon.hourglass.statistics.service.StaticsService;
import com.myweapon.hourglass.timer.respository.UserHourglassRepository;
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
            "[?] ?은 카테고리, 각 카테고리 별로 배운 내용을 today i learned 형식으로 종합" +
                    ", 각 카테고리만 정리하면 되고 모든 카테고리의 내용을 종합하면 안됨. 한국어로 응답";
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
        List<ChatRequestMsgDto> chatRequestMsgDtoList = ChatGPTService.createChatRequestMsgDtoListFrom(contents);
        chatRequestMsgDtoList.add(ChatGPTService.createSystemChatRequestMsgDtoFrom(GPT_SYSTEM_MESSAGE));

        Map<String,Object> result =  chatGPTService.prompt(ChatGPTService.createChatCompletionDtoFrom(chatRequestMsgDtoList));
        System.out.println(result);
        DocumentContent documentContent = todayILearnedRepository
                .findDocumentContentByDateTodo(dateTodo,user)
                .orElseGet(this::createDocumentEmptyContent);
        return createDocumentContentDtoFrom(documentContent);
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
