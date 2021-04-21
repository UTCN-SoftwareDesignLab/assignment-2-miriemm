package com.assignment2.bookstore;

import com.assignment2.bookstore.model.Book;
import com.assignment2.BaseControllerTest;
import com.assignment2.TestCreationFactory;
import com.assignment2.bookstore.model.dto.BookDTO;
import com.assignment2.report.CSVReportService;
import com.assignment2.report.PdfReportService;
import com.assignment2.report.ReportServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.assignment2.TestCreationFactory.randomLong;
import static com.assignment2.TestCreationFactory.randomString;
import static com.assignment2.TestCreationFactory.randomBoundedInt;
import static com.assignment2.UrlMapping.EXPORT_REPORT;
import static com.assignment2.UrlMapping.BOOK_STORE;
import static com.assignment2.report.ReportType.CSV;
import static com.assignment2.report.ReportType.PDF;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookStoreControllerTest extends BaseControllerTest {

    @InjectMocks
    private BookStoreController controller;

    @Mock
    private BookService bookService;

    @Mock
    private ReportServiceFactory reportServiceFactory;

    @Mock
    private CSVReportService csvReportService;

    @Mock
    private PdfReportService pdfReportService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new BookStoreController(bookService, reportServiceFactory);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void allBooks() throws Exception {
        List<BookDTO> books = TestCreationFactory.listOf(Book.class);
        when(bookService.findAll()).thenReturn(books);

        ResultActions response = mockMvc.perform(get(BOOK_STORE));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(books));

    }

    @Test
    void export() throws Exception {
        when(reportServiceFactory.getReportService(PDF)).thenReturn(pdfReportService);
        when(reportServiceFactory.getReportService(CSV)).thenReturn(csvReportService);
        String pdfResponse = "PDF!";
        when(pdfReportService.export()).thenReturn(pdfResponse.getBytes(StandardCharsets.UTF_8));
        String csvResponse = "CSV!";
        when(csvReportService.export()).thenReturn(csvResponse.getBytes(StandardCharsets.UTF_8));

        ResultActions pdfExport = mockMvc.perform(get(BOOK_STORE + EXPORT_REPORT, PDF.name()));
        ResultActions csvExport = mockMvc.perform(get(BOOK_STORE + EXPORT_REPORT, CSV.name()));

        pdfExport.andExpect(status().isOk())
                .andExpect(content().string(pdfResponse));
        csvExport.andExpect(status().isOk())
                .andExpect(content().string(csvResponse));

    }

    @Test
    void create() throws Exception {
        BookDTO reqBook = BookDTO.builder().title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomBoundedInt(1000))
                .build();

        when(bookService.create(reqBook)).thenReturn(reqBook);

        ResultActions result = performPostWithRequestBody(BOOK_STORE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void edit() throws Exception {
        BookDTO reqBook = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomBoundedInt(1000))
                .build();

        when(bookService.edit(reqBook)).thenReturn(reqBook);

        ResultActions result = performPutWithRequestBody(BOOK_STORE, reqBook);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(reqBook));
    }

    @Test
    void delete() throws Exception {

        BookDTO bookToDelete = BookDTO.builder()
                .id(randomLong())
                .title(randomString())
                .author(randomString())
                .genre(randomString())
                .price(randomLong())
                .quantity(randomBoundedInt(1000))
                .build();

        bookService.delete(bookToDelete.getId());

        ResultActions result = performDeleteWithRequestBody(BOOK_STORE, bookToDelete);
        result.andExpect(status().isOk());


    }
}