package com.example.facultdash;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ClassTakeAttendance extends RecyclerView.Adapter<ClassTakeAttendance.MyViewHolder> {

    private  String Id,date;
    private Context context;
    private ArrayList<String> classList;
     // Variable for storing the userID

    public ClassTakeAttendance(Context context, ArrayList<String> list, String id) {
        this.context = context;
        this.classList = list;
        this.Id= id;
        
    }
    
    public void SetDate(String date){
        this.date = date;
        
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
                    if(date == null){
                        Toast.makeText(context, "Please select a date!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent = new Intent(context, TakeAttendance.class);
                    intent.putExtra("ClassNames", className);
                    intent.putExtra("USER_ID", Id);
                    intent.putExtra("DATE", date);// Pass the userID to the next activity
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if(classList == null){
            return 0;
        }
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
