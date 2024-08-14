package com.appgate.codetest.codeChallenge2.app.config;

import com.appgate.codetest.codeChallenge2.adapter.DBServiceImpl;
import com.appgate.codetest.codeChallenge2.usecase.SocialMentionFacebookUseCase;
import com.appgate.codetest.codeChallenge2.usecase.SocialMentionTwitterUseCase;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public DBService dbService() {
        return new DBServiceImpl("localhost", 5432);
    }

    @Bean
    public SocialMentionFacebookUseCase socialMentionFacebookUseCase() {
        return new SocialMentionFacebookUseCase(dbService());
    }

    @Bean
    public SocialMentionTwitterUseCase socialMentionTwitterUseCase() {
        return new SocialMentionTwitterUseCase(dbService());
    }

}
