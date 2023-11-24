package com.example.facultdash;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FacultyList extends AppCompatActivity {


    RecyclerView recyclerView;
    ArrayList<Faculty> flist; // Change the type to Absent
    DatabaseReference databaseReference;
    FacultyListOfClass absent; // Change the adapter type to MyStudentList
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_list);

        recyclerView = findViewById(R.id.recycler);

        databaseReference = FirebaseDatabase.getInstance().getReference("Faculty");

        flist = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        absent = new FacultyListOfClass(this, flist); // Use MyStudentList adapter
        recyclerView.setAdapter(absent);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flist.clear(); // Clear the list before populating it
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Faculty absent = snapshot1.getValue(Faculty.class);
                    flist.add(absent);
                    System.out.println(flist);
                }
                absent.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
