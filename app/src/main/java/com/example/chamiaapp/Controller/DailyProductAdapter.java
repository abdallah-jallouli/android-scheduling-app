package com.example.chamiaapp.Controller;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Converters;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.ui.dailyProduct.DailyProductViewModel;

public class DailyProductAdapter extends ListAdapter<DailyProduct, DailyProductAdapter.DailyProductHolder> {
    private OnItemClickListener listener;
    DailyProductViewModel dailyProductViewModel;
    Context context;

    public DailyProductAdapter(Context context, DailyProductViewModel dailyProductViewModel) {
        super(DIFF_CALLBACK);
        this.dailyProductViewModel = dailyProductViewModel ;
        this.context = context ;
    }

    private static final DiffUtil.ItemCallback<DailyProduct> DIFF_CALLBACK = new DiffUtil.ItemCallback<DailyProduct>() {
        @Override
        public boolean areItemsTheSame(DailyProduct oldItem, DailyProduct newItem) {
            return oldItem.getD_id() == newItem.getD_id();
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        public boolean areContentsTheSame(DailyProduct oldItem, DailyProduct newItem) {
            return oldItem.getD_product_id()==(newItem.getD_product_id()) &&
                    oldItem.getD_team_id() == (newItem.getD_team_id()) &&
                    oldItem.getD_date() .equals (newItem.getD_date()) &&
                    oldItem.getD_number_of_cooks()==(newItem.getD_number_of_cooks())&&
                    oldItem.getD_priority()==(newItem.getD_priority());
        }
    };

    @NonNull
    @Override
    public DailyProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_daily_product, parent, false);
        return new DailyProductHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull DailyProductHolder holder, int position) {
        DailyProduct currentDailyProduct = getItem(position);
        LiveData<Product> productLiveData= dailyProductViewModel.getProductById(currentDailyProduct.getD_product_id());
        LiveData<Team> teamLiveData= dailyProductViewModel.getTeamById(currentDailyProduct.getD_team_id());

        productLiveData.observe((LifecycleOwner) context, new Observer<Product>() {
            @Override
            public void onChanged(Product product) {
                if (product != null) {
                    holder.productName.setText(product.getPr_name());
                    holder.productWeight.setText("Date: "+Converters.toStringDate(currentDailyProduct.getD_date()));

                    byte[] imageByteArray = product.getPr_image();

                    // Check if the byte array is not null
                    if (imageByteArray != null) {
                        // Step 1: Convert the byte array to a Bitmap
                        Converters converters = new Converters();
                        Bitmap bitmap = converters.byteArrayToBitmap(imageByteArray);

                        // Check if the bitmap is not null
                        if (bitmap != null) {
                            // Step 2: Set the bitmap to the ImageView
                            holder.productImageView.setImageBitmap(bitmap);
                        }
                    }
                }
            }
        });

        holder.number_of_cooks.setText("Number of Cooks: "+String.valueOf(currentDailyProduct.d_number_of_cooks));

        teamLiveData.observe((LifecycleOwner) context, new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                if (team != null ){
                    holder.responsible_team.setText("Responsible Team: "+team.getT_name());
                }
            }
        });


    }

    public DailyProduct getDailyProductAt(int position) {
        return getItem(position);
    }

    class DailyProductHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView number_of_cooks;
        private TextView responsible_team;
        private TextView productWeight;
        private ImageView productImageView ;

        public DailyProductHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.dailyProductNameTextView);
            number_of_cooks = itemView.findViewById(R.id.numberOfCooksTextView);
            responsible_team = itemView.findViewById(R.id.responsibleTeamTextView);
            productWeight = itemView.findViewById(R.id.weightTextView);
            productImageView = itemView.findViewById(R.id.dailyProductImageView);

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
        void onItemClick(DailyProduct dailyProduct);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}

