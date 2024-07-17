package com.myweapon.hourglass.today.i.learned.service;

import com.myweapon.hourglass.security.entity.User;
import com.myweapon.hourglass.today.i.learned.dto.DocumentContentDto;

import java.time.LocalDate;

public interface TodayILearnedService {
    public DocumentContentDto getDocumentContent(LocalDate createdDate, User user);
    public void updateDocumentContent(DocumentContentDto documentContentDto, LocalDate createdDate, User user);
    public DocumentContentDto convertTilWithChatGPT(DocumentContentDto requestContentDto, User user);
}
