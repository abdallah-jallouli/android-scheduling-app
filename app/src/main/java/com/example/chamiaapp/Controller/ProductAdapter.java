package com.example.chamiaapp.Controller;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Converters;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.R;

import java.util.List;

public class ProductAdapter extends ListAdapter<Product, ProductAdapter.ProductHolder> {
    private OnItemClickListener listener;

    public ProductAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Product> DIFF_CALLBACK = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(Product oldItem, Product newItem) {
            return oldItem.getPr_id() == newItem.getPr_id();
        }

        @Override
        public boolean areContentsTheSame(Product oldItem, Product newItem) {
            return oldItem.getPr_name().equals(newItem.getPr_name()) &&
                    oldItem.getPr_weight() == (newItem.getPr_weight()) &&
                    oldItem.getPr_cadence() == newItem.getPr_cadence() &&
                    oldItem.getPr_date_of_manufacture().equals(newItem.getPr_date_of_manufacture())&&
                    oldItem.getPr_description().equals(newItem.getPr_description())&&
                    oldItem.getPr_image() == newItem.getPr_image();
        }
    };

    @NonNull
    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_product, parent, false);
        return new ProductHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product currentProduct = getItem(position);
        holder.productName.setText(currentProduct.getPr_name());
        holder.productWeight.setText("Weight: "+ String.valueOf(currentProduct.getPr_weight())+" Gr");
        holder.productCadence.setText("Cadence: "+String.valueOf(currentProduct.getPr_cadence())+" Min");
        if (currentProduct != null) {
            byte[] imageByteArray = currentProduct.getPr_image();

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

    public Product getNoteAt(int position) {
        return getItem(position);
    }



    class ProductHolder extends RecyclerView.ViewHolder {
        private TextView productName;
        private TextView productWeight;
        private TextView productCadence;
        private ImageView productImageView ;

        public ProductHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productNameTextView);
            productWeight = itemView.findViewById(R.id.productWeightTextView);
            productCadence = itemView.findViewById(R.id.productCadenceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);

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
        void onItemClick(Product product);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


}
