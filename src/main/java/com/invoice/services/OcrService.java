package com.invoice.services;

import com.invoice.exception.OcrfailedException;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Service
public class OcrService {

    @Value("${ocr.tessdata-path}")
    private String tessdataPath;
    public String extractText(String pdfPath) throws OcrfailedException {

        try {

            StringBuilder result =
                    new StringBuilder();

            PDDocument document =
                    Loader.loadPDF(
                            new File(pdfPath));

            PDFRenderer renderer =
                    new PDFRenderer(document);

            Tesseract tesseract =
                    new Tesseract();

            tesseract.setDatapath(
                    tessdataPath);

            for(int page = 0;
                page < document.getNumberOfPages();
                page++) {

                BufferedImage image =
                        renderer.renderImageWithDPI(
                                page,
                                300);

                String text =
                        tesseract.doOCR(image);

                result.append(text)
                        .append("\n");
            }

            document.close();

            return result.toString();

        } catch (TesseractException e) {
            throw new OcrfailedException("Tesseract OCR failed: " + e.getMessage());
        } catch (IOException e) {
            throw new OcrfailedException("Failed to read PDF: " + e.getMessage());
        }
    }
}
