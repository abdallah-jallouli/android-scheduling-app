package com.example.chamiaapp.ui.employee;

import static android.app.Activity.RESULT_OK;

import android.app.Application;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Controller.EmployeeAdapter;
import com.example.chamiaapp.Controller.ProductAdapter;
import com.example.chamiaapp.Controller.TeamAdapter;
import com.example.chamiaapp.Converters;
import com.example.chamiaapp.Models.Employee;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.Repository.EmployeeRepository;
import com.example.chamiaapp.UpdateRecyclerView;
import com.example.chamiaapp.databinding.FragmentDailyProductBinding;
import com.example.chamiaapp.databinding.FragmentProductBinding;
import com.example.chamiaapp.ui.product.AddEditProduct;
import com.example.chamiaapp.ui.product.ProductViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class EmployeeFragment extends Fragment implements UpdateRecyclerView {


    public static final int ADD_EMPLOYEE_REQUEST = 1;
    public static final int EDIT_EMPLOYEE_REQUEST = 2;
    public static final int ADD_TEAM_REQUEST = 3;
    public static final int EDIT_TEAM_REQUEST = 4;
    private Button performance_btn;
    private FloatingActionButton addEmployee_fab , addTeam_fab ;
    private RecyclerView employee_rv , team_rv ;
    public EmployeeAdapter employeeAdapter ;
    public  static TeamAdapter teamAdapter ;
    private EmployeeViewModel employeeViewModel ;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);
        employeeViewModel.getAllTeams().observe(getViewLifecycleOwner(), new Observer<List<Team>>() {
            @Override
            public void onChanged(@Nullable List<Team> teams) {
                teamAdapter.submitList(teams);
            }
        });

        employeeViewModel.getAllemployees().observe(getViewLifecycleOwner(), new Observer<List<Employee>>() {
            @Override
            public void onChanged(@Nullable List<Employee> employees) {
                employeeAdapter.submitList(employees);
            }
        });

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        team_rv = view.findViewById(R.id.team_rv);
        employee_rv = view.findViewById(R.id.employee_rv);
        addEmployee_fab =view.findViewById(R.id.fab_add_employee);
        addTeam_fab =view.findViewById(R.id.fab_add_team);
        performance_btn =view.findViewById(R.id.performance_btn);


        addTeam_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditTeam.class);
                startActivityForResult(intent,ADD_TEAM_REQUEST);
            }
        });

        addEmployee_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditEmployee.class);
                startActivityForResult(intent,ADD_EMPLOYEE_REQUEST);
            }
        });

//        performance_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(getContext(), AddEmployee.class);
////                startActivity(intent);
//            }
//        });

        teamAdapter = new TeamAdapter(getContext(), employeeViewModel , this);
        team_rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        team_rv.setAdapter(teamAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.UP| ItemTouchHelper.DOWN) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                employeeViewModel.delete(teamAdapter.getTeamAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Team deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(team_rv);

//        teamAdapter.setOnItemClickListener(new TeamAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(Team team) {
//                Intent intent = new Intent(getContext(), AddEditTeam.class);
//                intent.putExtra(AddEditTeam.EXTRA_TEAM_ID, team.getT_id());
//                intent.putExtra(AddEditTeam.EXTRA_TEAM_NAME, team.getT_name());
//                intent.putExtra(AddEditTeam.EXTRA_BEGIN_TIME, String.valueOf(team.getT_begin_time()));
//                intent.putExtra(AddEditTeam.EXTRA_TEAM_DESCRIPTION, team.getT_description());
//                //Toast.makeText(getContext(), String.valueOf(team.getT_begin_time()), Toast.LENGTH_SHORT).show();
//
//                startActivityForResult(intent, EDIT_TEAM_REQUEST);
//            }
//
//        });
        teamAdapter.setOnLongItemClickListener(new TeamAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(Team team) {
                Intent intent = new Intent(getContext(), AddEditTeam.class);
                intent.putExtra(AddEditTeam.EXTRA_TEAM_ID, team.getT_id());
                intent.putExtra(AddEditTeam.EXTRA_TEAM_NAME, team.getT_name());
                intent.putExtra(AddEditTeam.EXTRA_BEGIN_TIME, String.valueOf(team.getT_begin_time()));
                intent.putExtra(AddEditTeam.EXTRA_TEAM_DESCRIPTION, team.getT_description());
                //Toast.makeText(getContext(), String.valueOf(team.getT_begin_time()), Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, EDIT_TEAM_REQUEST);
            }
        });

        employeeAdapter = new EmployeeAdapter(this.getContext() , employeeViewModel);
        employee_rv.setLayoutManager(new LinearLayoutManager(getContext()));
        employee_rv.setHasFixedSize(true);
        employee_rv.setAdapter(employeeAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT| ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                employeeViewModel.delete(employeeAdapter.getEmployeeAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Employee deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(employee_rv);

        employeeAdapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Employee employee) {
                Intent intent = new Intent(getContext(), AddEditEmployee.class);
                intent.putExtra(AddEditEmployee.EXTRA_EMPLOYEE_ID, employee.getE_id());
                intent.putExtra(AddEditEmployee.EXTRA_FIRST_NAME, employee.getE_first_name());
                intent.putExtra(AddEditEmployee.EXTRA_LAST_NAME, employee.getE_last_name());
                intent.putExtra(AddEditEmployee.EXTRA_PHONE_NUMBER, employee.getE_phone_number());
                intent.putExtra(AddEditEmployee.EXTRA_HIRING_DATE, employee.getE_hiring_date());
                intent.putExtra(AddEditEmployee.EXTRA_ADDRESS, employee.getE_address());
                intent.putExtra(AddEditEmployee.EXTRA_DATE_OF_BIRTH, employee.getE_date_of_birth());
                intent.putExtra(AddEditEmployee.EXTRA_TEAM_ID, employee.getTeam_id());
                //Toast.makeText(getContext(), String.valueOf(team.getT_begin_time()), Toast.LENGTH_SHORT).show();

                startActivityForResult(intent, EDIT_EMPLOYEE_REQUEST);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TEAM_REQUEST && resultCode == RESULT_OK) {

            String name = data.getStringExtra(AddEditTeam.EXTRA_TEAM_NAME);
            String begin_time = data.getStringExtra(AddEditTeam.EXTRA_BEGIN_TIME);
            String description = data.getStringExtra(AddEditTeam.EXTRA_TEAM_DESCRIPTION);

            Team team  = new Team(name, Converters.fromStringtoLocalTime(begin_time),description);
            employeeViewModel.insert(team);
            Toast.makeText(getContext(), "Team saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_TEAM_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditTeam.EXTRA_TEAM_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Team can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditTeam.EXTRA_TEAM_NAME);
            String begin_time = data.getStringExtra(AddEditTeam.EXTRA_BEGIN_TIME);
            String description = data.getStringExtra(AddEditTeam.EXTRA_TEAM_DESCRIPTION);

            Team team  = new Team(name, Converters.fromStringtoLocalTime(begin_time),description);
            team.setT_id(id);
            employeeViewModel.update(team);
            Toast.makeText(getContext(), "Team updated", Toast.LENGTH_SHORT).show();

        } else if (requestCode == ADD_EMPLOYEE_REQUEST && resultCode == RESULT_OK){
            String first_name = data.getStringExtra(AddEditEmployee.EXTRA_FIRST_NAME);
            String last_name = data.getStringExtra(AddEditEmployee.EXTRA_LAST_NAME);
            String phone_number = data.getStringExtra(AddEditEmployee.EXTRA_PHONE_NUMBER);
            String date_of_birth = data.getStringExtra(AddEditEmployee.EXTRA_HIRING_DATE);
            String address = data.getStringExtra(AddEditEmployee.EXTRA_ADDRESS);
            String hiring_date = data.getStringExtra(AddEditEmployee.EXTRA_DATE_OF_BIRTH);
            int team_id = data.getIntExtra(AddEditEmployee.EXTRA_TEAM_ID,-1);



            Employee employee  = new Employee(first_name,last_name,date_of_birth,address,phone_number,hiring_date,team_id);
            employeeViewModel.insert(employee);
            Toast.makeText(getContext(), "Employee saved", Toast.LENGTH_SHORT).show();

        } else if (requestCode == EDIT_EMPLOYEE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditEmployee.EXTRA_EMPLOYEE_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Employee can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String first_name = data.getStringExtra(AddEditEmployee.EXTRA_FIRST_NAME);
            String last_name = data.getStringExtra(AddEditEmployee.EXTRA_LAST_NAME);
            String phone_number = data.getStringExtra(AddEditEmployee.EXTRA_PHONE_NUMBER);
            String date_of_birth = data.getStringExtra(AddEditEmployee.EXTRA_HIRING_DATE);
            String address = data.getStringExtra(AddEditEmployee.EXTRA_ADDRESS);
            String hiring_date = data.getStringExtra(AddEditEmployee.EXTRA_DATE_OF_BIRTH);
            int team_id = data.getIntExtra(AddEditEmployee.EXTRA_TEAM_ID,-1);



            Employee employee  = new Employee(first_name,last_name,date_of_birth,address,phone_number,hiring_date,team_id);
            employee.setE_id(id);
            employeeViewModel.update(employee);
            Toast.makeText(getContext(), "Employee updated", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getContext(), "No action", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void callback(int position, List<Employee> employees) {
        employeeAdapter.submitList(employees);
    }


}