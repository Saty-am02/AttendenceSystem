package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

public class ClassStudentList extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton imageButton;
    ArrayList<Student> list; // Change the type to Absent

    DatabaseReference databaseReference;
    myClassStudentList classStudentList; // Change the adapter type to MyStudentList

    String Class, ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_student_list);

        Class = getIntent().getStringExtra("ClassNames");
        ID = getIntent().getStringExtra("USER_ID");

        recyclerView = findViewById(R.id.recycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Absent").child("Faculty").child(ID).child(Class);
        imageButton =findViewById(R.id.imageButton);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classStudentList = new myClassStudentList(this, list, Class, ID); // Use MyStudentList adapter
        recyclerView.setAdapter(classStudentList);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before populating it
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Student student = snapshot1.getValue(Student.class);
                    list.add(student);
                }
                classStudentList.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ClassStudentList.this, MyClass.class));
            }
        });
    }
}
