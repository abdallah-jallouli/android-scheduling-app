package com.example.chamiaapp.ui.schedule;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chamiaapp.Controller.ProductAdapter;
import com.example.chamiaapp.Controller.ScheduleAdapter;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.ui.Login_page1;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainSchedule extends AppCompatActivity {
    Button startButton  ;
    RecyclerView recyclerView ;
    RadioGroup radioGroup ;
    ImageView image_plus , image_minus ;
    TextView numberOfMinute ;
    private ScheduleViewModel scheduleViewModel;
    private ScheduleAdapter adapter ;
    private static final String TAG = "MainSchedule";
    Handler handler = new Handler();
    private int counter = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_schedule);

        startButton = findViewById(R.id.submit_button);
        radioGroup =findViewById(R.id.radio_group);
        image_plus =findViewById(R.id.add_image);
        image_minus =findViewById(R.id.remove_image);
        numberOfMinute =findViewById(R.id.num_minute);
        recyclerView =findViewById(R.id.recyclerView);

        scheduleViewModel = ViewModelProviders.of(this).get(ScheduleViewModel.class);
        adapter = new ScheduleAdapter(this , scheduleViewModel,numberOfMinute);
        scheduleViewModel.getAllScheudledProducts().observe(this, new Observer<List<ScheduledProduct>>() {
            @Override
            public void onChanged(List<ScheduledProduct> scheduledProducts) {
                adapter.submitList(scheduledProducts);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (startButton.getText().toString().equals("Start")){
                    BeginRunnable runnable  = new BeginRunnable();
                    new Thread(runnable).start();
                    startButton.setText("Finish");
                }
                else {
                    Intent intent = new Intent(MainSchedule.this , Login_page1.class);
                    startActivity(intent);
                    startButton.setText("Start");

                }
            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                numberOfMinute.setText(String.valueOf(counter));
                return true;
            }
        });

        // Set click listener for the plus button
        image_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                handler.sendEmptyMessage(0);
            }
        });

        // Set click listener for the minus button
        image_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                handler.sendEmptyMessage(0);
            }
        });




    }

    class BeginRunnable implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            List<ScheduledProduct> firstScheduledProducts ;
            firstScheduledProducts = scheduleViewModel.getFirstScheduledProductsForEachTeam();
            for (ScheduledProduct scheduledProduct : firstScheduledProducts){
                scheduledProduct.setSch_status("en cours");
                scheduleViewModel.update(scheduledProduct);
            }
        }
    }

    public int getSelectedRadioButtonId() {
        return radioGroup.getCheckedRadioButtonId();
    }






}