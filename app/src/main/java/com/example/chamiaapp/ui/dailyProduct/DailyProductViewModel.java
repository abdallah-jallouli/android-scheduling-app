package com.example.chamiaapp.ui.dailyProduct;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.Repository.DailyProductRepository;
import com.example.chamiaapp.Repository.ProductRepository;
import com.example.chamiaapp.Repository.ScheduledProductRepository;
import com.example.chamiaapp.Repository.TeamRepository;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DailyProductViewModel extends AndroidViewModel {

    private DailyProductRepository dailyProductRepository;
    private ProductRepository productRepository ;
    private TeamRepository teamRepository ;
    private ScheduledProductRepository scheduledProductRepository ;
    private LiveData<List<DailyProduct>> allDailyProducts;
    private LiveData<List<Product>> allProducts ;
    private LiveData<List<Team>> allTeams;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();



    @RequiresApi(api = Build.VERSION_CODES.O)
    public DailyProductViewModel(Application application) {
        super(application);
        dailyProductRepository = new DailyProductRepository(application);
        productRepository = new ProductRepository(application);
        teamRepository = new TeamRepository(application);
        scheduledProductRepository = new ScheduledProductRepository(application);

        allDailyProducts = dailyProductRepository.getAllDailyProducts();
        allProducts = productRepository.getAllProducts();
        allTeams = teamRepository.getAllTeams();
    }
    public void insert(DailyProduct dailyProduct) {
        dailyProductRepository.insert(dailyProduct);
    }

    public void update(DailyProduct dailyProduct) {
        dailyProductRepository.update(dailyProduct);
    }

    public void delete(DailyProduct dailyProduct) {
        dailyProductRepository.delete(dailyProduct);
    }

    public LiveData<List<DailyProduct>> getAllDailyProducts() {
        return allDailyProducts;
    }
    public LiveData<Team>getTeamById(int team_id ){return teamRepository.getTeamById(team_id);}
    public LiveData<Product>getProductById(int pr_id ){return productRepository.getProductById(pr_id);}


    public LiveData<List<Product>> getAllProducts () {
            return allProducts;
        }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
         }
    public Team getNotLiveTeamById(int teamId) {return teamRepository.getNotLiveTeamById(teamId);}

    public void insert (ScheduledProduct scheduledProduct ){scheduledProductRepository.insert(scheduledProduct);}

    public Product getNotLiveProductById (int product_id){
        return productRepository.getNotLiveProductById(product_id);
    }

    public List<Team> getInWorkTeams(){
        return teamRepository.getInWorkTeams();
    }

    public void deleteAllScheduledProduct (){
        scheduledProductRepository.deleteAllSchedule();
    }

    public List<DailyProduct> getDailyProductsForTeam(int team_id){
        return dailyProductRepository.getDailyProductsForTeam(team_id);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }


}