package com.example.facultdash;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class SUBMainAdapterStudentList extends FirebaseRecyclerAdapter<SUBMainModelStudentList, SUBMainAdapterStudentList.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */


    public SUBMainAdapterStudentList(@NonNull FirebaseRecyclerOptions<SUBMainModelStudentList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull SUBMainModelStudentList model) {
        holder.Name.setText(model.getName());


        // onclick for each item listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), holder.Name.getText().toString(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), SubPerticularStudentProfile.class);
                Bundle extras = intent.getExtras();
                intent.putExtra("Name",holder.Name.getText().toString());
                startActivity(v.getContext(),intent,extras);

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_list_item,parent,false);
        return new myViewHolder(view);
    }

    //    create myViewHolder class
    class myViewHolder extends RecyclerView.ViewHolder{
        TextView Name;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
//        register all declared variable(i.e name)
            Name = (TextView) itemView.findViewById(R.id.nametxt);


        }
    }

}

