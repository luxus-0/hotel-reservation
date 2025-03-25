package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@AllArgsConstructor
@Service
public class PdfGeneratorService implements PdfGenerator {

    private final ImageService imageService;
    private final ParagraphService paragraphService;
    private final TableService tableService;

    @Override
    public byte[] generate(PdfGeneratorRequest pdf) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();
            document.addTitle(pdf.title());

            Image image = imageService.getImage(pdf.image());
            document.add(image);

            Paragraph paragraph = paragraphService.getParagraph(pdf.paragraph());
            document.add(paragraph);

            PdfPTable table = tableService.getTable(pdf.table());
            document.add(table);

            document.close();
            return outputStream.toByteArray();
        }
    }
}