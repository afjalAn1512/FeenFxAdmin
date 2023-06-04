package com.shit.feenfxadmin.model;

import java.util.Date;

public class TradeModel {

    private String currency,currency_high,currency_percentage,currency_price,date,slot_id,status,trade,trade_amount,trade_id,user_id,username;
    private Date timestamp;
    private long trade_time;

    public TradeModel() {
    }

    public TradeModel(String currency, String currency_high, String currency_percentage, String currency_price, String date, String slot_id, String status, String trade, String trade_amount, String trade_id, String user_id, String username, Date timestamp, long trade_time) {
        this.currency = currency;
        this.currency_high = currency_high;
        this.currency_percentage = currency_percentage;
        this.currency_price = currency_price;
        this.date = date;
        this.slot_id = slot_id;
        this.status = status;
        this.trade = trade;
        this.trade_amount = trade_amount;
        this.trade_id = trade_id;
        this.user_id = user_id;
        this.username = username;
        this.timestamp = timestamp;
        this.trade_time = trade_time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency_high() {
        return currency_high;
    }

    public void setCurrency_high(String currency_high) {
        this.currency_high = currency_high;
    }

    public String getCurrency_percentage() {
        return currency_percentage;
    }

    public void setCurrency_percentage(String currency_percentage) {
        this.currency_percentage = currency_percentage;
    }

    public String getCurrency_price() {
        return currency_price;
    }

    public void setCurrency_price(String currency_price) {
        this.currency_price = currency_price;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getTrade_amount() {
        return trade_amount;
    }

    public void setTrade_amount(String trade_amount) {
        this.trade_amount = trade_amount;
    }

    public String getTrade_id() {
        return trade_id;
    }

    public void setTrade_id(String trade_id) {
        this.trade_id = trade_id;
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

    public long getTrade_time() {
        return trade_time;
    }

    public void setTrade_time(long trade_time) {
        this.trade_time = trade_time;
    }
}
