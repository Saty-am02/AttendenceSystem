package com.example.facultdash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassListOfFaculty extends RecyclerView.Adapter<ClassListOfFaculty.MyViewHolder> {

    private  String Id;
    private Context context;
    private ArrayList<String> classList;
    private String userID ="F220970121"; // Variable for storing the userID

    public ClassListOfFaculty(Context context, ArrayList<String> list, String ID) {
        this.context = context;
        this.classList = list;
        this.Id= ID;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.classlistoffaculty, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String className = classList.get(position);

        if (className != null) {
            holder.className.setText(className);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ClassStudentList.class);
                    intent.putExtra("ClassNames", className);
                    intent.putExtra("USER_ID", Id); // Pass the userID to the next activity
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return classList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView className;
        LinearLayout clickon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            className = itemView.findViewById(R.id.className);
            clickon = itemView.findViewById(R.id.classnameClick);
        }
    }
}
