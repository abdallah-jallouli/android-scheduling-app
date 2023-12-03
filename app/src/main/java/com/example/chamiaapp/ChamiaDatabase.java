package com.example.chamiaapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.chamiaapp.Dao.DailyProductDao;
import com.example.chamiaapp.Dao.EmployeeDao;
import com.example.chamiaapp.Dao.ProductDao;
import com.example.chamiaapp.Dao.ScheduledProductDao;
import com.example.chamiaapp.Dao.TeamDao;
import com.example.chamiaapp.Models.DailyProduct;
import com.example.chamiaapp.Models.Employee;
import com.example.chamiaapp.Models.Product;
import com.example.chamiaapp.Models.ScheduledProduct;
import com.example.chamiaapp.Models.Team;


@RequiresApi(api = Build.VERSION_CODES.O)
@Database(entities = {Product.class, Employee.class, Team.class, DailyProduct.class, ScheduledProduct.class}, version = 25,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class ChamiaDatabase extends RoomDatabase {

    private static ChamiaDatabase instance;

    public abstract ProductDao productDao();
    public abstract TeamDao teamDao();
    public abstract EmployeeDao employeeDao();
    public abstract DailyProductDao dailyProductDao();
    public abstract ScheduledProductDao scheduledProductDao();




    public static synchronized ChamiaDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            ChamiaDatabase.class, "chamia_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProductDao productDao;
        private TeamDao teamDao;
        private EmployeeDao employeeDao;

        private DailyProductDao dailyProductDao;
        private ScheduledProductDao scheduledProductDao;

        private PopulateDbAsyncTask(ChamiaDatabase db) {
            productDao = db.productDao();
            teamDao = db.teamDao();
            employeeDao = db.employeeDao();
            dailyProductDao = db.dailyProductDao();
            scheduledProductDao = db.scheduledProductDao();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            productDao.insert(new Product("Spécial 5000 Gr", 5000, 12,"24/06/2023", "Chamia"));
            productDao.insert(new Product("Spécial 2000 Gr", 2000, 20,"24/06/2023", "Chamia"));
            productDao.insert(new Product("Amande 400 Gr", 400, 35,"24/06/2023", "Chamia"));


            teamDao.insert(new Team("Team 1",Converters.fromStringtoLocalTime("06:30:30") , "this is the first team"));
            teamDao.insert(new Team("Team 2", Converters.fromStringtoLocalTime("07:30:30"),"this is the second team"));
            teamDao.insert(new Team("Team 3", Converters.fromStringtoLocalTime("08:30:30"),"this is the third team"));



            employeeDao.insert(new Employee("Name", "LastName 1","24/06/2023","rte de tunis km 5", "26 255 254", "24/06/2023",1));
            employeeDao.insert(new Employee("Name", "LastName 2","24/06/2023","rte de tunis km 5", "20 255 254", "24/06/2023",1));
            employeeDao.insert(new Employee("Name", "LastName 3","24/06/2023","rte de tunis km 5", "31 255 254", "24/06/2023",1));
            employeeDao.insert(new Employee("Name", "LastName 4","24/06/2023","rte de tunis km 5", "96 255 254", "24/06/2023",2));
            employeeDao.insert(new Employee("Name", "LastName 5","24/06/2023","rte de tunis km 5", "94 255 254", "24/06/2023",2));
            employeeDao.insert(new Employee("Name", "LastName 6","24/06/2023","rte de tunis km 5", "24 255 254", "24/06/2023",2));
            employeeDao.insert(new Employee("Name", "LastName 7","24/06/2023","rte de tunis km 5", "50 255 254", "24/06/2023",3));
            employeeDao.insert(new Employee("Name", "LastName 8","24/06/2023","rte de tunis km 5", "51 255 254", "24/06/2023",3));
            employeeDao.insert(new Employee("Name", "LastName 9","24/06/2023","rte de tunis km 5", "54 255 254", "24/06/2023",3));

            return null;
        }
    }
}

