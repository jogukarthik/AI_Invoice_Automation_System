package com.invoice.util;


import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.security.MessageDigest;

@Component
public class HashUtil {

    public String generateSHA256(MultipartFile file) {

        try {

            MessageDigest digest =
                    MessageDigest.getInstance("SHA-256");

            byte[] hash =
                    digest.digest(file.getBytes());

            StringBuilder sb =
                    new StringBuilder();

            for(byte b : hash) {
                sb.append(
                        String.format("%02x", b)
                );
            }

            return sb.toString();

        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}