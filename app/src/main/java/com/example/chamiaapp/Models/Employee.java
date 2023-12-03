package com.example.chamiaapp.Models;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName ="employee_table",indices = {@Index(value = {"team_id"})},foreignKeys = @ForeignKey(entity =
        Team.class, parentColumns = "t_id", childColumns = "team_id",onDelete = ForeignKey.CASCADE))
public class Employee {
    @PrimaryKey(autoGenerate = true)
    public int e_id;
    public String e_first_name;
    public String e_last_name;
    public String e_date_of_birth;
    public String e_address;
    public String e_phone_number;
    public String e_hiring_date;
    @ColumnInfo(name = "team_id")
    public int team_id;

    // Constructors

    public Employee( String e_first_name, String e_last_name, String e_date_of_birth, String e_address, String e_phone_number, String e_hiring_date, int team_id) {
        this.e_first_name = e_first_name;
        this.e_last_name = e_last_name;
        this.e_date_of_birth = e_date_of_birth;
        this.e_address = e_address;
        this.e_phone_number = e_phone_number;
        this.e_hiring_date = e_hiring_date;
        this.team_id = team_id;
    }

    // Getters and Setters
    public int getE_id() {
        return e_id;
    }

    public void setE_id(int e_id) {
        this.e_id = e_id;
    }

    public String getE_first_name() {
        return e_first_name;
    }

    public void setE_first_name(String e_first_name) {
        this.e_first_name = e_first_name;
    }

    public String getE_last_name() {
        return e_last_name;
    }

    public void setE_last_name(String e_last_name) {
        this.e_last_name = e_last_name;
    }

    public String getE_date_of_birth() {
        return e_date_of_birth;
    }

    public void setE_date_of_birth(String e_date_of_birth) {
        this.e_date_of_birth = e_date_of_birth;
    }

    public String getE_address() {
        return e_address;
    }

    public void setE_address(String e_address) {
        this.e_address = e_address;
    }

    public String getE_phone_number() {
        return e_phone_number;
    }

    public void setE_phone_number(String e_phone_number) {
        this.e_phone_number = e_phone_number;
    }

    public String getE_hiring_date() {
        return e_hiring_date;
    }

    public void setE_hiring_date(String e_hiring_date) {
        this.e_hiring_date = e_hiring_date;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }
}
