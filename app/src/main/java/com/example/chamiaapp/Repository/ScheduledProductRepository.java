package com.example.chamiaapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.ChamiaDatabase;
import com.example.chamiaapp.Dao.ScheduledProductDao;
import com.example.chamiaapp.Models.ScheduledProduct;


import java.time.LocalTime;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ScheduledProductRepository {
    private ScheduledProductDao scheduledProductDao;
    private LiveData<List<ScheduledProduct>> allScheduledProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ScheduledProductRepository(Application application) {
        ChamiaDatabase database = ChamiaDatabase.getInstance(application);
        scheduledProductDao = database.scheduledProductDao();
        allScheduledProducts = scheduledProductDao.getAllScheduledProducts();
    }

    public void insert(ScheduledProduct scheduledProduct) {
        new ScheduledProductRepository.InsertScheduledProductAsyncTask(scheduledProductDao).execute(scheduledProduct);
    }

    public void update(ScheduledProduct scheduledProduct) {
        new ScheduledProductRepository.UpdateScheduledProductAsyncTask(scheduledProductDao).execute(scheduledProduct);
    }

    public void delete(ScheduledProduct scheduledProduct) {
        new ScheduledProductRepository.DeleteScheduledProductAsyncTask(scheduledProductDao).execute(scheduledProduct);
    }
//    public void deleteAllSchedule() {
//        new ScheduledProductRepository.DeleteAllScheduledAsyncTask(scheduledProductDao).execute();
//    }

    public List<ScheduledProduct> getFirstScheduledProducts (){
        return scheduledProductDao.startSchedule();
    }

    public ScheduledProduct getJustAfterScheduledProduct(int scheduledProductId , LocalTime time){
        return scheduledProductDao.getJustAfterScheduledProduct(scheduledProductId , time);
    }

    public List<ScheduledProduct> getAllAfterScheduledProduct(int scheduledProductId , LocalTime time){
        return scheduledProductDao.getAllAfterScheduledProduct(scheduledProductId , time);
    }

    public List<ScheduledProduct> getScheduledProductsBeforeTime( LocalTime time){
        return scheduledProductDao.getScheduledProductsBeforeTime( time);
    }

    public List<ScheduledProduct> getAllAfterForBreakDown( LocalTime time){
        return scheduledProductDao.getAllAfterForBreakDown( time);
    }


    public void deleteAllSchedule() {
        scheduledProductDao.deleteAllScheduledProduct();
    }


    public LiveData<List<ScheduledProduct>> getAllScheduledProducts() {
        return allScheduledProducts;
    }



    private static class InsertScheduledProductAsyncTask extends AsyncTask<ScheduledProduct, Void, Void> {
        private ScheduledProductDao scheduledProductDao;

        private InsertScheduledProductAsyncTask(ScheduledProductDao scheduledProductDao) {
            this.scheduledProductDao = scheduledProductDao;
        }

        @Override
        protected Void doInBackground(ScheduledProduct... scheduledProducts) {
            scheduledProductDao.insert(scheduledProducts[0]);
            return null;
        }
    }

    private static class UpdateScheduledProductAsyncTask extends AsyncTask<ScheduledProduct, Void, Void> {
        private ScheduledProductDao scheduledProductDao;

        private UpdateScheduledProductAsyncTask(ScheduledProductDao scheduledProductDao) {
            this.scheduledProductDao = scheduledProductDao;
        }

        @Override
        protected Void doInBackground(ScheduledProduct... scheduledProducts) {
            scheduledProductDao.update(scheduledProducts[0]);
            return null;
        }
    }

    private static class DeleteScheduledProductAsyncTask extends AsyncTask<ScheduledProduct, Void, Void> {
        private ScheduledProductDao scheduledProductDao;

        private DeleteScheduledProductAsyncTask(ScheduledProductDao scheduledProductDao) {
            this.scheduledProductDao = scheduledProductDao;
        }

        @Override
        protected Void doInBackground(ScheduledProduct... scheduledProducts) {
            scheduledProductDao.delete(scheduledProducts[0]);
            return null;
        }
    }

//    private static class DeleteAllScheduledAsyncTask extends AsyncTask<Void, Void, Void> {
//        private ScheduledProductDao scheduledProductDao;
//
//        private DeleteAllScheduledAsyncTask(ScheduledProductDao scheduledProductDao) {
//            this.scheduledProductDao = scheduledProductDao;
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//            scheduledProductDao.deleteAllScheduledProduct();
//            return null;
//        }
//    }
}
