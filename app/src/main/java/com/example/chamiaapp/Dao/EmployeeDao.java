package com.example.chamiaapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.example.chamiaapp.Models.Employee;

@Dao
public interface EmployeeDao {

    @Insert   //(onConflict = OnConflictStrategy.IGNORE)
    void insert (Employee employee);

    @Update
    void update (Employee employee) ;

    @Delete
    void delete (Employee employee);

    @Query("SELECT * FROM employee_table")
    LiveData<List<Employee>> getAllEmployees();

    @Query("SELECT * FROM employee_table WHERE team_id = :teamId")
    LiveData<List<Employee>> getEmployeesByTeamId(int teamId);
}
