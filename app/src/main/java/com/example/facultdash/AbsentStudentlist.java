package com.example.facultdash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AbsentStudentlist extends RecyclerView.Adapter<AbsentStudentlist.MyViewHolder> {

    private Context context;
    private ArrayList<StudentPresentStatus> Absentslist;

    public AbsentStudentlist(Context context, ArrayList<StudentPresentStatus> list) {
        this.context = context;
        this.Absentslist = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.absent_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        StudentPresentStatus absent = Absentslist.get(position);

        // Check if the student is not null
        if (absent != null) {
            holder.studentName.setText(absent.getName());
            holder.RegNumber.setText(absent.getUniqueID());
        } else {
            holder.studentName.setText("No one is Absent Today");
            holder.RegNumber.setText("");

            //Handle the case where student is null, e.g., set a default value or log an error.
        }
    }

    @Override
    public int getItemCount() {
        return Absentslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName,RegNumber;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            RegNumber = itemView.findViewById(R.id.RegNumber);
        }
    }
}
