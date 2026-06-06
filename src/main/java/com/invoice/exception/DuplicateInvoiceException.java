package com.invoice.exception;

public class DuplicateInvoiceException extends Exception{
    DuplicateInvoiceException(String msg){
        super(msg);
    }
}
