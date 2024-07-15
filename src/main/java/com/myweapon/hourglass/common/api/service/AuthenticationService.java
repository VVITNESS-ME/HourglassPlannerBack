package com.myweapon.hourglass.common.api.service;

import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private String GPTAuthToken = "sk-proj-EEmpNHijWevsQi4XtqAJT3BlbkFJDYcRmbAXICofoIJFR7Et";

    public String getChatGPTAuthToken() {
        return GPTAuthToken;
    }
}
