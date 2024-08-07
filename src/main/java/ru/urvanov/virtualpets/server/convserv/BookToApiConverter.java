package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.Book;

public class BookToApiConverter implements
        Converter<Book, ru.urvanov.virtualpets.server.api.domain.Book> {

    @Override
    public ru.urvanov.virtualpets.server.api.domain.Book convert(
            Book source) {
        return new ru.urvanov.virtualpets.server.api.domain.Book(
                source.getId(), source.getBookcaseLevel(),
                source.getBookcaseOrder());
    }

}
