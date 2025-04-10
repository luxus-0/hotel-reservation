package com.lukasz.hotel_reservation.domain.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.lukasz.hotel_reservation.domain.pdf.dto.FontRequest;
import com.lukasz.hotel_reservation.domain.pdf.dto.FontStyleNotFoundException;
import com.lukasz.hotel_reservation.domain.pdf.exceptions.FontNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FontService {
    public Font convertFont(FontRequest fontRequest) {
        if (fontRequest == null) {
            throw new FontNotFoundException("Font not found");
        }
        int fontStyle = getStyle(fontRequest.style());
        BaseColor baseColor = new BaseColor(fontRequest.color().red(), fontRequest.color().green(), fontRequest.color().blue());
        return FontFactory.getFont(fontRequest.family(), fontRequest.size(), fontStyle, baseColor);
    }

    private int getStyle(String fontStyle) {
        switch (fontStyle.toLowerCase()) {
            case "normal" -> {
                return Font.NORMAL;
            }
            case "bold" -> {
                return Font.BOLD;
            }
            case "italic" -> {
                return Font.ITALIC;
            }
            case "bold italic" -> {
                return Font.BOLDITALIC;
            }
            case "underline" -> {
                return Font.UNDERLINE;
            }
            case "default size" -> {
                return Font.DEFAULTSIZE;
            }
            case "undefined" -> {
                return Font.UNDEFINED;
            }
            default -> throw new FontStyleNotFoundException("Font style not found");
        }
    }
}
