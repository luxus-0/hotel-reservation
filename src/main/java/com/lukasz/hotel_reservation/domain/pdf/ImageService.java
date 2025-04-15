package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.lukasz.hotel_reservation.domain.pdf.dto.ImageRequest;
import com.lukasz.hotel_reservation.domain.pdf.exceptions.TableAlignmentNotFoundException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Service
public class ImageService {

    public Image getImage(ImageRequest imageRequest) throws BadElementException, IOException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("image/" + imageRequest.src())) {
            if (is == null) {
                throw new FileNotFoundException("Image not found in path resources/image/");
            }
            Image image = Image.getInstance(IOUtils.toByteArray(is));
            image.scaleAbsolute(imageRequest.width(), imageRequest.height());
            image.setAbsolutePosition(imageRequest.xPosition(), imageRequest.yPosition());
            int alignment = getAlignment(imageRequest.alignment());
            image.setAlignment(alignment);
            return image;
        }
    }

    private int getAlignment(String imageAlignment) {
        switch (imageAlignment.toLowerCase()) {
            case "center" -> {
                return Image.ALIGN_CENTER;
            }
            case "right" -> {
                return Image.RIGHT;
            }
            case "left" -> {
                return Image.LEFT;
            }
            default -> throw new TableAlignmentNotFoundException("Table alignment not found");
        }
    }
}
