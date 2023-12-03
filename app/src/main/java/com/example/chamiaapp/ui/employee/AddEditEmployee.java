package com.example.chamiaapp.ui.employee;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AddEditEmployee extends AppCompatActivity {

    public static final String EXTRA_EMPLOYEE_ID =
            "com.example.chamiaapp.EXTRA_EMPLOYEE_ID";
    public static final String EXTRA_FIRST_NAME =
            "com.example.chamiaapp.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME =
            "com.example.chamiaapp.EXTRA_LAST_NAME";
    public static final String EXTRA_PHONE_NUMBER =
            "com.example.chamiaapp.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_DATE_OF_BIRTH =
            "com.example.chamiaapp.EXTRA_DATE_OF_BIRTH";
    public static final String EXTRA_ADDRESS =
            "com.example.chamiaapp.EXTRA_ADDRESS";
    public static final String EXTRA_HIRING_DATE =
            "com.example.chamiaapp.EXTRA_HIRING_DATE";
    public static final String EXTRA_TEAM_ID =
            "com.example.chamiaapp.EXTRA_TEAM_ID";

    private TextInputLayout editTextFirstName, editTextLastName, editTextPhoneNumber, editTextDateOfBirth,
            editTextAddress, editTextHiringDate;
    private Spinner spinner;

    List<Team> teamList = new ArrayList<>();
    private EmployeeViewModel employeeViewModel ;
    private Team selectedTeam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_employee);

        employeeViewModel = ViewModelProviders.of(this).get(EmployeeViewModel.class);

        editTextFirstName = findViewById(R.id.e_first_name);
        editTextLastName = findViewById(R.id.e_last_name);
        editTextPhoneNumber = findViewById(R.id.e_edit_phone);
        editTextDateOfBirth = findViewById(R.id.e_dateOfBirth);
        editTextAddress = findViewById(R.id.e_address);
        editTextHiringDate = findViewById(R.id.e_hiringDate);
        spinner = findViewById(R.id.teamSpinner);

        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar1);

        // make the tool bar and put it into actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        // handling with spinner

        employeeViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                // Create an ArrayList to store the team names
                ArrayList<String> teamNames = new ArrayList<>();

                // Iterate through the list of teams and retrieve their names
                for (Team team : teams) {
                    String teamName = team.getT_name();
                    teamNames.add(teamName);
                }

                // Use the teamNames ArrayList to populate your spinner
                ArrayAdapter<String> adapter = new ArrayAdapter<>(AddEditEmployee.this, android.R.layout.simple_spinner_item, teamNames);
                spinner.setAdapter(adapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected team object
                        selectedTeam = teams.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });


        // end handling with spinner

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_EMPLOYEE_ID)) {
            setTitle("Edit Employee");
            editTextFirstName.getEditText().setText(intent.getStringExtra(EXTRA_FIRST_NAME));
            editTextLastName.getEditText().setText(intent.getStringExtra(EXTRA_LAST_NAME));
            editTextPhoneNumber.getEditText().setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));
            editTextDateOfBirth.getEditText().setText(intent.getStringExtra(EXTRA_DATE_OF_BIRTH));
            editTextAddress.getEditText().setText(intent.getStringExtra(EXTRA_ADDRESS));
            editTextHiringDate.getEditText().setText(intent.getStringExtra(EXTRA_HIRING_DATE));

        } else {
            setTitle("Add Employee");
        }

    } // fin on create

    //validate the name
    private Boolean validateUsername() {
        String val = editTextFirstName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            editTextFirstName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            editTextFirstName.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            editTextFirstName.setError("White Spaces are not allowed");
            return false;
        } else {
            editTextFirstName.setError(null);
            editTextFirstName.setErrorEnabled(false);
            return true;
        }
    }


    //validate the name
    private Boolean validateLastName() {
        String val = editTextLastName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            editTextLastName.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            editTextLastName.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            editTextLastName.setError("White Spaces are not allowed");
            return false;
        } else {
            editTextLastName.setError(null);
            editTextLastName.setErrorEnabled(false);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveProduct() {
        String first_name = editTextFirstName.getEditText().getText().toString();
        String last_name = editTextLastName.getEditText().getText().toString();
        String phone_number = editTextPhoneNumber.getEditText().getText().toString();
        String date_of_birth = editTextDateOfBirth.getEditText().getText().toString();
        String address = editTextAddress.getEditText().getText().toString();
        String hiring_date = editTextHiringDate.getEditText().getText().toString();



        if (first_name.trim().isEmpty() || last_name.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the full name", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_FIRST_NAME, first_name);
        data.putExtra(EXTRA_LAST_NAME, last_name);
        data.putExtra(EXTRA_PHONE_NUMBER, phone_number);
        data.putExtra(EXTRA_DATE_OF_BIRTH, date_of_birth);
        data.putExtra(EXTRA_ADDRESS, address);
        data.putExtra(EXTRA_HIRING_DATE, hiring_date);
        data.putExtra(EXTRA_TEAM_ID,selectedTeam.getT_id());

        int id = getIntent().getIntExtra(EXTRA_EMPLOYEE_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_EMPLOYEE_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_note) {
            if (!validateUsername() | !validateLastName()){
                return false ;
            }
            else {
                saveProduct();
                return true;
            }
        }else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

