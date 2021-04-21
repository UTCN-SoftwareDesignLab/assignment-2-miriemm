package com.assignment2.report;

import com.assignment2.bookstore.BookService;
import com.assignment2.bookstore.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.CsvListWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CSVReportService implements ReportService {

    private final BookService bookService;

    @Override
    public byte[] export() {
        List<BookDTO> booksOutOfStock = bookService.findOutOfStockBooks();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Writer writer = new StringWriter();
        try {

            writer.append(new String(byteArrayOutputStream.toByteArray()));
            writer.close();

        ICsvBeanWriter csvWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"Title", "Author", "Genre", "Price"};
        String[] nameMapping = {"title", "author", "genre", "price"};

        csvWriter.writeHeader(csvHeader);


        for (BookDTO book: booksOutOfStock) {
            csvWriter.write(book, nameMapping);
        }


        csvWriter.close();

        return  writer.toString().getBytes();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ReportType getType() {
        return ReportType.CSV;
    }
}
