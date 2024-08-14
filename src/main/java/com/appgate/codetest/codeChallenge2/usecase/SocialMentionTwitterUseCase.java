package com.appgate.codetest.codeChallenge2.usecase;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;
import com.appgate.codetest.codeChallenge2.usecase.analizer.TweeterAnalyzer;
import com.appgate.codetest.codeChallenge2.usecase.factory.SocialMentionFactory;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;

public class SocialMentionTwitterUseCase extends SocialMentionFactory {

    private static final String ANALYZED_TWEETS_TABLE = "analyzed_tweets";

    public SocialMentionTwitterUseCase(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void adjustMessage(SocialMention socialMention) {
        socialMention.setMessage("tweeterMessage: " + socialMention.getMessage());
    }

    @Override
    protected Double executeAnalizer(SocialMention socialMention) {
        return TweeterAnalyzer.analyzeTweet(
                socialMention.getMessage(),
                socialMention.getTweeterUrl(),
                socialMention.getTweeterAccount()
        );
    }

    @Override
    protected void saveSocialMention(SocialMention socialMention, Double scoreSocial) {
        dbService.insertTweet(
                ANALYZED_TWEETS_TABLE,
                scoreSocial,
                socialMention.getMessage(),
                socialMention.getTweeterUrl(),
                socialMention.getTweeterAccount()
        );
    }

    @Override
    protected String mapResponse(Double scoreSocial, String defaultResponse) {
        if (scoreSocial >= -1 && scoreSocial <= -0.5d) {
            return "HIGH_RISK";
        } else if (scoreSocial > -0.5d && scoreSocial < 0.7d) {
            return "MEDIUM_RISK";
        } else if (scoreSocial >= 0.7d) {
            return "LOW_RISK";
        }
        return defaultResponse;
    }

}
