package com.example.facultdash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class SUBSubjectAdapter extends RecyclerView.Adapter<SUBSubjectViewHolder> {
    ImageView faculty;
    Context context;
    List<SUBSubjectListItem> items;

    public SUBSubjectAdapter(Context context, List<SUBSubjectListItem> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public SUBSubjectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SUBSubjectViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_subject,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SUBSubjectViewHolder holder, int position) {
        holder.subject.setText(items.get(position).getSubject());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}


