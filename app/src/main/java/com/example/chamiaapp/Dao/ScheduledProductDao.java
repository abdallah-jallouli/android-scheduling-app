package com.example.chamiaapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalTime;
import java.util.List;


import com.example.chamiaapp.Models.ScheduledProduct;

import io.reactivex.rxjava3.core.Single;

@Dao
public interface ScheduledProductDao {


    @Insert
    void insert (ScheduledProduct scheduledProduct);

    @Update
    void update (ScheduledProduct scheduledProduct) ;

    @Delete
    void delete (ScheduledProduct scheduledProduct);

    @Query("SELECT * FROM scheduled_product_table ORDER BY sch_start_time ASC")
    LiveData<List<ScheduledProduct>> getAllScheduledProducts() ;
    @Query("DELETE FROM scheduled_product_table")
    void deleteAllScheduledProduct();
    @Query("SELECT sp.* FROM (SELECT dp.d_team_id, MIN(sp.sch_start_time) AS minBeginTime " +
            "FROM scheduled_product_table sp JOIN daily_product_table dp ON sp.sch_daily_product_id = dp.d_product_id " +
            "GROUP BY dp.d_team_id) minTimes " +
            "JOIN team_table t ON minTimes.d_team_id = t.t_id " +
            "JOIN scheduled_product_table sp ON sp.sch_start_time = minTimes.minBeginTime " +
            "AND sp.sch_daily_product_id IN (SELECT dp.d_product_id FROM daily_product_table dp " +
            "WHERE dp.d_team_id = minTimes.d_team_id)")
    Single<List<ScheduledProduct>> getFirstScheduledProducts();

//    @Query ("SELECT sp.*" +
//            "FROM (" +
//            "  SELECT dp.d_team_id, MIN(sp.sch_start_time) AS minBeginTime" +
//            "  FROM scheduled_product_table sp" +
//            "  JOIN daily_product_table dp ON sp.sch_daily_product_id = dp.d_product_id" +
//            "  GROUP BY dp.d_team_id" +
//            ") minTimes" +
//            " JOIN team_table t ON minTimes.d_team_id = t.t_id" +
//            " JOIN scheduled_product_table sp ON sp.sch_start_time = minTimes.minBeginTime" +
//            "                       AND sp.sch_daily_product_id IN (" +
//            "                       SELECT dp.d_product_id" +
//            "                       FROM daily_product_table dp" +
//            "                       WHERE dp.d_team_id = minTimes.d_team_id" +
//            "                       )")
//    List<ScheduledProduct> getFirst();


    // we start the schedule with selecting scheduleProdut which have the minimum start time.
    @Query("SELECT *" +
            "FROM scheduled_product_table" +
            " WHERE sch_start_time = (" +
            "  SELECT MIN(sch_start_time)" +
            "  FROM scheduled_product_table" +
            ")")
    List<ScheduledProduct> startSchedule();

    @Query("SELECT sp.sch_id, sp.sch_status,sp.sch_delay , sp.sch_start_time ,sp.sch_daily_product_id" +
            " FROM scheduled_product_table sp" +
            " INNER JOIN daily_product_table dp ON sp.sch_daily_product_id = dp.d_id" +
            " WHERE dp.d_team_id = (" +
            "    SELECT d_team_id" +
            "    FROM daily_product_table" +
            "    WHERE d_id = (" +
            "        SELECT sch_daily_product_id" +
            "        FROM scheduled_product_table" +
            "        WHERE sch_id = :scheduledProductId" +
            "    )" +
            ")" +
            "AND sp.sch_start_time > :time " +
            " ORDER BY sp.sch_start_time ASC" +
            " LIMIT 1;")
    ScheduledProduct getJustAfterScheduledProduct( int scheduledProductId,LocalTime time);

    @Query("SELECT sp.sch_id, sp.sch_status,sp.sch_delay , sp.sch_start_time ,sp.sch_daily_product_id" +
            " FROM scheduled_product_table sp" +
            " INNER JOIN daily_product_table dp ON sp.sch_daily_product_id = dp.d_id" +
            " WHERE dp.d_team_id = (" +
            "    SELECT d_team_id" +
            "    FROM daily_product_table" +
            "    WHERE d_id = (" +
            "        SELECT sch_daily_product_id" +
            "        FROM scheduled_product_table" +
            "        WHERE sch_id = :scheduledProductId" +
            "    )" +
            ")" +
            "AND sp.sch_start_time > :time " +
            " ORDER BY sp.sch_start_time ASC")
    List<ScheduledProduct> getAllAfterScheduledProduct (int scheduledProductId,LocalTime time );


    @Query("SELECT * FROM scheduled_product_table " +
            "WHERE sch_start_time < :time " +
            "AND sch_status = 'en attente'")
    List<ScheduledProduct> getScheduledProductsBeforeTime(LocalTime time);

    @Query("SELECT * FROM scheduled_product_table " +
            "WHERE sch_start_time > :time " +
            "AND sch_status = 'en attente' ")
    List<ScheduledProduct> getAllAfterForBreakDown (LocalTime time );

}
