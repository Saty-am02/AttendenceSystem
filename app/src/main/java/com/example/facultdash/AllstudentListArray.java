package com.example.facultdash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AllstudentListArray extends RecyclerView.Adapter<AllstudentListArray.MyViewHolder> {

    private Context context;
    private ArrayList<Student> slist;

    public AllstudentListArray(Context context, ArrayList<Student> list) {
        this.context = context;
        this.slist = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_list_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = slist.get(position);

        // Check if the student is not null
        if (student != null) {
            holder.studentName.setText(student.getName());
        } else {
            holder.studentName.setText("No one is Absent Today");
            // Handle the case where student is null, e.g., set a default value or log an error.
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, student.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, StudentProfile.class);
                intent.putExtra("STUDENT_ID", student.getUniqueID());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.nametxt);
        }
    }
}
