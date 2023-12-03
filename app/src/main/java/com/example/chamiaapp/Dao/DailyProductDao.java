package com.example.chamiaapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface DailyProductDao {


    @Insert
    void insert (DailyProduct dailyProduct);

    @Update
    void update (DailyProduct dailyProduct) ;

    @Delete
    void delete (DailyProduct dailyProduct);

    @Query("SELECT * FROM daily_product_table")
    LiveData<List<DailyProduct>> getAllDailyProducts();

    @Query("SELECT * FROM daily_product_table WHERE d_team_id = :teamId ORDER BY d_priority ASC")
    List<DailyProduct> getDailyProductsForTeam(int teamId);

    @Query("SELECT * FROM daily_product_table WHERE d_id = :d_id")
    LiveData<DailyProduct> getDailyProductById(int d_id);

    @Query("SELECT * FROM product_table " +
            "INNER JOIN daily_product_table ON product_table.pr_id = daily_product_table.d_product_id " +
            "WHERE daily_product_table.d_id = :dailyProductId")
    Single<Product>getProductByDailyProductId(int dailyProductId);

    @Query("SELECT * FROM team_table " +
            "INNER JOIN daily_product_table ON team_table.t_id = daily_product_table.d_team_id " +
            "WHERE daily_product_table.d_id = :dailyProductId")
    Single<Team> getTeamByDailyProductId(int dailyProductId);
}
