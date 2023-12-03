package com.example.chamiaapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.ChamiaDatabase;
import com.example.chamiaapp.Dao.EmployeeDao;
import com.example.chamiaapp.Models.Employee;

import java.util.List;

public class EmployeeRepository {

    private EmployeeDao employeeDao;
    private LiveData<List<Employee>> allEmployees;


    @RequiresApi(api = Build.VERSION_CODES.O)
    public EmployeeRepository(Application application) {
        ChamiaDatabase database = ChamiaDatabase.getInstance(application);
        employeeDao = database.employeeDao();
        allEmployees = employeeDao.getAllEmployees();
    }

    public void insert(Employee employee) {
        new EmployeeRepository.InsertEmployeeAsyncTask(employeeDao).execute(employee);
    }

    public void update(Employee employee) {
        new EmployeeRepository.UpdateEmployeeAsyncTask(employeeDao).execute(employee);
    }

    public void delete(Employee employee) {
        new EmployeeRepository.DeleteEmployeeAsyncTask(employeeDao).execute(employee);
    }

    public LiveData<List<Employee>> getAllEmployees() {
        return allEmployees;
    }
    public LiveData<List<Employee>> getEmployeesByTeamId(int teamId) {
        return employeeDao.getEmployeesByTeamId(teamId);
    }

    private static class InsertEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;

        private InsertEmployeeAsyncTask(EmployeeDao employeeDao) {
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.insert(employees[0]);
            return null;
        }
    }

    private static class UpdateEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;

        private UpdateEmployeeAsyncTask(EmployeeDao employeeDao) {
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.update(employees[0]);
            return null;
        }
    }

    private static class DeleteEmployeeAsyncTask extends AsyncTask<Employee, Void, Void> {
        private EmployeeDao employeeDao;

        private DeleteEmployeeAsyncTask(EmployeeDao employeeDao) {
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            employeeDao.delete(employees[0]);
            return null;
        }
    }

}
