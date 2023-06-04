package com.shit.feenfxadmin.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class DepositStatusModel {

    private String amount, sender_trx, deposit_id, sendTo, status, transaction, user_id, username;
    private @ServerTimestamp
    Date timestamp;

    public DepositStatusModel() {
    }


    public DepositStatusModel(String amount, String sender_trx, String deposit_id, String sendTo, String status, String transaction, String user_id, String username, Date timestamp) {
        this.amount = amount;
        this.sender_trx = sender_trx;
        this.deposit_id = deposit_id;
        this.sendTo = sendTo;
        this.status = status;
        this.transaction = transaction;
        this.user_id = user_id;
        this.username = username;
        this.timestamp = timestamp;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSender_trx() {
        return sender_trx;
    }

    public void setSender_trx(String sender_trx) {
        this.sender_trx = sender_trx;
    }

    public String getDeposit_id() {
        return deposit_id;
    }

    public void setDeposit_id(String deposit_id) {
        this.deposit_id = deposit_id;
    }

    public String getSendTo() {
        return sendTo;
    }

    public void setSendTo(String sendTo) {
        this.sendTo = sendTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
