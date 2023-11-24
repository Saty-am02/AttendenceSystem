package com.example.facultdash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Takeattendancelist extends RecyclerView.Adapter<Takeattendancelist.MyViewHolder> {

    private Context context;
    private ArrayList<Student> slist;
    private ArrayList<StudentPresentStatus> plist;

    private String FID, CID;

    public Takeattendancelist(Context context, ArrayList<Student> list, String classID, String facultyID) {
        this.context = context;
        this.slist = list;
        this.CID = classID;
        this.FID = facultyID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = slist.get(position);

        if (student != null) {
            holder.studentName.setText(student.getName());
            holder.reg.setText(student.getUniqueID());

            // Set the initial state of the present checkbox
            holder.present.setChecked(student.isPresent);

            // Handle individual checkbox click
            holder.present.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Update the student's checked status
                    student.SetPresent(holder.present.isChecked());
                }
            });
        } else {
            holder.studentName.setText("No one is Absent Today");
        }




    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, reg;
        Button submit;
        CheckBox present, allabsent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.studentName);
            reg = itemView.findViewById(R.id.RegNumber);
            present = itemView.findViewById(R.id.CheckPresent);



        }
    }
}
