package com.appgate.codetest.codeChallenge2.usecase.port;

public interface DBService {
    void insertTweet(String analyzedTweetsTable, Double tweeterScore, String message, String tweeterUrl, String tweeterAccount);
    void insertFBPost(String analyzedFbTable, Double facebookScore, String message, String facebookAccount);
}
