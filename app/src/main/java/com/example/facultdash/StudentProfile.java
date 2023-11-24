//package com.example.home;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//public class StudentProfile extends AppCompatActivity {
//
//    DatabaseReference databaseReference;
//    String ID, FID, CID;
//
//    Absent curStudent;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_student_profile);
//
//        TextView nameTV = findViewById(R.id.textViewStudent);
//        TextView classTV = findViewById(R.id.textViewClass);
//        TextView absentTV = findViewById(R.id.textViewAbsentCount);
//        TextView presentTV = findViewById(R.id.textViewPresentCount);
//        TextView totalclassTV = findViewById(R.id.textViewTotalCount);
//
//        Intent intent = getIntent();
//        ID = intent.getStringExtra("STUDENT_ID");
//        FID = intent.getStringExtra("FACULTY_ID");
//        CID = intent.getStringExtra("CLASS_ID");
//       // Toast.makeText(this, ID, Toast.LENGTH_SHORT).show();
//        databaseReference = FirebaseDatabase.getInstance().getReference("Absent").child("Faculty").child(FID).child(CID);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot snapshot1: snapshot.getChildren()){
//                    Absent student = snapshot1.getValue(Absent.class);
//                    if(student.getUniqueID().equals(ID)){
//                        curStudent = student;
////                        Toast.makeText(StudentProfile.this, curStudent.getName(), Toast.LENGTH_SHORT).show();
//                        nameTV.setText(curStudent.getName());
//                        classTV.setText(CID);
//                        absentTV.setText(String.valueOf(curStudent.getTotalAbsent()));
//                        presentTV.setText(String.valueOf(curStudent.getTotalPresent()));
//                        totalclassTV.setText(String.valueOf(curStudent.getTotalAbsent() + curStudent.getTotalPresent()));
//
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//}
package com.example.facultdash;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StudentProfile extends AppCompatActivity {

    DatabaseReference databaseReference;
    String ID, FID, CID;
    long totalPresent;
    Absent curStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_profile);

        TextView nameTV = findViewById(R.id.textViewStudent);
        TextView classTV = findViewById(R.id.textViewClass);
        TextView absentTV = findViewById(R.id.textViewAbsentCount);
        TextView presentTV = findViewById(R.id.textViewPresentCount);
        TextView totalclassTV = findViewById(R.id.textViewTotalCount);

        Intent intent = getIntent();
        ID = intent.getStringExtra("STUDENT_ID");
        FID = intent.getStringExtra("FACULTY_ID");
        CID = intent.getStringExtra("CLASS_ID");

        // Construct the Firebase database reference path correctly
        databaseReference = FirebaseDatabase.getInstance().getReference("Absent")
                .child("Faculty")
                .child(FID)
                .child(CID);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Absent student = snapshot1.getValue(Absent.class);
                    if (student != null && student.getUniqueID() != null && student.getUniqueID().equals(ID)) {
                        curStudent = student;

                        // Check if the "TotalPresent" field exists in the database
//                        if (snapshot1.child("TotalPresent").exists()) {
//                            //totalPresent = snapshot1.child("TotalPresent").getValue(Long.class);
//                            presentTV.setText(String.valueOf(curStudent.getTotalAbsent()));
//                        } else {
//                            // Handle the case where "TotalPresent" is missing or null
//                            presentTV.setText("N/A");
//                        }

                        nameTV.setText(curStudent.getName());
                        classTV.setText(CID);
                        absentTV.setText(String.valueOf(curStudent.getTotalAbsent()));
                        presentTV.setText(String.valueOf(curStudent.getTotalPresent()));
                        totalclassTV.setText(String.valueOf(curStudent.getTotalAbsent() + curStudent.getTotalPresent()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(StudentProfile.this, "Database Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
