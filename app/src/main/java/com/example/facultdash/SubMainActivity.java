package com.example.facultdash;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SubMainActivity extends AppCompatActivity {


    String fName;
    String fEmail;
    String fCourse;

    String fPassword;
    String fProgram;
    String fPhone;
    String fRole;
    String fUniqueID;

    ImageButton imgBtn_menuIcon;

    FrameLayout FrameAbsentList, Frame_takeattendance, frame_studentList, frm_myclass;
    ImageView imgv;
    RelativeLayout relativeLayout;
    Button btnattendance_progress;
    Button logoutButton;  // Add the logout button

    DrawerLayout drawerLayout;
    NavigationView navigationView;

    private Handler handler = new Handler();
    private int currentIndex = 0;
    CircularArray<String> circularArray;
    TextView nameTextView, qoutes,emailTextView, courseTextView, programTextView, passwordTextView, phoneTextView, roleTextView, uniqueIDTextView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_main);

        imgv=findViewById(R.id.profileImageView);
        // Initialize your elements
        FrameAbsentList = findViewById(R.id.frame1);
        frm_myclass = findViewById(R.id.frame3);
        Frame_takeattendance = findViewById(R.id.frame4);
        frame_studentList = findViewById(R.id.frame2);


        // Initialize the logout button
        logoutButton = findViewById(R.id.logoutbutton);

        //for navigationView
        imgBtn_menuIcon = findViewById(R.id.imgBtn_menuIcon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //for retrieving data from the database, to set faculty data
        nameTextView = findViewById(R.id.t1);
        emailTextView = findViewById(R.id.t2);
        courseTextView = findViewById(R.id.t3);
        programTextView = findViewById(R.id.t4);
        passwordTextView = findViewById(R.id.t5);
        phoneTextView = findViewById(R.id.t6);
        roleTextView = findViewById(R.id.t7);
        uniqueIDTextView = findViewById(R.id.t8);


        circularArray = new CircularArray<>();
        qoutes = findViewById(R.id.Quets);
        circularArray.addFirst("“Progress is quiet until it isn't.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("“And sometimes it's as simple as changing your environment.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("“Every battle is lost or won in the arena of the mind.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("“Time the time before the time time you”\n\n" +
                "― Bello Salihu");
        circularArray.addFirst("“Life is an outward projection of one's perceived self value.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("“Love is free but it'll cost you.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("“If you don't have haters in your way of success, It means you are on wrong way!”\n\n" +
                "― Hassanwainx");

        circularArray.addFirst("“You are the draftsman and craftsman of your own destiny.”\n\n" +
                "― Napz Cherub Pellazo");
        circularArray.addFirst("“Life is a matter of choosing what is good for you.”\n\n" +
                "― Napz Cherub Pellazo");
        circularArray.addFirst("“Ang simula at wakas ay magkamukha; sa bawat wakas ay may nakalaang simula.”\n\n" +
                "― Napz Cherub Pellazo");
        circularArray.addFirst("“Innerstand this: Life could be great if you did more things you're proud of.”\n\n" +
                "― Kierra C.T. Banks");
        circularArray.addFirst("\"Well done is better than well said.\"\n\n- Benjamin Franklin");
        circularArray.addFirst("\"Be yourself; everyone else is already taken.\"\n\n- Oscar Wilde");
        circularArray.addFirst("\"If life were predictable it would cease to be life and be without flavor.\"\n\n- Eleanor Roosevelt");
        circularArray.addFirst(" \"In the end, it's not the years in your life that count. It's the life in your years.\"\n\n- Abraham Lincoln");
        circularArray.addFirst(" \"Life is a succession of lessons which must be lived to be understood.\"\n\n- Ralph Waldo Emerson");
        circularArray.addFirst(" \"You will face many defeats in life, but never let yourself be defeated.\"\n\n- Maya Angelou");
        circularArray.addFirst(" \"Never let the fear of striking out keep you from playing the game.\"\n\n- Babe Ruth");
        circularArray.addFirst(" \"Life is never fair, and perhaps it is a good thing for most of us that it is not.\"\n\n- Oscar Wilde");
        circularArray.addFirst(" \"The only impossible journey is the one you never begin.\"\n\n- Tony Robbins");
        circularArray.addFirst(" \"In this life we cannot do great things. We can only do small things with great love.\"\n\n- Mother Teresa");
        circularArray.addFirst(" \"You only live once, but if you do it right, once is enough.\"\n\n- Mae West");

        Runnable updateTextRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentIndex < circularArray.size()) {
                    String item = circularArray.get(currentIndex);
                    qoutes.setText(item);
                    currentIndex = (currentIndex + 1) % circularArray.size();

                    // Create a new ValueAnimator for text color change
                    ValueAnimator colorChangeAnim = ValueAnimator.ofFloat(0, 1);
                    colorChangeAnim.setDuration(4500); // 2.5 seconds for fade-in and fade-out
                    int startColor = getResources().getColor(R.color.gray2);
                    int endColor = Color.WHITE;

                    colorChangeAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            float fraction = animation.getAnimatedFraction();
                            int runColor = (int) ArgbEvaluator.getInstance().evaluate(fraction, startColor, endColor);
                            qoutes.setTextColor(runColor);
                        }
                    });

                    colorChangeAnim.start();

                    // Delay before starting the next quote
                    handler.postDelayed(this, 5000); // Delay for 3 seconds before showing the next quote
                }
            }
        };

// Start the text update process
        handler.post(updateTextRunnable);

        // Start the text update process
        handler.post(updateTextRunnable);

        String userID = getIntent().getStringExtra("USER_ID");

        // Set up onClickListeners for your elements
        setLogoutButtonClickListener(); // Set up the logout button click listener




        if (userID == null) {
            startActivity(new Intent(SubMainActivity.this, MainActivity.class));
        }

//        // Apply a fade-in animation to the TextView
        AlphaAnimation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000); // 1-second duration
        qoutes.startAnimation(fadeInAnimation);


        //using shared preference to store uniqueid so that we can use this in faculty_profile_edit page
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String facultyUniqueId =userID ; // Replace this with the actual unique ID you want to store

        editor.putString("userID", facultyUniqueId);
        editor.apply();

        getFacultyData(userID); // Retrieve and set faculty data
    }


    // Function to retrieve and set faculty data from the database
    private void getFacultyData(String userID) {
        //Toast.makeText(SubMainActivity.this, userID, Toast.LENGTH_SHORT).show();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference facultyRef = database.getReference("Faculty");

        // Query the database to find the faculty member with the matching unique ID
        Query query = facultyRef.orderByChild("UniqueID").equalTo(userID);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    DataSnapshot facultySnapshot = dataSnapshot.getChildren().iterator().next(); // Get the first matching result

                    fCourse = facultySnapshot.child("Course").getValue(String.class);
                    fEmail = facultySnapshot.child("Email").getValue(String.class);
                    fName = facultySnapshot.child("Name").getValue(String.class);
                    fPassword= facultySnapshot.child("Password").getValue(String.class);
                    fPhone= facultySnapshot.child("Phone").getValue(String.class);
                    fProgram= facultySnapshot.child("Program").getValue(String.class);
                    fRole= facultySnapshot.child("Role").getValue(String.class);
                    fUniqueID= facultySnapshot.child("UniqueID").getValue(String.class);


                } else {
                    // Handle the case when no data with the specified unique ID is found
                    Log.e("FirebaseDataError", "No data found for uniqueID: " + userID);
                    Toast.makeText(SubMainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database read errors if necessary
                Log.e("FirebaseError", "Error: " + databaseError.getMessage());
            }
        });

        // Set up onClickListener for FrameLayout
            FrameAbsentList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), TakeAttendance.class));

                }
            });

            // Set up onClickListener for ProgressBar




            // Intent for faculty profile/edit page
            imgv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent profilePage = new Intent(getApplicationContext(), FacultyProfile.class);
                    profilePage.putExtra("fCourse",fCourse);
                    profilePage.putExtra("fEmail",fEmail);
                    profilePage.putExtra("fName",fName);
                    profilePage.putExtra("fPassword",fPassword);
                    profilePage.putExtra("fPhone",fPhone);
                    profilePage.putExtra("fProgram",fProgram);
                    profilePage.putExtra("fRole",fRole);
                    profilePage.putExtra("fUniqueID",fUniqueID);

                    startActivity(profilePage);
                }
            });


            // Set up onClickListener for ImageButton (Absent Student)
            FrameAbsentList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AbsentList.class); // Assuming StudentList is the correct class name
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            });

            // Set up onClickListener for FrameLayout
            Frame_takeattendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), setTimeandDate.class); // Assuming StudentList is the correct class name
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            });


            // Intent for attendance flow, Set up onClickListener for TextView
            frame_studentList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), AllstudentList.class); // Assuming StudentList is the correct class name
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            });


            frm_myclass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), MyClass.class); // Assuming StudentList is the correct class name
                    intent.putExtra("USER_ID", userID);
                    startActivity(intent);
                }
            });

            imgBtn_menuIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });

            // NavigationView item OnClickListener
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_take_attendance) {
                        startActivity(new Intent(getApplicationContext(), TakeAttendance.class));
                    } else if (itemId == R.id.nav_myclass) {
                        startActivity(new Intent(getApplicationContext(), MyClass.class));
                    } else if (itemId == R.id.nav_studentlist) {
                        startActivity(new Intent(getApplicationContext(), AllstudentList.class));
                    } else if (itemId == R.id.nav_absentstudent) {
                        startActivity(new Intent(getApplicationContext(), AbsentStudent.class));
                    }  else if (itemId == R.id.nav_sendMail) {
                        startActivity(new Intent(getApplicationContext(), SendMail.class));
                    } else {
                        showLogoutDialog();
                    }

                    drawerLayout.closeDrawer(GravityCompat.START);
                    return true;
                }
            });
        }


    private void setLogoutButtonClickListener() {
        // Set up onClickListener for the logout button
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogoutDialog();
            }
        });
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Handle the logout action here (e.g., clear user data)
                        // Redirect to the login page (assuming LoginActivity is the login page)
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish(); // Finish the current activity
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User clicked "No," so dismiss the dialog
                        dialog.dismiss();
                    }
                });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


}
