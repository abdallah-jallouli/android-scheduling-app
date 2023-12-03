package com.example.chamiaapp.ui.schedule;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
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

import java.time.LocalTime;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class ScheduleViewModel extends AndroidViewModel {

    private ScheduledProductRepository scheduledProductRepository;
    private ProductRepository productRepository  ;
    private TeamRepository teamRepository ;
    private DailyProductRepository dailyProductRepository ;
    private LiveData<List<ScheduledProduct>> allScheudledProducts;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ScheduleViewModel(@NonNull Application application) {
        super(application);
        scheduledProductRepository = new ScheduledProductRepository(application);
        dailyProductRepository = new DailyProductRepository(application);
        teamRepository = new TeamRepository(application);
        productRepository = new ProductRepository(application);
        allScheudledProducts = scheduledProductRepository.getAllScheduledProducts();

    }

    public LiveData<Product>getProductById(int pr_id ){return productRepository.getProductById(pr_id);}
    public LiveData<Team>getTeamById(int team_id ){return teamRepository.getTeamById(team_id);}
    public LiveData<DailyProduct> getDailyProductById (int d_id ){return dailyProductRepository.getDailyProductById(d_id);}
    public Single<Product> getProductByDailyProductId (int dailyProductId){return dailyProductRepository.getProductByDailyProductId(dailyProductId);}
    public Single<Team> getTeamByDailyProductId (int dailyProductId ){return dailyProductRepository.getTeamByDailyProductId(dailyProductId);}
    public List<ScheduledProduct>getFirstScheduledProductsForEachTeam (){return scheduledProductRepository.getFirstScheduledProducts();}
    public ScheduledProduct getJustAfterScheduledProduct (int scheduledProductId , LocalTime time)
    {return scheduledProductRepository.getJustAfterScheduledProduct(scheduledProductId,time);}

    public List<ScheduledProduct> getAllAfterScheduledProduct (int scheduledProductId , LocalTime time)
    {return scheduledProductRepository.getAllAfterScheduledProduct(scheduledProductId,time);}

    public List<ScheduledProduct> getScheduledProductsBeforeTime (LocalTime time)
    {return scheduledProductRepository.getScheduledProductsBeforeTime(time);}


    public List<ScheduledProduct> getAllAfterForBreakDown (LocalTime time)
    {return scheduledProductRepository.getAllAfterForBreakDown(time);}
    public void insert(ScheduledProduct scheduledProduct) {
        scheduledProductRepository.insert(scheduledProduct);
    }

    public void update(ScheduledProduct scheduledProduct) {
        scheduledProductRepository.update(scheduledProduct);
    }

    public void delete(ScheduledProduct scheduledProduct) {
        scheduledProductRepository.delete(scheduledProduct);
    }

    public LiveData<List<ScheduledProduct>> getAllScheudledProducts() {
        return allScheudledProducts;
    }

}
