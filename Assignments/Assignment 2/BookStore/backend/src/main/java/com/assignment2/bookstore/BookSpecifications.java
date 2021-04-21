package com.assignment2.bookstore;

import com.assignment2.bookstore.model.Book;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecifications {
    static Specification<Book> hasAuthor(String author) {
        return (book, cq, cb) -> cb.equal(book.get("author"), author);
    }

    static Specification<Book> titleContains(String title) {
        return (book, cq, cb) -> cb.like(book.get("title"), "%" + title + "%");
    }

    static Specification<Book> genreContains(String genre) {
        return (book, cq, cb) -> cb.like(book.get("genre"), "%" + genre + "%");
    }
}
