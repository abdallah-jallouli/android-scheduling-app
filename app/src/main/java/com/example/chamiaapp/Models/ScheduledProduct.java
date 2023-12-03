package com.example.chamiaapp.Models;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity(tableName ="scheduled_product_table",indices = {@Index(value = {"sch_daily_product_id"})},
        foreignKeys = @ForeignKey(entity = DailyProduct.class, parentColumns = "d_id", childColumns = "sch_daily_product_id",
                onDelete = ForeignKey.CASCADE ))
public class ScheduledProduct {
    @PrimaryKey(autoGenerate = true)
    public int sch_id;

    public String sch_status;
    public int sch_delay;
    public LocalTime sch_start_time;

    public int sch_daily_product_id;

    // Constructors

    public ScheduledProduct(int sch_id, String sch_status, int sch_delay, LocalTime sch_start_time, int sch_daily_product_id) {
        this.sch_id = sch_id;
        this.sch_status = sch_status;
        this.sch_delay = sch_delay;
        this.sch_start_time = sch_start_time;
        this.sch_daily_product_id = sch_daily_product_id;
    }

    public ScheduledProduct() {
    }

    // Getters and Setters
    public int getSch_id() {
        return sch_id;
    }

    public void setSch_id(int sch_id) {
        this.sch_id = sch_id;
    }

    public String getSch_status() {
        return sch_status;
    }

    public void setSch_status(String sch_status) {
        this.sch_status = sch_status;
    }

    public int getSch_delay() {
        return sch_delay;
    }

    public void setSch_delay(int sch_delay) {
        this.sch_delay = sch_delay;
    }

    public LocalTime getSch_begin_time() {
        return sch_start_time;
    }

    public void setSch_begin_time(LocalTime sch_begin_time) {
        this.sch_start_time = sch_begin_time;
    }

    public int getSch_daily_product_id() {
        return sch_daily_product_id;
    }

    public void setSch_daily_product_id(int sch_daily_product_id) {
        this.sch_daily_product_id = sch_daily_product_id;
    }

    public void addDelay(int delay){
        sch_delay += delay;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void addDelayToBeginTime (int delay){
        sch_start_time= sch_start_time.plusMinutes(delay);
    }
}

