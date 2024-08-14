package com.appgate.codetest.codeChallenge2.domain;

import lombok.Data;

import java.util.List;

@Data
public class SocialMention {
    private String message;
    private String facebookAccount;
    private String tweeterAccount;
    private String creationDate;
    private String tweeterUrl;
    private List<String> facebookComments;
}
