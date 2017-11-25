package com.suver.nate.patientscheduler.Models;

/**
 * Created by nates on 11/24/2017.
 */

public class InvoiceListItem {
    private Integer id;
    private String invoiceNumber;
    private String invoiceDate;
    private String serviceFrom;
    private String serviceThrough;
    private Double amount;
    private Double balance;
    private String payerName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public String getServiceFrom() {
        return serviceFrom;
    }

    public void setServiceFrom(String serviceFrom) {
        this.serviceFrom = serviceFrom;
    }

    public String getServiceThrough() {
        return serviceThrough;
    }

    public void setServiceThrough(String serviceThrough) {
        this.serviceThrough = serviceThrough;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }
}
