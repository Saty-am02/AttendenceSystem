package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

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
import java.util.HashMap;
import java.util.Map;

public class studentList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<Student> list;
    ImageButton imageButton;
    DatabaseReference databaseReference, databaseReferenceSend;
    MyStudentList students;
    String Class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        recyclerView = findViewById(R.id.recycler);
        databaseReference = FirebaseDatabase.getInstance().getReference("Student");
        imageButton = findViewById(R.id.imageButton);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        students = new MyStudentList(this, list);
        recyclerView.setAdapter(students);

        String userID = getIntent().getStringExtra("USER_ID");
        Class = getIntent().getStringExtra("CLASS");

        // Initialize the reference to the "Absent" node for the current faculty user
        databaseReferenceSend = FirebaseDatabase.getInstance().getReference("Absent")
                .child("Faculty")
                .child(userID);

        // Send the data to Firebase when the "submitBtn" is clicked
        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Student> selectedList = students.selectedList;

                for (Student student : selectedList) {
                    Map<String, Object> values = new HashMap<>();
                    values.put("Name", student.getName());
                    values.put("UniqueID", student.getUniqueID());
                    values.put("TotalAbsent", 1);
                    values.put("TotalPresent", 1);

                    // Save the absent data for the selected students in the database
                    databaseReferenceSend.child(Class).child(student.getUniqueID()).setValue(values);
                }
                Toast.makeText(studentList.this, "Data sent successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Listen for changes in the "Student" node in Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Student student = snapshot1.getValue(Student.class);
                    list.add(student);
                }
                students.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error (e.g., log or display an error message)
                Toast.makeText(studentList.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // Navigate to MyClass activity when the image button is clicked
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(studentList.this, MyClass.class));
            }
        });

    }
}
