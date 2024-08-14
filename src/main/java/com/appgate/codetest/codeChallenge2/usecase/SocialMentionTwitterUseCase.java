package com.appgate.codetest.codeChallenge2.usecase;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;
import com.appgate.codetest.codeChallenge2.usecase.analizer.TweeterAnalyzer;
import com.appgate.codetest.codeChallenge2.usecase.factory.SocialMentionFactory;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;

public class SocialMentionTwitterUseCase extends SocialMentionFactory {

    private static final String ANALYZED_TWEETS_TABLE = "analyzed_tweets";
    private static final double TWITTER_HIGH_RISK_VALUE = -1;
    private static final double TWITTER_MEDIUM_RISK_VALUE = -0.5d;
    private static final double TWITTER_LOW_RISK_VALUE = 0.7d;

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
        if (scoreSocial >= TWITTER_HIGH_RISK_VALUE && scoreSocial <= TWITTER_MEDIUM_RISK_VALUE) {
            return "HIGH_RISK";
        } else if (scoreSocial > TWITTER_MEDIUM_RISK_VALUE && scoreSocial < TWITTER_LOW_RISK_VALUE) {
            return "MEDIUM_RISK";
        } else if (scoreSocial >= TWITTER_LOW_RISK_VALUE) {
            return "LOW_RISK";
        }
        return defaultResponse;
    }

}
