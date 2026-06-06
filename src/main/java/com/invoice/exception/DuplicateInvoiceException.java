package com.invoice.exception;

public class DuplicateInvoiceException extends Exception{
    public DuplicateInvoiceException(String msg){
        super(msg);
    }
}
