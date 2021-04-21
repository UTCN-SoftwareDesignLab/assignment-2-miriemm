package com.assignment2.report;

import com.assignment2.bookstore.BookRepository;
import com.assignment2.bookstore.BookService;
import com.assignment2.bookstore.model.Book;
import com.assignment2.bookstore.model.dto.BookDTO;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PdfReportService implements ReportService {

    private final BookService bookService;

    @Override
    public byte[] export() {
        PDDocument document = new PDDocument();
        PDPage page = new PDPage();
        document.addPage(page);
        List<BookDTO> booksOutOfStock = bookService.findOutOfStockBooks();


        PDPageContentStream contentStream = null;
        try {
            contentStream = new PDPageContentStream(document, page);

            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.setLeading(15.0f);


            float yCordinate = page.getCropBox().getUpperRightY() - 30;
            float startX = page.getCropBox().getLowerLeftX() + 30;


            contentStream.beginText();
            contentStream.newLineAtOffset(startX, yCordinate);
            contentStream.showText("Out of stock books");
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 16);


            contentStream.newLine();

            for(BookDTO book: booksOutOfStock){
                contentStream.newLine();
                contentStream.showText(book.getTitle() + " by " + book.getAuthor());

            }

            contentStream.endText();

            contentStream.close();

            // PDF document to byte array:
            // https://stackoverflow.com/questions/11593116/using-pdfbox-how-do-i-retrieve-contents-of-pddocument-as-a-byte-array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            document.save(byteArrayOutputStream);
            document.close();
            return byteArrayOutputStream.toByteArray();

           // document.save("outOfStock.pdf");
          //  document.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ReportType getType() {
        return ReportType.PDF;
    }
}
