package com.example.facultdash;

import android.os.Bundle;
import android.widget.ImageButton;

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

public class AllstudentList extends AppCompatActivity {

            RecyclerView recyclerView;
            ImageButton imageButton;
            ArrayList<Student> list; // Change the type to Absent

            DatabaseReference databaseReference;
            AllstudentListArray allStudentList; // Change the adapter type to MyStudentList

            String Class, ID;
            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_allstudent_list);

//                Class = getIntent().getStringExtra("ClassNames");
//                ID = getIntent().getStringExtra("USER_ID");

                recyclerView = findViewById(R.id.recycler);
                databaseReference = FirebaseDatabase.getInstance().getReference("Student");

                list = new ArrayList<>();
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                allStudentList = new AllstudentListArray(this, list); // Use MyStudentList adapter
                recyclerView.setAdapter(allStudentList);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear(); // Clear the list before populating it
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            Student student = snapshot1.getValue(Student.class);
                            list.add(student);
                        }
                        allStudentList.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        }

