package com.example.chamiaapp.Models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity (tableName = "team_table")
public class Team {
    @PrimaryKey(autoGenerate = true)
    public int t_id;
    public String t_name;
    public String t_description;
    public LocalTime t_begin_time;

    public int t_overall_performance;


    // Constructors

    public Team(String t_name, LocalTime t_begin_time, String t_description) {
        this.t_name = t_name;
        this.t_description = t_description;
        this.t_begin_time = t_begin_time;
    }

    public Team() {

    }

    // Getter and Setter


    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getT_description() {
        return t_description;
    }

    public void setT_description(String t_description) {
        this.t_description = t_description;
    }

    public LocalTime getT_begin_time() {
        return t_begin_time;
    }

    public void setT_begin_time(LocalTime t_begin_time) {
        this.t_begin_time = t_begin_time;
    }

    public int getT_overall_performance() {
        return t_overall_performance;
    }

    public void setT_overall_performance(int t_overall_performance) {
        this.t_overall_performance = t_overall_performance;
    }
}
