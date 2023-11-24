package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

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

public class AbsentList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<StudentPresentStatus> list; // Change the type to Absent
    DatabaseReference databaseReference;
    AbsentStudentlist absent; // Change the adapter type to MyStudentList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absentlist);
        recyclerView = findViewById(R.id.recycler);


        Intent intent = getIntent();
        String FID = intent.getStringExtra("USER_ID");

        Calendar cal = Calendar.getInstance();
        String date = cal.get(Calendar.DAY_OF_MONTH) +"-"+(cal.get(Calendar.MONTH)+1)+"-"+cal.get(Calendar.YEAR);

        ArrayList<String> classList = new ArrayList<>();


        Spinner classSpinner = findViewById(R.id.classSpinner);
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this, R.layout.spinner_item_text, classList);
        classSpinner.setAdapter(spinnerAdapter);

        classSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String className = classList.get(position);
                list.clear();

                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("TakeAttendance").child(FID).child(date).child(className);
                dbRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1: snapshot.getChildren()){
                            StudentPresentStatus sp = snapshot1.getValue(StudentPresentStatus.class);
                            if(sp.isPresent())
                                continue;
                            list.add(sp);
                        }
                        absent.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("TakeAttendance").child(FID).child(date);
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        absent = new AbsentStudentlist(this, list); // Use MyStudentList adapter
        recyclerView.setAdapter(absent);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear(); // Clear the list before populating it
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    spinnerAdapter.add(snapshot1.getKey());
                    ArrayList<StudentPresentStatus> tempList = new ArrayList<>();
                }
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}