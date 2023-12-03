package com.example.chamiaapp.ui.dailyProduct;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.ui.employee.AddEditEmployee;
import com.example.chamiaapp.ui.employee.EmployeeViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AddEditDailyProduct extends AppCompatActivity {

    public static final String EXTRA_DAILY_ID =
            "com.example.chamiaapp.EXTRA_DAILY_ID";
    public static final String EXTRA_DAILY_DATE =
            "com.example.chamiaapp.EXTRA_DAILY_DATE";
    public static final String EXTRA_PRODUCT_ID =
            "com.example.chamiaapp.EXTRA_PRODUCT_ID";
    public static final String EXTRA_TEAM_ID =
            "com.example.chamiaapp.EXTRA_TEAM_ID";
    public static final String EXTRA_NUMBER_PREPARATION =
            "com.example.chamiaapp.EXTRA_NUMBER_PREPARATION";
    public static final String EXTRA_PRIORITY =
            "com.example.chamiaapp.EXTRA_PRIORITY";

    private CalendarView calendarView  ;
    Spinner product_spinner , team_spinner ;
    private TextInputLayout number_of_cooks;
    private NumberPicker numberPickerPriority ;
    private String dateString ;
    DailyProductViewModel dailyProductViewModel ;
    Product selectedProduct ;
    Team selectedTeam ;
    List<Product> productList = new ArrayList<>();






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_daily_product);

        dailyProductViewModel = ViewModelProviders.of(this).get(DailyProductViewModel.class);

        calendarView = findViewById(R.id.calendarView);
        product_spinner = findViewById(R.id.dp_product_spinner);
        team_spinner = findViewById(R.id.dp_team_spinner);
        number_of_cooks = findViewById(R.id.dp_numb_cooks);
        numberPickerPriority = findViewById(R.id.number_picker_priority);

        Toolbar toolbar;
        toolbar = findViewById(R.id.toolbar1);

        // make the tool bar and put it into actionbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        // Handling with CalenderView
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //dateString = year+"-"+(month+1) +"-"+dayOfMonth  ;
                dateString = String.format(Locale.getDefault(),"%04d-%02d-%02d",year , (month+1), dayOfMonth);
            }
        });

        Intent intent = getIntent();

        // Handling with Product Spinner
        if(intent.hasExtra(EXTRA_PRODUCT_ID)) {
            //Log.d("message: ", String.valueOf(intent.getIntExtra(EXTRA_PRODUCT_ID, -1)));
            dailyProductViewModel.getProductById(intent.getIntExtra(EXTRA_PRODUCT_ID,-1)).observe(this , new Observer<Product>() {
                @Override
                public void onChanged(Product product) {
                    selectedProduct = product;
                    //Log.d("message", selectedProduct.getPr_name());
                }
            });
        }


        dailyProductViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(List<Product> products) {
                // put products to the productList
                productList.addAll(products);

                // Create an ArrayList to store the team names
                ArrayList<String> productNames = new ArrayList<>();

                // Iterate through the list of teams and retrieve their names
                for (Product product : products) {
                    String teamName = product.getPr_name();
                    productNames.add(teamName);
                }

                // Use the teamNames ArrayList to populate your spinner
                ArrayAdapter<String> productArrayAdapter = new ArrayAdapter<>(AddEditDailyProduct.this, android.R.layout.simple_spinner_item, productNames);
                product_spinner.setAdapter(productArrayAdapter);
                if(intent.hasExtra(EXTRA_TEAM_ID) && selectedTeam != null ){
                    product_spinner.setSelection(productArrayAdapter.getPosition(selectedProduct.getPr_name()));
                }
                else {
                    //Log.d("message", "selected product is null");
                }

                product_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Get the selected team object
                        selectedProduct = products.get(position);
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }
        });

        // Handling with Team Spinner
        if(intent.hasExtra(EXTRA_TEAM_ID)) {
            //Log.d("message: ", String.valueOf(intent.getIntExtra(EXTRA_TEAM_ID, -1)));
            dailyProductViewModel.getTeamById(intent.getIntExtra(EXTRA_TEAM_ID,-1)).observe(this , new Observer<Team>() {
                @Override
                public void onChanged(Team team) {
                    selectedTeam = team;
                }
            });
        }


        dailyProductViewModel.getAllTeams().observe(this, new Observer<List<Team>>() {
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
                ArrayAdapter<String> teamArrayAdapter = new ArrayAdapter<>(AddEditDailyProduct.this, android.R.layout.simple_spinner_item, teamNames);
                team_spinner.setAdapter(teamArrayAdapter);

                if(intent.hasExtra(EXTRA_TEAM_ID) && selectedTeam != null ){
                    team_spinner.setSelection(teamArrayAdapter.getPosition(selectedTeam.getT_name()));
                }
                else {
                    Log.d("message", "selected team is null");
                }

                team_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        // Handling with Number Picker
        numberPickerPriority.setMinValue(1);
        numberPickerPriority.setMaxValue(10);


        //Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_DAILY_ID)) {
            setTitle("Edit Daily Product");
            if(intent.hasExtra(EXTRA_NUMBER_PREPARATION)){
                number_of_cooks.getEditText().setText(String.valueOf(intent.getIntExtra(EXTRA_NUMBER_PREPARATION,1)));
            }
            if(intent.getStringExtra(EXTRA_DAILY_DATE)!= null){
                //calendarView.setDate(Long.parseLong(intent.getStringExtra(EXTRA_DAILY_DATE)));
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

                try {
                    Date date = dateFormat.parse(intent.getStringExtra(EXTRA_DAILY_DATE));
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(date.getTime());

                    // Now you can set the calendar view to the desired date
                    calendarView.setDate(calendar.getTimeInMillis());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            numberPickerPriority.setValue(intent.getIntExtra(EXTRA_PRIORITY,1));

        } else {
            setTitle("Add Daily Product");
        }








    } // Fin on create


    // validation of number of cooks
    private Boolean validateNoCooks() {
        String val = number_of_cooks.getEditText().getText().toString();
        String digitPattern = "^[0-9]+$";

        if (val.isEmpty()) {
            number_of_cooks.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(digitPattern)) {
            number_of_cooks.setError("Field does contain only digits");
            return false;
        }
        else
        {
            number_of_cooks.setError(null);
            number_of_cooks.setErrorEnabled(false);
            return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveDailyProduct() {

        int product_id = selectedProduct.getPr_id();
        int team_id = selectedTeam.getT_id();
        int number_cooks = Integer.parseInt(number_of_cooks.getEditText().getText().toString());
        int priority = numberPickerPriority.getValue();

//        if (number_of_cooks.isE()) {
//            Toast.makeText(this, "Please insert the full name", Toast.LENGTH_SHORT).show();
//            return;
//        }

        Intent data = new Intent();
        if (dateString != null ){
            data.putExtra(EXTRA_DAILY_DATE, dateString);
        }
        else {
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // January is 0
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            // Print the current date
            dateString = String.format("%04d-%02d-%02d", year, month, day);
            data.putExtra(EXTRA_DAILY_DATE, dateString);
            Log.d("the current date: ", dateString);
        }

        data.putExtra(EXTRA_PRODUCT_ID, product_id);
        data.putExtra(EXTRA_TEAM_ID, team_id);
        data.putExtra(EXTRA_NUMBER_PREPARATION, number_cooks);
        data.putExtra(EXTRA_PRIORITY, priority);


        int id = getIntent().getIntExtra(EXTRA_DAILY_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_DAILY_ID, id);
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
            if (!validateNoCooks()){
                return false ;
            }
            else {
                saveDailyProduct();
                return true;
            }
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}