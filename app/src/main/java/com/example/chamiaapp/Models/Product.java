package com.example.chamiaapp.Models;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "product_table")
public class Product {
    @PrimaryKey(autoGenerate = true)
    int pr_id ;
    String pr_name ;
    int pr_weight ;
    int pr_cadence ;
    String pr_date_of_manufacture ;
    String pr_description ;
    byte[] pr_image ;

//    public Product(String pr_name, int pr_weight, int pr_cadence, String pr_date_of_manufacture, String pr_description, byte[] pr_image) {
//        this.pr_name = pr_name;
//        this.pr_weight = pr_weight;
//        this.pr_cadence = pr_cadence;
//        this.pr_date_of_manufacture = pr_date_of_manufacture;
//        this.pr_description = pr_description;
//        this.pr_image = pr_image;
//    }

    public Product(String pr_name, int pr_weight, int pr_cadence, String pr_date_of_manufacture, String pr_description) {
        this.pr_name = pr_name;
        this.pr_weight = pr_weight;
        this.pr_cadence = pr_cadence;
        this.pr_date_of_manufacture = pr_date_of_manufacture;
        this.pr_description = pr_description;
    }

    public Product() {
    }

    public int getPr_id() {
        return pr_id;
    }

    public void setPr_id(int pr_id) {
        this.pr_id = pr_id;
    }

    public String getPr_name() {
        return pr_name;
    }

    public void setPr_name(String pr_name) {
        this.pr_name = pr_name;
    }

    public int getPr_weight() {
        return pr_weight;
    }

    public void setPr_weight(int pr_weight) {
        this.pr_weight = pr_weight;
    }

    public int getPr_cadence() {
        return pr_cadence;
    }

    public void setPr_cadence(int pr_cadence) {
        this.pr_cadence = pr_cadence;
    }

    public String getPr_date_of_manufacture() {
        return pr_date_of_manufacture;
    }

    public void setPr_date_of_manufacture(String pr_date_of_manufacture) {
        this.pr_date_of_manufacture = pr_date_of_manufacture;
    }

    public String getPr_description() {
        return pr_description;
    }

    public void setPr_description(String pr_description) {
        this.pr_description = pr_description;
    }

    public byte[] getPr_image() {
        return pr_image;
    }

    public void setPr_image(byte[] pr_image) {
        this.pr_image = pr_image;
    }
}

