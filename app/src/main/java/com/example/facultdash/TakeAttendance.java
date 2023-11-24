package com.example.facultdash;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TakeAttendance extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageButton imageButton;
    Button btnSubmit;
    ArrayList<Student> list; // Change the type to Absent

    DatabaseReference databaseReference;
    Takeattendancelist classStudentList; // Change the adapter type to MyStudentList


    CheckBox checkBox;
    String Class, ID,date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendence);

        btnSubmit = findViewById(R.id.btnsubmit);

        Class = getIntent().getStringExtra("ClassNames");
        ID = getIntent().getStringExtra("USER_ID");
        date = getIntent().getStringExtra("DATE");

        recyclerView = findViewById(R.id.recycler);
       // checkBox= findViewById(R.id.CheckallPresent);
        databaseReference = FirebaseDatabase.getInstance().getReference("Absent").child("Faculty").child(ID).child(Class);

        //imageButton =findViewById(R.id.imageButton);
        list = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        classStudentList = new Takeattendancelist(this, list, Class, ID); // Use MyStudentList adapter
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

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference = FirebaseDatabase.getInstance().getReference("TakeAttendance").child(ID).child(date).child(Class);
                DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("Absent").child("Faculty").child(ID).child(Class);
                for(Student student: list){
                    Map<String, Object> userData = new HashMap<>();

                    userData.put("UniqueID", student.getUniqueID());
                    userData.put("Name", student.getName());
                    userData.put("present", student.isPresent);

                    databaseReference.child(student.getUniqueID()).setValue(userData);

                    dbRef.child(student.getUniqueID()).get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            Absent ab = dataSnapshot.getValue(Absent.class);

                            Map<String, Object> absentTable = new HashMap<>();
                            absentTable.put("UniqueID", student.getUniqueID());
                            if(student.isPresent){

                                absentTable.put("TotalAbsent", ab.getTotalAbsent());
                                absentTable.put("TotalPresent", ab.getTotalPresent() + 1);
                            }else{
                                absentTable.put("TotalAbsent", ab.getTotalAbsent() + 1);
                                absentTable.put("TotalPresent", ab.getTotalPresent());
                            }

                            dbRef.child(student.getUniqueID()).updateChildren(absentTable);


                        }
                    });

                }
                Toast.makeText(TakeAttendance.this, "Successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

//        checkBox.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (checkBox.isChecked()) {
//                    // Checkbox is checked, perform your action here
//                    // For example, you can mark all students as present
//                    //markAllStudentsPresent();
//                    Toast.makeText(TakeAttendance.this, "checked", Toast.LENGTH_SHORT).show();
//                } else {
//                    // Checkbox is unchecked, perform your action here
//                    // For example, you can mark all students as absent
//                    //markAllStudentsAbsent();
//                }
//            }
//        });


//        imageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(TakeAttendance.this, MyClass.class));
//            }
//        });
    }
}
