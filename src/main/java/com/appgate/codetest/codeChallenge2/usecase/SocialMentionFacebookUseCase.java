package com.appgate.codetest.codeChallenge2.usecase;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;
import com.appgate.codetest.codeChallenge2.usecase.analizer.FacebookAnalyzer;
import com.appgate.codetest.codeChallenge2.usecase.factory.SocialMentionFactory;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;

public class SocialMentionFacebookUseCase extends SocialMentionFactory {

    private static final String ANALYZED_FB_TABLE = "analyzed_fb_posts";

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
            if (facebookCommentsScore < 50d)
                facebookScore = Double.sum(facebookScore, -100d);
        }
        if (facebookScore > -100)
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
        if (scoreSocial == -100d)
            return "HIGH_RISK";
        if (scoreSocial > -100d && scoreSocial < 50d)
            return "MEDIUM_RISK";
        if (scoreSocial >= 50d)
            return "LOW_RISK";
        return defaultResponse;
    }
}
