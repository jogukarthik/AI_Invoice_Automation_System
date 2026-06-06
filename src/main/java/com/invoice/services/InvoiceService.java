package com.invoice.services;

import com.invoice.dto.InvoiceResponse;
import com.invoice.entity.Invoice;
import com.invoice.repositories.InvoiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService
         {
    private final InvoiceRepository repository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public InvoiceResponse uploadInvoice(
            MultipartFile file,
            String sourceEmail) {

        try {

            File directory =
                    new File(uploadDir);

            if(!directory.exists()) {
                directory.mkdirs();
            }

            String fileName =
                    UUID.randomUUID()
                            + "_"
                            + file.getOriginalFilename();

            Path path =
                    Paths.get(uploadDir, fileName);

            Files.copy(
                    file.getInputStream(),
                    path
            );

            Invoice invoice =
                    Invoice.builder()
                            .fileName(file.getOriginalFilename())
                            .filePath(path.toString())
                            .sourceEmail(sourceEmail)
                            .status("RECEIVED")
                            .createdAt(LocalDateTime.now())
                            .build();

            Invoice saved =
                    repository.save(invoice);

            return InvoiceResponse.builder()
                    .id(saved.getId())
                    .status(saved.getStatus())
                    .build();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
