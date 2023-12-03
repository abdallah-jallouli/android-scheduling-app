package com.example.chamiaapp.Models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.time.LocalDate;

@Entity(tableName = "daily_product_table",indices = {@Index(value = {"d_team_id"}),@Index(value = {"d_product_id"})} ,foreignKeys = {
        @ForeignKey(entity = Team.class, parentColumns = "t_id", childColumns = "d_team_id", onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = Product.class, parentColumns = "pr_id", childColumns = "d_product_id", onDelete = ForeignKey.CASCADE)
})
public class DailyProduct {
    @PrimaryKey(autoGenerate = true)
    public int d_id;

    public int d_number_of_cooks;
    public int d_priority;
    public LocalDate d_date;

    public int d_team_id;
    public int d_product_id;

    // Constructors, Getters, and Setters...


    public DailyProduct(LocalDate d_date, int d_product_id,  int d_team_id,int d_number_of_cooks, int d_priority) {
        this.d_number_of_cooks = d_number_of_cooks;
        this.d_priority = d_priority;
        this.d_date = d_date;
        this.d_team_id = d_team_id;
        this.d_product_id = d_product_id;
    }

    public int getD_id() {
        return d_id;
    }

    public void setD_id(int d_id) {
        this.d_id = d_id;
    }

    public int getD_number_of_cooks() {
        return d_number_of_cooks;
    }

    public void setD_number_of_cooks(int d_number_of_cooks) {
        this.d_number_of_cooks = d_number_of_cooks;
    }

    public int getD_priority() {
        return d_priority;
    }

    public void setD_priority(int d_priority) {
        this.d_priority = d_priority;
    }

    public LocalDate getD_date() {
        return d_date;
    }

    public void setD_date(LocalDate d_date) {
        this.d_date = d_date;
    }

    public int getD_team_id() {
        return d_team_id;
    }

    public void setD_team_id(int d_team_id) {
        this.d_team_id = d_team_id;
    }

    public int getD_product_id() {
        return d_product_id;
    }

    public void setD_product_id(int d_product_id) {
        this.d_product_id = d_product_id;
    }
}