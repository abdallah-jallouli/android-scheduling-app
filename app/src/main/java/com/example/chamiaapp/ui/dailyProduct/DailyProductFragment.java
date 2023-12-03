package com.example.chamiaapp.ui.dailyProduct;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Controller.DailyProductAdapter;
import com.example.chamiaapp.Converters;
import com.example.chamiaapp.MainActivity;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.databinding.FragmentDailyProductBinding;
import com.example.chamiaapp.ui.schedule.MainSchedule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

public class DailyProductFragment extends Fragment {

    public static final int ADD_DAILY_PRODUCT_REQUEST = 1;
    public static final int EDIT_DAILY_PRODUCT_REQUEST = 2;

    private DailyProductViewModel dailyProductViewModel;

    private FragmentDailyProductBinding binding;

    DailyProductAdapter adapter;
    private Handler mainHandler = new Handler();




    private static final String TAG = "DailyProductFragment";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dailyProductViewModel = ViewModelProviders.of(this).get(DailyProductViewModel.class);
        adapter = new DailyProductAdapter(getContext(), dailyProductViewModel);
        dailyProductViewModel.getAllDailyProducts().observe(getViewLifecycleOwner(), new Observer<List<DailyProduct>>() {
            @Override
            public void onChanged(@Nullable List<DailyProduct> dailyProducts) {
                adapter.submitList(dailyProducts);
            }
        });

        binding = FragmentDailyProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FloatingActionButton buttonAddDailyProduct = view.findViewById(R.id.fab_add_daily_product);
        buttonAddDailyProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEditDailyProduct.class);
                startActivityForResult(intent, ADD_DAILY_PRODUCT_REQUEST);
            }
        });

        FloatingActionButton intoSchedule = view.findViewById(R.id.fab_IntoSchedule);
        intoSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainSchedule.class);
                startActivity(intent);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.daily_product_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                dailyProductViewModel.delete(adapter.getDailyProductAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DailyProductAdapter.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(DailyProduct dailyProduct) {
                Intent intent = new Intent(getContext(), AddEditDailyProduct.class);
                intent.putExtra(AddEditDailyProduct.EXTRA_DAILY_ID, dailyProduct.getD_id());
                intent.putExtra(AddEditDailyProduct.EXTRA_DAILY_DATE, Converters.toStringDate(dailyProduct.getD_date()));
                intent.putExtra(AddEditDailyProduct.EXTRA_PRODUCT_ID, dailyProduct.getD_product_id());
                intent.putExtra(AddEditDailyProduct.EXTRA_TEAM_ID, dailyProduct.getD_team_id());
                intent.putExtra(AddEditDailyProduct.EXTRA_NUMBER_PREPARATION, dailyProduct.getD_number_of_cooks());
                intent.putExtra(AddEditDailyProduct.EXTRA_PRIORITY, dailyProduct.getD_priority());

                startActivityForResult(intent, EDIT_DAILY_PRODUCT_REQUEST);
            }
        });

        // Button create scheudle

        Button createScheduleButton = view.findViewById(R.id.create_schedule_btn);
        createScheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // getInWorkTeam();
                //dailyProductViewModel.deleteAllScheduledProduct();
                GenerateSchedule runnable = new GenerateSchedule();
                new Thread(runnable).start();
                Toast.makeText(getContext() , "SCHEDULE GENERATED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_DAILY_PRODUCT_REQUEST && resultCode == RESULT_OK) {
            String date = data.getStringExtra(AddEditDailyProduct.EXTRA_DAILY_DATE);
            int product_id = data.getIntExtra(AddEditDailyProduct.EXTRA_PRODUCT_ID, 1);
            int team_id = data.getIntExtra(AddEditDailyProduct.EXTRA_TEAM_ID, 1);
            int number_of_preperation = data.getIntExtra(AddEditDailyProduct.EXTRA_NUMBER_PREPARATION ,1);
            int priority = data.getIntExtra(AddEditDailyProduct.EXTRA_PRIORITY,1);


            DailyProduct dailyProduct = new DailyProduct(Converters.fromStringToLocaDate(date), product_id, team_id,number_of_preperation,priority);
            dailyProductViewModel.insert(dailyProduct);

            Toast.makeText(getContext(), "Daily Product saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_DAILY_PRODUCT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditDailyProduct.EXTRA_DAILY_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Daily Product can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String date = data.getStringExtra(AddEditDailyProduct.EXTRA_DAILY_DATE);
            int product_id = data.getIntExtra(AddEditDailyProduct.EXTRA_PRODUCT_ID, 1);
            int team_id = data.getIntExtra(AddEditDailyProduct.EXTRA_TEAM_ID, 1);
            int number_of_preperation = data.getIntExtra(AddEditDailyProduct.EXTRA_NUMBER_PREPARATION ,1);
            int priority = data.getIntExtra(AddEditDailyProduct.EXTRA_PRIORITY,1);

            DailyProduct dailyProduct = new DailyProduct(Converters.fromStringToLocaDate(date), product_id, team_id,number_of_preperation,priority);
            dailyProduct.setD_id(id);
            dailyProductViewModel.update(dailyProduct);

            Toast.makeText(getContext(), "Daily Product updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Daily Product not saved", Toast.LENGTH_SHORT).show();
        }
    }


    class GenerateSchedule implements Runnable {
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            dailyProductViewModel.deleteAllScheduledProduct();
            List<Team> inWorkTeams = new ArrayList<>();
            inWorkTeams = dailyProductViewModel.getInWorkTeams();
            for (Team team : inWorkTeams){
                LocalTime startTime = Converters.fromStringtoLocalTime("06:00");
                startTime = team.getT_begin_time();
                Log.d(TAG, "start Thread " + team.getT_name());
                List<DailyProduct> dailyProducts = new ArrayList<>();
                dailyProducts = dailyProductViewModel.getDailyProductsForTeam(team.getT_id());
                for (DailyProduct dailyProduct : dailyProducts){
                    int numberOfCooks = dailyProduct.getD_number_of_cooks();

                    for (int i =0 ; i < numberOfCooks ; i++){
                        Product product = new Product();
                        product = dailyProductViewModel.getNotLiveProductById(dailyProduct.getD_product_id());

                        ScheduledProduct scheduledProduct = new ScheduledProduct();
                        scheduledProduct.setSch_daily_product_id(dailyProduct.getD_id());
                        scheduledProduct.setSch_status("en attente");
                        scheduledProduct.setSch_delay(0);
                        scheduledProduct.setSch_begin_time(startTime);

                        dailyProductViewModel.insert(scheduledProduct);

                        startTime = startTime.plusMinutes(product.getPr_cadence());
                    }
                }
            }
//            mainHandler.post(new Runnable() {
//                @Override
//                public void run() {
//                    Toast.makeText(getContext(), "Schedule Created Successfully", Toast.LENGTH_SHORT).show();
//                }
//            });
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}