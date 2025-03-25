package com.lukasz.hotel_reservation.domain.pdf;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdf")
@AllArgsConstructor
@Log4j2
public class PdfController {
    private final PdfGeneratorService pdfGeneratorService;

    @PostMapping(value = "/generate", produces = MediaType.APPLICATION_PDF_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generatePdf(@RequestBody @Valid PdfGeneratorRequest pdfRequest) throws Exception {
        byte[] response = pdfGeneratorService.generate(pdfRequest);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; " + pdfRequest.image().src())
                .body(response);
    }
}
