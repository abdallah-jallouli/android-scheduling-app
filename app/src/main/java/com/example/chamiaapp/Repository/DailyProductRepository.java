package com.example.chamiaapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.ChamiaDatabase;
import com.example.chamiaapp.Dao.DailyProductDao;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.Team;


import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class DailyProductRepository {

    private DailyProductDao dailyProductDao;
    private LiveData<List<DailyProduct>> allDailyProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public DailyProductRepository(Application application) {
        ChamiaDatabase database = ChamiaDatabase.getInstance(application);
        dailyProductDao = database.dailyProductDao();
        allDailyProducts = dailyProductDao.getAllDailyProducts();

    }

    public LiveData<DailyProduct> getDailyProductById (int d_id){return dailyProductDao.getDailyProductById(d_id);}

    public void insert(DailyProduct dailyProduct) {
        new DailyProductRepository.InsertDailyProductAsyncTask(dailyProductDao).execute(dailyProduct);
    }

    public void update(DailyProduct dailyProduct) {
        new DailyProductRepository.UpdateDailyProductAsyncTask(dailyProductDao).execute(dailyProduct);
    }

    public void delete(DailyProduct dailyProduct) {
        new DailyProductRepository.DeleteDailyProductAsyncTask(dailyProductDao).execute(dailyProduct);
    }

    public LiveData<List<DailyProduct>> getAllDailyProducts() {
        return allDailyProducts;
    }
//    public Single<List<DailyProduct>> getDailyProductsForTeam (int team_id){return dailyProductDao.getDailyProductsForTeam(team_id);}
    public List<DailyProduct> getDailyProductsForTeam (int team_id){return dailyProductDao.getDailyProductsForTeam(team_id);}
    public Single<Product> getProductByDailyProductId (int dailyProducId){return dailyProductDao.getProductByDailyProductId(dailyProducId);}
    public Single<Team> getTeamByDailyProductId(int dailyProductId){return dailyProductDao.getTeamByDailyProductId(dailyProductId);}







    // AsynkTasks
    private static class InsertDailyProductAsyncTask extends AsyncTask<DailyProduct, Void, Void> {
        private DailyProductDao dailyProductDao;

        private InsertDailyProductAsyncTask(DailyProductDao dailyProductDao) {
            this.dailyProductDao = dailyProductDao;
        }

        @Override
        protected Void doInBackground(DailyProduct... dailyProducts) {
            dailyProductDao.insert(dailyProducts[0]);
            return null;
        }
    }

    private static class UpdateDailyProductAsyncTask extends AsyncTask<DailyProduct, Void, Void> {
        private DailyProductDao dailyProductDao;

        private UpdateDailyProductAsyncTask(DailyProductDao dailyProductDao) {
            this.dailyProductDao = dailyProductDao;
        }

        @Override
        protected Void doInBackground(DailyProduct... dailyProducts) {
            dailyProductDao.update(dailyProducts[0]);
            return null;
        }
    }

    private static class DeleteDailyProductAsyncTask extends AsyncTask<DailyProduct, Void, Void> {
        private DailyProductDao dailyProductDao;

        private DeleteDailyProductAsyncTask(DailyProductDao dailyProductDao) {
            this.dailyProductDao = dailyProductDao;
        }

        @Override
        protected Void doInBackground(DailyProduct... dailyProducts) {
            dailyProductDao.delete(dailyProducts[0]);
            return null;
        }
    }
}
