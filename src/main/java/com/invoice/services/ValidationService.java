package com.invoice.services;

import com.invoice.dto.ExtractionAIResult;
import com.invoice.dto.ValidationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class  ValidationService {

    @Value("${invoice.confidence-threshold}")
    private Double threshold;

    public ValidationResult validate(
            ExtractionAIResult result) {

        boolean arithmeticValid =
                result.getSubtotal()
                        .add(result.getTax())
                        .compareTo(
                                result.getTotal()) == 0;

        boolean confidenceValid =
                result.getConfidence()
                        >= threshold;

        boolean autoApproved =
                arithmeticValid
                        && confidenceValid;

        return new ValidationResult(
                arithmeticValid,
                confidenceValid,
                autoApproved
        );
    }
}