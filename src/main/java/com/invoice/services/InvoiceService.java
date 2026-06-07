package com.invoice.services;

import com.invoice.dto.InvoiceResponse;
import com.invoice.entity.Invoice;
import com.invoice.exception.DuplicateInvoiceException;
import com.invoice.repositories.InvoiceRepository;
import com.invoice.util.HashUtil;
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
    private final InvoiceProducer invoiceProducer;
    private final HashUtil hashUtil;
    @Value("${file.upload-dir}")
    private String uploadDir;

    public InvoiceResponse uploadInvoice(
            MultipartFile file,
            String sourceEmail) throws DuplicateInvoiceException {

        try {

            String fileHash = hashUtil.generateSHA256(file);

            if (repository.existsByFileHash(fileHash)) {
                throw new DuplicateInvoiceException(
                        "Invoice already uploaded: " + file.getOriginalFilename());
            }

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
                            .fileHash(fileHash)
                            .sourceEmail(sourceEmail)
                            .status("RECEIVED")
                            .createdAt(LocalDateTime.now())
                            .build();

            Invoice saved =
                    repository.save(invoice);

            invoiceProducer.publishInvoice(saved.getId());
            return InvoiceResponse.builder()
                    .id(saved.getId())
                    .status(saved.getStatus())
                    .build();

        } catch(DuplicateInvoiceException e) {
            throw e;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
