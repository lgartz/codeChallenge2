package com.appgate.codetest.codeChallenge2.usecase;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;
import com.appgate.codetest.codeChallenge2.usecase.analizer.FacebookAnalyzer;
import com.appgate.codetest.codeChallenge2.usecase.factory.SocialMentionFactory;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;

public class SocialMentionFacebookUseCase extends SocialMentionFactory {

    private static final String ANALYZED_FB_TABLE = "analyzed_fb_posts";
    private static final double FACEBOOK_HIGH_RISK_VALUE = -100d;
    private static final double FACEBOOK_MEDIUM_RISK_VALUE = 50d;

    public SocialMentionFacebookUseCase(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    protected void adjustMessage(SocialMention socialMention) {
        socialMention.setMessage("facebookMessage: " + socialMention.getMessage());
        String comments = socialMention.getFacebookComments().stream().reduce("", (h, c) -> h + " " + c);
        socialMention.setMessage(socialMention.getMessage() + " || comments: " + comments);
    }

    @Override
    protected Double executeAnalizer(SocialMention socialMention) {
        Double facebookScore = 0d;
        if (socialMention.getMessage().contains("comments:")) {
            Double facebookCommentsScore = FacebookAnalyzer.calculateFacebookCommentsScore(
                    socialMention.getMessage().substring(socialMention.getMessage().indexOf("comments:")));
            if (facebookCommentsScore < FACEBOOK_MEDIUM_RISK_VALUE)
                facebookScore = Double.sum(facebookScore, FACEBOOK_HIGH_RISK_VALUE);
        }
        if (facebookScore > FACEBOOK_HIGH_RISK_VALUE)
            facebookScore = FacebookAnalyzer.analyzePost(socialMention.getMessage(),socialMention.getFacebookAccount());
        return facebookScore;
    }

    @Override
    protected void saveSocialMention(SocialMention socialMention, Double scoreSocial) {
        if (scoreSocial > -100) {
            dbService.insertFBPost(
                    ANALYZED_FB_TABLE,
                    scoreSocial,
                    socialMention.getMessage(),
                    socialMention.getFacebookAccount()
            );
        }
    }

    @Override
    protected String mapResponse(Double scoreSocial, String defaultResponse) {
        if (scoreSocial == FACEBOOK_HIGH_RISK_VALUE)
            return "HIGH_RISK";
        if (scoreSocial > FACEBOOK_HIGH_RISK_VALUE && scoreSocial < FACEBOOK_MEDIUM_RISK_VALUE)
            return "MEDIUM_RISK";
        if (scoreSocial >= FACEBOOK_MEDIUM_RISK_VALUE)
            return "LOW_RISK";
        return defaultResponse;
    }
}
