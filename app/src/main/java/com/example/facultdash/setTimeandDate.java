package com.example.facultdash;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

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
import java.util.Calendar;

public class setTimeandDate extends AppCompatActivity {

    Button selectedDate;

    String selectedDateString;
    RecyclerView recyclerView;

    private ArrayList<String> classList; // List to store class names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timeand_date);

        selectedDate = findViewById(R.id.selectDate);
        recyclerView = findViewById(R.id.recycler); // Initialize the RecyclerView

        String userID = getIntent().getStringExtra("USER_ID");

        classList = new ArrayList<>(); // Initialize the class list

        DatabaseReference databaseReference;
        ClassTakeAttendance classListAdapter = new ClassTakeAttendance(this, classList, userID);

        selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                Calendar cal = Calendar.getInstance();
                DatePickerDialog datePickerDialog = new DatePickerDialog(setTimeandDate.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                selectedDateString =  dayOfMonth + "-" + (month + 1) + "-" + year;
                                classListAdapter.SetDate(selectedDateString);
                                selectedDate.setText("Date: " + selectedDateString); // Display the selected date in the TextView
                            }
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)); // Initialize with a default date (2023-01-01)
                datePickerDialog.show();
            }
        });






        // Set up the Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("Absent")
                .child("Faculty")
                .child(userID);

        // Set up the RecyclerView and adapter
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
                // Handle database error (if needed)
            }
        });
    }
}
