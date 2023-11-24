package com.example.facultdash;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class SUBSubjectViewHolder extends RecyclerView.ViewHolder {
    TextView subject;
    public SUBSubjectViewHolder(@NonNull View itemView){
        super(itemView);
        subject= itemView.findViewById(R.id.subject);

    }


}
