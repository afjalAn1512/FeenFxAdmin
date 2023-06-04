package com.shit.feenfxadmin.model;

import java.util.Date;

public class WithdrawModel {

    private String amount,email,method,phone,sender_trx,status,user_id,username,withdraw_id;
    private Date timestamp;


    public WithdrawModel() {
    }

    public WithdrawModel(String amount, String email, String method, String phone, String sender_trx, String status, String user_id, String username, String withdraw_id, Date timestamp) {
        this.amount = amount;
        this.email = email;
        this.method = method;
        this.phone = phone;
        this.sender_trx = sender_trx;
        this.status = status;
        this.user_id = user_id;
        this.username = username;
        this.withdraw_id = withdraw_id;
        this.timestamp = timestamp;
    }


    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSender_trx() {
        return sender_trx;
    }

    public void setSender_trx(String sender_trx) {
        this.sender_trx = sender_trx;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getWithdraw_id() {
        return withdraw_id;
    }

    public void setWithdraw_id(String withdraw_id) {
        this.withdraw_id = withdraw_id;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
