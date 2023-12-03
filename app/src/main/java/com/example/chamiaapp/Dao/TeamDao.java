package com.example.chamiaapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.chamiaapp.Models.Team;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface TeamDao {

    @Insert
    void insert (Team team);

    @Update
    void update (Team team) ;

    @Delete
    void delete (Team team);

    @Query("SELECT * FROM team_table")
    LiveData<List<Team>> getAllTeams() ;

    @Query("SELECT * FROM team_table WHERE t_id = :teamId")
    LiveData<Team> getTeamById(int teamId);

    @Query("SELECT * FROM team_table WHERE t_id = :teamId")
    Team getNotLiveTeamById(int teamId);
    @Query("SELECT * FROM team_table WHERE t_id IN (SELECT d_team_id FROM daily_product_table)")
    List<Team> getInWorkTeams();

}
