package com.assignment2.bookstore;


import com.assignment2.bookstore.model.Book;
import com.assignment2.bookstore.model.dto.BookDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookDTO toDto(Book book);

    Book fromDto(BookDTO book);

}
