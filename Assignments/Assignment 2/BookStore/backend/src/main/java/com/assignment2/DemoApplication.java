package com.assignment2;

import com.assignment2.bookstore.BookService;
import com.assignment2.bookstore.model.Book;
import com.assignment2.report.PdfReportService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {


    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

}
