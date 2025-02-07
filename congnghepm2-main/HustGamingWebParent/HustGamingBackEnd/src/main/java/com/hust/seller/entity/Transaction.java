package com.hust.seller.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TransactionID")
    private int transactionID;
    @Column(name="UserID")
    private int userID;
    @Column(name="Payment_content")
    private String paymentContent;
    @Column(name="Payment_status")
    private String paymentStatus;
    @Column(name="Payment_date")
    private LocalDateTime paymentDate;
    @Column(name="Money")
    private float money;

    public Transaction(int userID, String paymentContent, String paymentStatus, LocalDateTime paymentDate, float money) {
        this.userID = userID;
        this.paymentContent = paymentContent;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
        this.money = money;
    }
    public Transaction(){};

    public int getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(int transactionID) {
        this.transactionID = transactionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getPaymentContent() {
        return paymentContent;
    }

    public void setPaymentContent(String paymentContent) {
        this.paymentContent = paymentContent;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }
}
