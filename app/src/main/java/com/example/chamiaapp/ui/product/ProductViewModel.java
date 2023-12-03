package com.example.chamiaapp.ui.product;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Repository.ProductRepository;

import java.io.Closeable;
import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepository repository;
    private LiveData<List<Product>> allProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProductViewModel(@NonNull Application application) {
        super(application);
        repository = new ProductRepository(application);
        allProducts = repository.getAllProducts();
    }

    public void insert(Product product) {
        repository.insert(product);
    }

    public void update(Product product) {
        repository.update(product);
    }

    public void delete(Product product) {
        repository.delete(product);
    }

    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }
}