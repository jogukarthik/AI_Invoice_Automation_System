package com.invoice.exception;

public class InvoiceNotFoundException extends Exception{
    public InvoiceNotFoundException(String msg){
        super(msg);
    }
}
