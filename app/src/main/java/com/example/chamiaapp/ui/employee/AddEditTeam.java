package com.example.chamiaapp.ui.employee;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.chamiaapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddEditTeam extends AppCompatActivity {

    public static final String EXTRA_TEAM_ID =
            "com.example.chamiaapp.EXTRA_TEAM_ID";
    public static final String EXTRA_TEAM_NAME =
            "com.example.chamiaapp.EXTRA_TEAM_NAME";
    public static final String EXTRA_BEGIN_TIME =
            "com.example.chamiaapp.EXTRA_BEGIN_TIME";
    public static final String EXTRA_TEAM_DESCRIPTION =
            "com.example.chamiaapp.EXTRA_TEAM_DESCRIPTION";

    TextInputLayout editTextName ;
    TextInputLayout editTextDescription ;
    TimePicker timePicker ;
    String selectedTime ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_team);


        editTextName = findViewById(R.id.team_name);
        editTextDescription = findViewById(R.id.team_description);
        timePicker= findViewById(R.id.timePicker);

        Toolbar team_toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(team_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_TEAM_ID)) {
            setTitle("Edit Team");
            editTextName.getEditText().setText(intent.getStringExtra(EXTRA_TEAM_NAME));
            // Définissez le format de l'heure
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");


            try {
                // Analysez la chaîne en objet Date
                Date date = format.parse(intent.getStringExtra(EXTRA_BEGIN_TIME));

                // Créez un objet Calendar et définissez l'heure et les minutes
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                // Obtenez l'heure et les minutes de l'objet Calendar
                //int heureDuPicker = 5;
                int heureDuPicker = calendar.get(Calendar.HOUR_OF_DAY);
                int minutesDuPicker = calendar.get(Calendar.MINUTE);

                // Utilisez l'heure et les minutes pour paramétrer le TimePicker
                //TimePicker timePicker = findViewById(R.id.timePicker);
                timePicker.setHour(heureDuPicker);
                timePicker.setMinute(minutesDuPicker);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            editTextDescription.getEditText().setText(intent.getStringExtra(EXTRA_TEAM_DESCRIPTION));

        } else {
            setTitle("Add Team");
        }

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                //selectedTime = hourOfDay + ":" + minute;
                selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
            }
        });
    } // fin on create


    // validate team name
    private Boolean validateTeamName() {
        String val = editTextName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if (val.isEmpty()) {
            editTextName.setError("Field cannot be empty");
            return false;}
        else {
            editTextName.setError(null);
            editTextName.setErrorEnabled(false);
            return true;
        }
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
            if (!validateTeamName() ){
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

    private void saveProduct() {
        String name = editTextName.getEditText().getText().toString();
        String description = editTextDescription.getEditText().getText().toString();

        if (name.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please insert a name and description", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_TEAM_NAME, name);
        if (selectedTime != null){
            data.putExtra(EXTRA_BEGIN_TIME, selectedTime);
        }
        else {
            data.putExtra(EXTRA_BEGIN_TIME, "07:00");
        }

        data.putExtra(EXTRA_TEAM_DESCRIPTION, description);


        int id = getIntent().getIntExtra(EXTRA_TEAM_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_TEAM_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();
    }


}