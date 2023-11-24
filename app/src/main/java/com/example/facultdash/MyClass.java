package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class MyClass extends AppCompatActivity {
    LinearLayout linearLayout, Addclass, addStudentLiLayout, ChooseClass;
    EditText enterClassNameLL;
    RecyclerView recyclerView;
    int onoff = 1;
    private ArrayList<String> classList; // List to store class names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_class);

        linearLayout = findViewById(R.id.addStudentLiLayout);
        ChooseClass = findViewById(R.id.linearLayout5);
        Addclass = findViewById(R.id.linearLayout);
        enterClassNameLL = findViewById(R.id.editTextClass);
        addStudentLiLayout = findViewById(R.id.addStudentLiLayout);
        recyclerView = findViewById(R.id.recyclerChoose);

        // Getting user ID from faculty dashboard
        String userID = getIntent().getStringExtra("USER_ID");

        classList = new ArrayList<>(); // Initialize the class list
        ClassListOfFaculty classListAdapter;

            // Set up the Firebase database reference
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Absent")
                    .child("Faculty")
                    .child(userID);

        // Set up the RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classListAdapter = new ClassListOfFaculty(this, classList, userID);
        recyclerView.setAdapter(classListAdapter);

            // Listen for changes in the Firebase database and populate the class list
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    classList.clear(); // Clear the class list before populating it
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        // Get the class name from the database
                        String className = snapshot1.getKey();
                        classList.add(className);
                    }
                    classListAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle database error (e.g., log it)
                    Log.e("DatabaseError", "Error: " + error.getMessage());
                }
            });





            // Code to show/hide the class input fields
            Addclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onoff == 1) {
                        enterClassNameLL.setVisibility(View.VISIBLE);
                        addStudentLiLayout.setVisibility(View.VISIBLE);
                        onoff = 0;
                    } else if (onoff == 0) {
                        enterClassNameLL.setVisibility(View.GONE);
                        addStudentLiLayout.setVisibility(View.GONE);
                        onoff = 1;
                    }
                }
            });

            // Code to handle class selection and navigation to the student list
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String className = enterClassNameLL.getText().toString();

                    if (!className.isEmpty()) {
                        Intent intent = new Intent(MyClass.this, studentList.class); // Assuming StudentList is the correct class name
                        intent.putExtra("CLASS", className);
                        intent.putExtra("USER_ID", userID);
                        startActivity(intent);
                    } else {
                        // Show a toast message if the class name is not entered
                        Toast.makeText(MyClass.this, "Please enter a class name first", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            // Code to show/hide the list of classes
            ChooseClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onoff == 1) {
                        recyclerView.setVisibility(View.VISIBLE);
                        onoff = 0;
                    } else if (onoff == 0) {
                        recyclerView.setVisibility(View.GONE);
                        onoff = 1;
                    }
                }
            });
        }


    }




