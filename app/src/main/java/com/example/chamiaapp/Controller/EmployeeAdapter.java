package com.example.chamiaapp.Controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chamiaapp.Converters;
import com.example.chamiaapp.Models.Employee;
import com.example.chamiaapp.Models.Team;
import com.example.chamiaapp.R;
import com.example.chamiaapp.Repository.TeamRepository;
import com.example.chamiaapp.ui.employee.EmployeeViewModel;

import java.util.List;

public class EmployeeAdapter extends ListAdapter<Employee, EmployeeAdapter.EmployeeHolder> {
    private OnItemClickListener listener;
    private Context context ;
    private EmployeeViewModel employeeViewModel;

    public EmployeeAdapter(Context context, EmployeeViewModel employeeViewModel) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.employeeViewModel = employeeViewModel;
    }

    private static final DiffUtil.ItemCallback<Employee> DIFF_CALLBACK = new DiffUtil.ItemCallback<Employee>() {
        @Override
        public boolean areItemsTheSame(Employee oldItem, Employee newItem) {
            return oldItem.getE_id() == newItem.getE_id();
        }

        @Override
        public boolean areContentsTheSame(Employee oldItem, Employee newItem) {
            return oldItem.getE_first_name().equals(newItem.getE_first_name()) &&
                    oldItem.getE_last_name() .equals (newItem.getE_last_name()) &&
                    oldItem.getE_phone_number() .equals(newItem.getE_phone_number())  &&
                    oldItem.getE_date_of_birth().equals(newItem.getE_date_of_birth())&&
                    oldItem.getE_address().equals(newItem.getE_address())&&
                    oldItem.getE_hiring_date() .equals(newItem.getE_hiring_date())&&
                    oldItem.getTeam_id() == newItem.getTeam_id();
        }
    };

    @NonNull
    @Override
    public EmployeeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_employee, parent, false);
        return new EmployeeHolder(itemView);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EmployeeHolder holder, int position) {
        Employee currentEmployee = getItem(position);
        holder.employee_full_name.setText(currentEmployee.getE_first_name()+" "+currentEmployee.getE_last_name());
        holder.employee_phone_number.setText("Phone number: "+ currentEmployee.getE_phone_number());

        LiveData<Team> teamLiveData = employeeViewModel.getTeamById(currentEmployee.getTeam_id());
        teamLiveData.observe((LifecycleOwner) context, new Observer<Team>() {
            @Override
            public void onChanged(Team team) {
                // Set the team name in the employeeViewHolder
                if (team != null){
                    holder.employee_team_name.setText(team.getT_name());
                }
                else {
                    holder.employee_team_name.setText("please insert a team to this employee");
                }
                // Stop observing the LiveData to prevent unnecessary updates
                //teamLiveData.removeObserver(this);
            }
        });
    }

    public Employee getEmployeeAt (int position) {
        return getItem(position);
    }


    class EmployeeHolder extends RecyclerView.ViewHolder {
        private TextView employee_full_name;
        private TextView employee_phone_number;
        private TextView employee_team_name;


        public EmployeeHolder(View itemView) {
            super(itemView);
            employee_full_name = itemView.findViewById(R.id.employee_name);
            employee_phone_number = itemView.findViewById(R.id.employee_phone_number);
            employee_team_name = itemView.findViewById(R.id.employee_team_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }



}