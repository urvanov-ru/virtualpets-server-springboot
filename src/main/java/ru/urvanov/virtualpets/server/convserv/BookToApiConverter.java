package ru.urvanov.virtualpets.server.convserv;

import org.springframework.core.convert.converter.Converter;

import ru.urvanov.virtualpets.server.dao.domain.Book;

/**
 * На основе экземпляра класса Book предметной области создаёт
 * экземпляр Book из API для JavaScript-клиента.
 */
public class BookToApiConverter implements Converter<Book,
        ru.urvanov.virtualpets.server.controller.api.domain.Book> {

    /**
     * @param source Экземпляр {@link Book} предметной области.
     * @return Экземпляр {@link
     * ru.urvanov.virtualpets.server.controller.api.domain.Book} API
     */
    @Override
    public ru.urvanov.virtualpets.server.controller.api.domain.Book convert(
            Book source) {
        return new ru.urvanov.virtualpets.server.controller.api.domain.Book(
                source.getId(), source.getBookcaseLevel(),
                source.getBookcaseOrder());
    }

}
