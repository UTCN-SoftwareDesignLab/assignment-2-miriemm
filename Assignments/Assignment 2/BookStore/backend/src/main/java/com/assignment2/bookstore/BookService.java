package com.assignment2.bookstore;

import com.assignment2.bookstore.model.Book;
import com.assignment2.bookstore.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
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

    public List<BookDTO> findByMultipleCriteria(String searchParameter){

        return bookRepository.findAll(Specification.where(BookSpecifications.hasAuthor(searchParameter)).or(BookSpecifications.titleContains(searchParameter)).or(BookSpecifications.genreContains(searchParameter))).stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDTO> findOutOfStockBooks() {

        List<Book> allBooks = bookRepository.findAll();

        List<BookDTO> outOfStockBooks = new ArrayList<>();

        for (Book book : allBooks) {
            if (book.getQuantity() == 0) {
                outOfStockBooks.add(bookMapper.toDto(book));
            }
        }

        return outOfStockBooks;


    }

}
