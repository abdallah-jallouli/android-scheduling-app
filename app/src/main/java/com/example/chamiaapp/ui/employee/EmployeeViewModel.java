package com.example.chamiaapp.ui.employee;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chamiaapp.Models.Employee;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.Repository.EmployeeRepository;
import com.example.chamiaapp.Repository.ProductRepository;
import com.example.chamiaapp.Repository.TeamRepository;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {


    private EmployeeRepository emp_repository;
    private TeamRepository team_repository;
    private LiveData<List<Employee>> allemployees;
    private List<Employee> employeesByTeam;

    private LiveData<List<Team>>allTeams;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        emp_repository = new EmployeeRepository(application);
        allemployees = emp_repository.getAllEmployees();

        team_repository = new TeamRepository(application);
        allTeams = team_repository.getAllTeams();
    }

    public void insert(Employee employee) {
        emp_repository.insert(employee);
    }

    public void update(Employee employee) {
        emp_repository.update(employee);
    }

    public void delete(Employee employee) {
        emp_repository.delete(employee);
    }
    public LiveData<List<Employee>> getAllemployees() {
        return allemployees;
    }
    public LiveData<List<Employee>> getEmployeesByTeam(int teamId){return emp_repository.getEmployeesByTeamId(teamId);}



    public void insert(Team team) {
        team_repository.insert(team);
    }

    public void update(Team team) {
        team_repository.update(team);
    }

    public void delete(Team team) {
        team_repository.delete(team);
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }
    public LiveData<Team> getTeamById(int teamId) {
        return team_repository.getTeamById(teamId);
    }
}