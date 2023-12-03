package com.example.chamiaapp.Controller;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Converters;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.ui.schedule.MainSchedule;
import com.example.chamiaapp.ui.schedule.ScheduleViewModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ScheduleAdapter extends ListAdapter<ScheduledProduct, ScheduleAdapter.ScheduleHolder> {
    private ScheduleAdapter.OnItemClickListener listener;
    private TextView numberTextView;
    ScheduleViewModel scheduleViewModel;
    Context context;
    Product mproduct = new Product();
    Team mteam = new Team();
    private static final String TAG = "ScheduleAdapter";

    public ScheduleAdapter(Context context, ScheduleViewModel scheduleViewModel, TextView numberTextView ) {
        super(DIFF_CALLBACK);
        this.scheduleViewModel = scheduleViewModel ;
        this.context = context ;
        this.numberTextView = numberTextView;
    }

    private static final DiffUtil.ItemCallback<ScheduledProduct> DIFF_CALLBACK = new DiffUtil.ItemCallback<ScheduledProduct>() {
        @Override
        public boolean areItemsTheSame(ScheduledProduct oldItem, ScheduledProduct newItem) {
            return oldItem.getSch_id() == newItem.getSch_id();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean areContentsTheSame(ScheduledProduct oldItem, ScheduledProduct newItem) {
            return oldItem.getSch_begin_time().equals (newItem.getSch_begin_time()) &&
                    oldItem.getSch_delay() == (newItem.getSch_delay()) &&
                    oldItem.getSch_status() .equals (newItem.getSch_status()) &&
                    oldItem.getSch_daily_product_id()==(newItem.getSch_daily_product_id());
        }
    };

    @NonNull
    @Override
    public ScheduleAdapter.ScheduleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_scheduled_product, parent, false);
        return new ScheduleAdapter.ScheduleHolder(itemView);
    }


    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ScheduleAdapter.ScheduleHolder holder, int position) {
        ScheduledProduct currentScheduledProduct = getItem(position);
        scheduleViewModel.getProductByDailyProductId(currentScheduledProduct.getSch_daily_product_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Product>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Product product) {
                        holder.name.setText(product.getPr_name());
                        holder.weight.setText("Weight: "+String.valueOf(product.getPr_weight()));
                        byte[] imageByteArray = product.getPr_image();

                        // Check if the byte array is not null
                        if (imageByteArray != null) {
                            // Step 1: Convert the byte array to a Bitmap
                            Converters converters = new Converters();
                            Bitmap bitmap = converters.byteArrayToBitmap(imageByteArray);

                            // Check if the bitmap is not null
                            if (bitmap != null) {
                                // Step 2: Set the bitmap to the ImageView
                                holder.imageView.setImageBitmap(bitmap);
                            }
                        }
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });
        scheduleViewModel.getTeamByDailyProductId(currentScheduledProduct.getSch_daily_product_id())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Team>() {
                    @Override
                    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@io.reactivex.rxjava3.annotations.NonNull Team team) {
                        holder.teamName.setText(team.getT_name());
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }
                });

        holder.startTime.setText("Start Time: "+String.valueOf(currentScheduledProduct.getSch_begin_time()));


        String status = currentScheduledProduct.sch_status;
        if (status.equals("en attente")) {
            holder.coloredCercle.setBackgroundResource(R.drawable.gris_background);
        } else if (status.equals("en cours")) {
            holder.coloredCercle.setBackgroundResource(R.drawable.yellow_background);
        } else if (status.equals("terminé sans retard")) {
            holder.coloredCercle.setBackgroundResource(R.drawable.green_background);
        } else if (status.equals("terminé avec retard")) {
            holder.coloredCercle.setBackgroundResource(R.drawable.red_background);
        } else if (status.equals("a estimé")) {
            holder.coloredCercle.setBackgroundResource(R.drawable.orange_background);
        } else {
            // Statut inconnu ou autre traitement par défaut
            holder.coloredCercle.setBackgroundResource(R.drawable.button_background);
        }

        if (status.equals("en cours")){
            holder.passButton.setEnabled(true);
            holder.passButton.setBackgroundResource(R.drawable.button_background);
        }
        else {
            holder.passButton.setEnabled(false);
            holder.passButton.setBackgroundResource(R.drawable.gris_background);
        }
        holder.passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int selectedId = ((MainSchedule) v.getContext()).getSelectedRadioButtonId();
                int delay = Integer.parseInt(numberTextView.getText().toString());

                if (selectedId == R.id.radio_button1) {

                    currentScheduledProduct.setSch_delay(currentScheduledProduct.getSch_delay() + delay);
                    if (delay != 0 ){
                        currentScheduledProduct.setSch_status("terminé avec retard");
                        scheduleViewModel.update(currentScheduledProduct);
                    }
                    else {
                        currentScheduledProduct.setSch_status("terminé sans retard");
                        scheduleViewModel.update(currentScheduledProduct);
                    }


                    // run in other thread
                    ModificationForTeamDelay runnableModification = new ModificationForTeamDelay(delay , currentScheduledProduct.getSch_id(),
                            currentScheduledProduct.getSch_begin_time());
                    new Thread(runnableModification).start();


                } else if (selectedId == R.id.radio_button2) {
                    // Effectuer une opération pour le radioButton2
                } else if (selectedId == R.id.radio_button3) {
                    ModificationForFailure modificationForBreakDown = new ModificationForFailure(delay ,
                            currentScheduledProduct.getSch_begin_time(),currentScheduledProduct.getSch_id());
                    new Thread(modificationForBreakDown).start();
                }



                holder.passButton.setEnabled(false);
                holder.passButton.setBackgroundResource(R.drawable.gris_background);

            }
        });



    }

    public ScheduledProduct getScheduledProductAt(int position) {
        return getItem(position);
    }

    class ScheduleHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView weight;
        private TextView startTime;
        private TextView teamName;
        private ImageView imageView ;
        private ImageView coloredCercle ;
        private Button passButton;

        public ScheduleHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.schedulingProductNameTextView);
            weight = itemView.findViewById(R.id.weightTextView);
            startTime = itemView.findViewById(R.id.beginTimeTextView);
            teamName = itemView.findViewById(R.id.teamNameTextView);
            imageView = itemView.findViewById(R.id.schedulingProductImageView);
            coloredCercle = itemView.findViewById(R.id.coloredCircleImageView);
            passButton = itemView.findViewById(R.id.passButton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(ScheduledProduct scheduledProduct);
    }

    public void setOnItemClickListener(ScheduleAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    class ModificationForTeamDelay implements Runnable {
        int sch_id ;
        LocalTime time;
        int delay ;
        ModificationForTeamDelay (int delay,int sch_id, LocalTime time){
            this.sch_id = sch_id ;
            this.time = time ;
            this.delay = delay ;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {


            // Modify the next Scheduled Product
            ScheduledProduct nextScheduledProduct = scheduleViewModel.getJustAfterScheduledProduct(sch_id, time);
            if (nextScheduledProduct != null){
                nextScheduledProduct.setSch_status("en cours");
                scheduleViewModel.update(nextScheduledProduct);

                // Modify all next Scheduled Product
                List<ScheduledProduct> allAfterScheduledProduct = new ArrayList<>();
                allAfterScheduledProduct = scheduleViewModel.getAllAfterScheduledProduct(sch_id,nextScheduledProduct.getSch_begin_time());

                if (allAfterScheduledProduct != null){
                    for (ScheduledProduct scheduledProduct : allAfterScheduledProduct ){
                        scheduledProduct.addDelayToBeginTime(delay);
                        scheduleViewModel.update(scheduledProduct);
                    }
                }


                // Modify if there are scheduled product before next schedule ayant une status en attente
                List<ScheduledProduct> beforeNextScheduledProduct = new ArrayList<>();
                beforeNextScheduledProduct = scheduleViewModel.getScheduledProductsBeforeTime(nextScheduledProduct.getSch_begin_time());
                if (beforeNextScheduledProduct != null){
                    for (ScheduledProduct beforeNext : beforeNextScheduledProduct){
                        //Log.d(TAG, "abdallah " + beforeNext.getSch_begin_time());
                        beforeNext.setSch_status("en cours");
                        scheduleViewModel.update(beforeNext);
                    }
                }
            }
        }
    }

    class ModificationForFailure implements Runnable {
        LocalTime time;
        int delay ;
        int sch_id ;
        ModificationForFailure (int delay ,LocalTime time,int sch_id ){
            this.time = time ;
            this.delay = delay ;
            this.sch_id = sch_id;
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public void run() {
            List<ScheduledProduct> mList = new ArrayList<>( );
            mList = scheduleViewModel.getAllAfterForBreakDown(time);
            if (mList != null ){
                for (ScheduledProduct mScheduledProduct : mList ){
                    mScheduledProduct.addDelayToBeginTime(delay);
                    scheduleViewModel.update(mScheduledProduct);
                }
            }
            // Modify the next Scheduled Product
            ScheduledProduct nextScheduledProduct = scheduleViewModel.getJustAfterScheduledProduct(sch_id, time);
            if (nextScheduledProduct != null) {
                nextScheduledProduct.setSch_status("en cours");
                scheduleViewModel.update(nextScheduledProduct);
            }
        }
    }





}
