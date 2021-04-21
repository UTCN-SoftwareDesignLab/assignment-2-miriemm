package com.assignment2.bookstore;

import com.assignment2.UrlMapping;
import com.assignment2.bookstore.model.dto.BookDTO;
import com.assignment2.report.ReportServiceFactory;
import com.assignment2.report.ReportType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UrlMapping.BOOK_STORE)
@RequiredArgsConstructor
public class BookStoreController {

    private final BookService bookService;
    private final ReportServiceFactory reportServiceFactory;

    @GetMapping
    public List<BookDTO> allBooks() {
        return bookService.findAll();
    }

    @PostMapping
    public BookDTO create(@RequestBody BookDTO book) {
        return bookService.create(book);
    }

    @PatchMapping
    public BookDTO edit(@RequestBody BookDTO book) {
        return bookService.edit(book);
    }

    @DeleteMapping
    public void delete(@RequestBody BookDTO book){
        bookService.delete(book.getId());
    }

//    @GetMapping(UrlMapping.EXPORT_REPORT)
    @GetMapping(value = "/export/{type}",
    produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public @ResponseBody byte[] export(@PathVariable ReportType type) {

        return reportServiceFactory.getReportService(type).export();
    }

    @PatchMapping("/sellBook")
    public void sellBook(@RequestBody BookDTO book) {
        bookService.sellBook(book.getId());
    }

    @GetMapping("/findByMultipleCriteria/{searchParameter}")
    public List<BookDTO> findByMultipleCriteria(@PathVariable(value="searchParameter") String searchParameter) {
        return bookService.findByMultipleCriteria(searchParameter);
    }

}
