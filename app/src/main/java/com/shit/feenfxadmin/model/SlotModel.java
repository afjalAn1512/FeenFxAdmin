package com.shit.feenfxadmin.model;

import java.util.Date;

public class SlotModel {
    private String slot_id,date,slot_time;
    private long current_slot,last_slot;
    private Date timestamp;

    public SlotModel() {
    }

    public SlotModel(String slot_id, String date, String slot_time, long current_slot, long last_slot, Date timestamp) {
        this.slot_id = slot_id;
        this.date = date;
        this.slot_time = slot_time;
        this.current_slot = current_slot;
        this.last_slot = last_slot;
        this.timestamp = timestamp;
    }


    public String getSlot_time() {
        return slot_time;
    }

    public void setSlot_time(String slot_time) {
        this.slot_time = slot_time;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public long getCurrent_slot() {
        return current_slot;
    }

    public void setCurrent_slot(long current_slot) {
        this.current_slot = current_slot;
    }

    public long getLast_slot() {
        return last_slot;
    }

    public void setLast_slot(long last_slot) {
        this.last_slot = last_slot;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
