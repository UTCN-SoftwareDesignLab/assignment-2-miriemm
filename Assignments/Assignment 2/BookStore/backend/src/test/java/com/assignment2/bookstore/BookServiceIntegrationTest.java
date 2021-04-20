package com.assignment2.bookstore;

import com.assignment2.bookstore.model.Book;
import com.assignment2.bookstore.model.dto.BookDTO;
import com.assignment2.TestCreationFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class BookServiceIntegrationTest {

    @Autowired
    private BookService BookService;

    @Autowired
    private BookRepository BookRepository;

    @BeforeEach
    void setUp() {
        BookRepository.deleteAll();
    }

    @Test
    void findAll() {
        List<Book> books = TestCreationFactory.listOf(Book.class);
        BookRepository.saveAll(books);

        List<BookDTO> all = BookService.findAll();

        Assertions.assertEquals(books.size(), all.size());
    }
}