package com.example.chamiaapp.Controller;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Models.Employee;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.UpdateRecyclerView;
import com.example.chamiaapp.ui.employee.EmployeeViewModel;

import java.util.ArrayList;
import java.util.List;

public class TeamAdapter extends ListAdapter<Team, TeamAdapter.TeamHolder> {
    private OnItemClickListener listener;
    private OnLongItemClickListener listener1 ;
    private Context context ;
    int row_index = -1 ;

    boolean check =true ;
    boolean select= true;
    EmployeeViewModel employeeViewModel;
    UpdateRecyclerView updateRecyclerView ;
    private static final String TAG = "TeamAdapter";


    public TeamAdapter(Context context,EmployeeViewModel employeeViewModel, UpdateRecyclerView updateRecyclerView) {
        super(DIFF_CALLBACK);
        this.employeeViewModel = employeeViewModel;
        this.context = context;
        this.updateRecyclerView = updateRecyclerView ;
    }

    private static final DiffUtil.ItemCallback<Team> DIFF_CALLBACK = new DiffUtil.ItemCallback<Team>() {
        @Override
        public boolean areItemsTheSame(Team oldItem, Team newItem) {
            return oldItem.getT_id() == newItem.getT_id();
        }

        @Override
        public boolean areContentsTheSame(Team oldItem, Team newItem) {
            return oldItem.getT_name().equals(newItem.getT_name()) &&
                    oldItem.getT_description() .equals(newItem.getT_description()) ;
        }
    };

    @NonNull
    @Override
    public TeamHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_team, parent, false);
        return new TeamHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TeamHolder holder, int position) {
        Team currentTeam = getItem(position);
        holder.team_name.setText(currentTeam.getT_name());

//        if(check){
//            LiveData<List<Employee>> employees = new ArrayList<>();
//            employees = employeeViewModel.getAllemployees();
//
//            updateRecyclerView.callback(position,employees);
//            check = false ;
//        }

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_index = position;
                //notifyDataSetChanged();

                // Retrieve the employees for the selected team
                LiveData<List<Employee>> employeesLiveData = employeeViewModel.getEmployeesByTeam(currentTeam.getT_id());
                employeesLiveData.observe((LifecycleOwner)context, new Observer<List<Employee>>() {
                    @Override
                    public void onChanged(List<Employee> employees) {
                        // Invoke the callback method to update the employees RecyclerView
                        updateRecyclerView.callback(position, employees);


                    }
                });
            }
        });

        Log.d(TAG, "abdallah: row_index= " + row_index + " ** " + position);
//        if (row_index == position)
//            {
//                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
//            }
//        else {
//                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
//            }


//        if (select){
//            if (position == 0)
//                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
//            select = false ;
//        }
//        else {
//            if (row_index == position)
//            {
//                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_selected_bg);
//            }
//            else {
//                holder.linearLayout.setBackgroundResource(R.drawable.static_rv_bg);
//            }
//        }
    }

    public Team getTeamAt (int position) {
        return getItem(position);
    }

    class TeamHolder extends RecyclerView.ViewHolder {
        private TextView team_name;
        private LinearLayout linearLayout ;

        public TeamHolder(View itemView) {
            super(itemView);
            team_name = itemView.findViewById(R.id.row_team_name);
            linearLayout = itemView.findViewById(R.id.linearlayout_team);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onItemClick(getItem(position));
//                    }
//                }
//            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    if (listener1 != null && position!= RecyclerView.NO_POSITION){
                        listener1.onLongItemClick(getItem(position));
                        return true ;
                    }
                    return false;
                }
            });





        }
    }

    public interface OnItemClickListener {
        void onItemClick(Team team);
    }

    public interface OnLongItemClickListener {
        void onLongItemClick(Team team);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.listener1 = listener;
    }



}
