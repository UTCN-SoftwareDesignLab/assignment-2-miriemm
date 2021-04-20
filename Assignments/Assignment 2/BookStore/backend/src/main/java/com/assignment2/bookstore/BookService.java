package com.assignment2.bookstore;

import com.assignment2.bookstore.model.Book;
import com.assignment2.bookstore.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Book not found: " + id));
    }

    public List<BookDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public BookDTO create(BookDTO book) {
        return bookMapper.toDto(bookRepository.save(
                bookMapper.fromDto(book)
        ));
    }

    public BookDTO edit(BookDTO book) {
        Book actBook = findById(book.getId());
        actBook.setTitle(book.getTitle());
        actBook.setAuthor(book.getAuthor());
        actBook.setGenre(book.getGenre());
        actBook.setPrice(book.getPrice());
        actBook.setQuantity(book.getQuantity());
        return bookMapper.toDto(
                bookRepository.save(actBook)
        );
    }

    public void delete(Long id) {
        Book bookToDelete = findById(id);
        bookRepository.delete(bookToDelete);
    }

    // I can sell only one book per moment
    // in the frontend, I have a sell icon
    public void sellBook(Long id) {
        Book bookToSell = findById(id);
        bookToSell.setQuantity(bookToSell.getQuantity() - 1);

        bookMapper.toDto(bookRepository.save(bookToSell));

    }

    static Specification<Book> hasAuthor(String author) {
        return (book, cq, cb) -> cb.equal(book.get("author"), author);
    }

    static Specification<Book> titleContains(String title) {
        return (book, cq, cb) -> cb.like(book.get("title"), "%" + title + "%");
    }

    static Specification<Book> genreContains(String genre) {
        return (book, cq, cb) -> cb.like(book.get("genre"), "%" + genre + "%");
    }

    public List<BookDTO> findByMultipleCriteria(String searchParameter){

        return bookRepository.findAll(Specification.where(hasAuthor(searchParameter)).or(titleContains(searchParameter)).or(genreContains(searchParameter))).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

}
