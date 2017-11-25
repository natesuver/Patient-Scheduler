package com.suver.nate.patientscheduler.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by nates on 11/24/2017.
 * note: ran through http://www.parcelabler.com/ to implements Parcelable
 */

public class InvoiceListItem implements Parcelable {
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

    public String getServiceDates() {
        return this.serviceFrom + " to " + this.serviceThrough;
    }

    protected InvoiceListItem(Parcel in) {
        id = in.readByte() == 0x00 ? null : in.readInt();
        invoiceNumber = in.readString();
        invoiceDate = in.readString();
        serviceFrom = in.readString();
        serviceThrough = in.readString();
        amount = in.readByte() == 0x00 ? null : in.readDouble();
        balance = in.readByte() == 0x00 ? null : in.readDouble();
        payerName = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeInt(id);
        }
        dest.writeString(invoiceNumber);
        dest.writeString(invoiceDate);
        dest.writeString(serviceFrom);
        dest.writeString(serviceThrough);
        if (amount == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(amount);
        }
        if (balance == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(balance);
        }
        dest.writeString(payerName);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<InvoiceListItem> CREATOR = new Parcelable.Creator<InvoiceListItem>() {
        @Override
        public InvoiceListItem createFromParcel(Parcel in) {
            return new InvoiceListItem(in);
        }

        @Override
        public InvoiceListItem[] newArray(int size) {
            return new InvoiceListItem[size];
        }
    };
}