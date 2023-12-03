package com.example.chamiaapp.Repository;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import com.example.chamiaapp.ChamiaDatabase;
import com.example.chamiaapp.Dao.TeamDao;
import com.example.chamiaapp.Models.Team;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class TeamRepository {

    private TeamDao teamDao;
    private LiveData<List<Team>> allTeams;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public TeamRepository(Application application) {
        ChamiaDatabase database = ChamiaDatabase.getInstance(application);
        teamDao = database.teamDao();
        allTeams = teamDao.getAllTeams();
    }

    public void insert(Team team) {
        new TeamRepository.InsertTeamAsyncTask(teamDao).execute(team);
    }

    public void update(Team team) {
        new UpdateTeamAsyncTask(teamDao).execute(team);
    }

    public void delete(Team team) {
        new TeamRepository.DeleteTeamAsyncTask(teamDao).execute(team);
    }

    public LiveData<List<Team>> getAllTeams() {
        return allTeams;
    }
    public LiveData<Team> getTeamById(int team_id){return teamDao.getTeamById(team_id);}
    public Team getNotLiveTeamById(int teamId) {return teamDao.getNotLiveTeamById(teamId);}
    public List<Team> getInWorkTeams () {return teamDao.getInWorkTeams();}

    private static class InsertTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private InsertTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.insert(teams[0]);
            return null;
        }
    }

    private static class UpdateTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private UpdateTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.update(teams[0]);
            return null;
        }
    }

    private static class DeleteTeamAsyncTask extends AsyncTask<Team, Void, Void> {
        private TeamDao teamDao;

        private DeleteTeamAsyncTask(TeamDao teamDao) {
            this.teamDao = teamDao;
        }

        @Override
        protected Void doInBackground(Team... teams) {
            teamDao.delete(teams[0]);
            return null;
        }
    }
}
