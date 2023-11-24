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

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<Student> list;
    private ArrayList<Student> absentOrPresent;

    public MainAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
        this.absentOrPresent = new ArrayList<>();
    }

    public MainAdapter(FirebaseRecyclerOptions<MainModel> options) {

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Student student = list.get(position);

        if (student != null) {
            holder.Name.setText(student.getName());
            holder.UniqueID.setText(student.getUniqueID());

            // Set the initial state of the checkboxes based on absentOrPresent list
            holder.present.setChecked(absentOrPresent.contains(student));

            // Add an OnCheckedChangeListener to the checkboxes
            holder.present.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    // Checkbox is checked
                    absentOrPresent.add(student);
                    Toast.makeText(context, student.getName() + " : Added", Toast.LENGTH_SHORT).show();
                } else {
                    // Checkbox is unchecked
                    absentOrPresent.remove(student);
                    Toast.makeText(context, student.getName() + " : Removed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name, UniqueID;
        CheckBox present;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name = itemView.findViewById(R.id.studentName);
            UniqueID = itemView.findViewById(R.id.RegNumber);
            present = itemView.findViewById(R.id.CheckPresent);
        }
    }
}
