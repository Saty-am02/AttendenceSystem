package com.example.facultdash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyStudentList extends RecyclerView.Adapter<MyStudentList.MyViewHolder> {

    private Context context;
    private ArrayList<Student> list;
    ArrayList<Student> selectedList;

    public MyStudentList(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
        this.selectedList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.student_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);

        // Check if the student is not null
        if (student != null) {
            holder.studentName.setText(student.getName());

            // Set the initial state of the checkbox based on selectedList
            holder.checkBox.setChecked(selectedList.contains(student));

            // Add an OnCheckedChangeListener to the checkbox
            holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Checkbox is checked
                    selectedList.add(student);
                     Toast.makeText(context,   student.getName()+" : Added", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox is unchecked
                    selectedList.remove(student);
                    Toast.makeText(context,   student.getName()+" : Removed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            checkBox = itemView.findViewById(R.id.checkBox);
        }
    }
}
