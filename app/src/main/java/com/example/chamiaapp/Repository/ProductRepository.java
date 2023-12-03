package com.example.chamiaapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;


import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.ChamiaDatabase;
import com.example.chamiaapp.Dao.ProductDao;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;


import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ProductRepository {

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ProductRepository(Application application) {
        ChamiaDatabase database = ChamiaDatabase.getInstance(application);
        productDao = database.productDao();
        allProducts = productDao.getAllProducts();
    }

    public void insert(Product product) {
        new InsertProductAsyncTask(productDao).execute(product);
    }

    public void update(Product product) {
        new UpdateProductAsyncTask(productDao).execute(product);
    }

    public void delete(Product product) {
        new DeleteProductAsyncTask(productDao).execute(product);
    }



    public LiveData<List<Product>> getAllProducts() {
        return allProducts;
    }
    public LiveData<Product> getProductById(int pr_id){return productDao.getProductById(pr_id);}
    // Not live Data
    public Product getNotLiveProductById (int product_id ){return productDao.getNotLiveProductById(product_id);}



    private static class InsertProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private InsertProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.insert(products[0]);
            return null;
        }
    }

    private static class UpdateProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private UpdateProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.update(products[0]);
            return null;
        }
    }

    private static class DeleteProductAsyncTask extends AsyncTask<Product, Void, Void> {
        private ProductDao productDao;

        private DeleteProductAsyncTask(ProductDao productDao) {
            this.productDao = productDao;
        }

        @Override
        protected Void doInBackground(Product... products) {
            productDao.delete(products[0]);
            return null;
        }
    }

    // Get Product By id






}
