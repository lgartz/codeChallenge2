package com.appgate.codetest.codeChallenge2.app.rest;

import com.appgate.codetest.codeChallenge2.usecase.SocialMentionFacebookUseCase;
import com.appgate.codetest.codeChallenge2.usecase.SocialMentionTwitterUseCase;
import com.appgate.codetest.codeChallenge2.usecase.validators.SocialMentionValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;

import static java.util.Objects.nonNull;

@RestController
public class SocialMentionController {

    @Autowired
    private SocialMentionFacebookUseCase socialMentionFacebookUseCase;

    @Autowired
    private SocialMentionTwitterUseCase socialMentionTweeterUseCase;

    @PostMapping(value = "/AnalyzeSocialMention", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String analyze(@RequestBody SocialMention socialMention) {
        return execute(socialMention);
    }

    private String execute(SocialMention socialMention){
        String messageValidation = SocialMentionValidator.validate(socialMention);
        if(nonNull(messageValidation))
            return messageValidation;
        if (nonNull(socialMention.getFacebookAccount()))
            return socialMentionFacebookUseCase.execute(socialMention);
        if (nonNull(socialMention.getTweeterAccount()))
            return socialMentionTweeterUseCase.execute(socialMention);
        return "No social network account found";
    }

}
