package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

public class ClothTypeSharedToServerConverter implements Converter<ru.urvanov.virtualpets.shared.domain.ClothType, ru.urvanov.virtualpets.server.dao.domain.ClothId> {

    @Override
    public ru.urvanov.virtualpets.server.dao.domain.ClothId convert(
            ru.urvanov.virtualpets.shared.domain.ClothType source) {
        return ru.urvanov.virtualpets.server.dao.domain.ClothId.valueOf(source.name());
    }

}
