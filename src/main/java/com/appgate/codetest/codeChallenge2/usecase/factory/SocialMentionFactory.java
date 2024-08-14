package com.appgate.codetest.codeChallenge2.usecase.factory;

import com.appgate.codetest.codeChallenge2.domain.SocialMention;
import com.appgate.codetest.codeChallenge2.usecase.port.DBService;

public abstract class SocialMentionFactory {

    protected DBService dbService;

    public String execute(SocialMention socialMention){
        // 1. se ajusta el mensaje segun la red social
        adjustMessage(socialMention);
        // 2. Pasar la mención social al analizador de la red social correspondiente
        Double scoreSocial = executeAnalizer(socialMention);
        // 3. Guardar en base de datos el resultado del análisis
        saveSocialMention(socialMention, scoreSocial);
        // 4. Mapear la respuesta de la red social a un mensaje que puede interpretar el cliente
        return mapResponse(scoreSocial, socialMention.getMessage());
    }

    protected abstract void adjustMessage(SocialMention socialMention);

    protected abstract Double executeAnalizer(SocialMention socialMention);

    protected abstract void saveSocialMention(SocialMention socialMention, Double scoreSocial);

    protected abstract String mapResponse(Double scoreSocial, String defaultResponse);

}
