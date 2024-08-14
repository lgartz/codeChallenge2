package com.appgate.codetest.codeChallenge2.adapter;

import com.appgate.codetest.codeChallenge2.usecase.port.DBService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DBServiceImpl implements DBService {
    private String host;
    private Integer port;

    @Override
    public void insertTweet(String analyzedTweetsTable, Double tweeterScore, String message, String tweeterUrl, String tweeterAccount) {
        //TODO: Implement this method
    }

    @Override
    public void insertFBPost(String analyzedFbTable, Double facebookScore, String message, String facebookAccount) {
        //TODO: Implement this method
    }
}
