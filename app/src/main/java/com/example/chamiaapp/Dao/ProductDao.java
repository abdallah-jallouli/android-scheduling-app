package com.example.chamiaapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.chamiaapp.Models.Product;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ProductDao {

    @Insert
    void insert (Product product);

    @Update
    void update (Product product) ;

    @Delete
    void delete (Product product);

    @Query("SELECT * FROM product_table")
    LiveData<List<Product>> getAllProducts() ;

    @Query("SELECT * FROM product_table WHERE pr_id = :pr_id")
    LiveData<Product> getProductById(int pr_id);

    @Query("SELECT * FROM product_table WHERE pr_id = :pr_id")
    Product getNotLiveProductById(int pr_id);



}
