package com.example.chamiaapp.ui.product;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Controller.ProductAdapter;
import com.example.chamiaapp.MainActivity;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.R;
import com.example.chamiaapp.databinding.FragmentProductBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ProductFragment extends Fragment {

    public static final int ADD_PRODUCT_REQUEST = 1;
    public static final int EDIT_PRODUCT_REQUEST = 2;

    private ProductViewModel productViewModel;

    private FragmentProductBinding binding;
    final ProductAdapter adapter = new ProductAdapter();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProducts().observe(getViewLifecycleOwner(), new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                adapter.submitList(products);
            }
        });

        binding = FragmentProductBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        FloatingActionButton buttonAddNote = view.findViewById(R.id.fab_add_product);
        buttonAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEditProduct.class);
                startActivityForResult(intent, ADD_PRODUCT_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.product_rv);
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
                productViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getContext(), "Product deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Product product) {
                Intent intent = new Intent(getContext(), AddEditProduct.class);
                intent.putExtra(AddEditProduct.EXTRA_ID, product.getPr_id());
                intent.putExtra(AddEditProduct.EXTRA_NAME, product.getPr_name());
                intent.putExtra(AddEditProduct.EXTRA_WEIGHT, product.getPr_weight());
                intent.putExtra(AddEditProduct.EXTRA_CADENCE, product.getPr_cadence());
                intent.putExtra(AddEditProduct.EXTRA_DATE_OF_MANUFACTURE, product.getPr_date_of_manufacture());
                intent.putExtra(AddEditProduct.EXTRA_DESCRIPTION, product.getPr_description());
                intent.putExtra(AddEditProduct.EXTRA_IMAGE, product.getPr_image());

                startActivityForResult(intent, EDIT_PRODUCT_REQUEST);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_PRODUCT_REQUEST && resultCode == RESULT_OK) {
            String name = data.getStringExtra(AddEditProduct.EXTRA_NAME);
            int weight = data.getIntExtra(AddEditProduct.EXTRA_WEIGHT, 1);
            int cadence = data.getIntExtra(AddEditProduct.EXTRA_CADENCE, 1);
            String date_of_manufacture = data.getStringExtra(AddEditProduct.EXTRA_DATE_OF_MANUFACTURE);
            String description = data.getStringExtra(AddEditProduct.EXTRA_DESCRIPTION);
            byte[] image = data.getByteArrayExtra(AddEditProduct.EXTRA_IMAGE);

            Product product = new Product(name, weight, cadence,date_of_manufacture,description);
            product.setPr_image(image);
            productViewModel.insert(product);

            Toast.makeText(getContext(), "Product saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_PRODUCT_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditProduct.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getContext(), "Product can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }

            String name = data.getStringExtra(AddEditProduct.EXTRA_NAME);
            int weight = data.getIntExtra(AddEditProduct.EXTRA_WEIGHT, 1);
            int cadence = data.getIntExtra(AddEditProduct.EXTRA_CADENCE, 1);
            String date_of_manufacture = data.getStringExtra(AddEditProduct.EXTRA_DATE_OF_MANUFACTURE);
            String description = data.getStringExtra(AddEditProduct.EXTRA_DESCRIPTION);
            byte[] image = data.getByteArrayExtra(AddEditProduct.EXTRA_IMAGE);

            Product product = new Product(name, weight, cadence,date_of_manufacture,description);
            product.setPr_image(image);
            product.setPr_id(id);
            productViewModel.update(product);

            Toast.makeText(getContext(), "Product updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Product not saved", Toast.LENGTH_SHORT).show();
        }
    }



}