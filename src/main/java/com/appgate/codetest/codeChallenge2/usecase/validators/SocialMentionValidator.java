package com.appgate.codetest.codeChallenge2.usecase.validators;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;

import static java.util.Objects.isNull;

public class SocialMentionValidator {

    public static String validate(SocialMention socialMention){
        if(isNull(socialMention))
            return "Entry cannot be null";
        if(isNull(socialMention.getFacebookAccount()) && isNull(socialMention.getTweeterAccount()))
            return "Error, Tweeter or Facebook account must be present";
        return null;
    }

}
