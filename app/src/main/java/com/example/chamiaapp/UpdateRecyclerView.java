package com.example.chamiaapp;

import androidx.lifecycle.LiveData;

import com.example.chamiaapp.Models.Employee;

import java.util.List;

public interface UpdateRecyclerView {
    public void callback(int position , List<Employee> employees);
}
