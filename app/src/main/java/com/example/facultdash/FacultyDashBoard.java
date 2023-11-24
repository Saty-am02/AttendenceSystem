package com.example.facultdash;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.collection.CircularArray;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;

import com.google.android.material.navigation.NavigationView;

public class FacultyDashBoard extends AppCompatActivity {
    ImageButton takeattendance, imgBtn_menuIcon, imageButton_studentList;
    TextView txtv1, txt_attendanceflow1, txt_attendanceflow2, txt_studentList, qoutes;
    FrameLayout frml, frm3, frmlayout, frame_studentList, absentstu;
    ProgressBar prgbar1, prgbar2, prgbar3, progress_studentList;
    ImageView imgv;
    RelativeLayout relativeLayout;
    Button btnattendance_progress;
    Button logoutButton;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private Handler handler = new Handler();
    private int currentIndex = 0;
    CircularArray<String> circularArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_dash_board);

        // Initialize your elements
        txtv1 = findViewById(R.id.text_takeattendance);
        takeattendance = findViewById(R.id.imageButton4);
        frml = findViewById(R.id.frame4);
        prgbar1 = findViewById(R.id.progress1);

        // for faculty page intent
        imgv = findViewById(R.id.profileImageView);
        relativeLayout = findViewById(R.id.profileLayout);
        absentstu = findViewById(R.id.frame1);

        // for absent student
        prgbar2 = findViewById(R.id.prgbarblue);

        frame_studentList = findViewById(R.id.frame2);
        progress_studentList = findViewById(R.id.progressbar_studentList);
        imageButton_studentList = findViewById(R.id.imageButton6);

        // for attendance flow
        frmlayout = findViewById(R.id.frm_attendance_flow);

        // Initialize the logout button
        logoutButton = findViewById(R.id.logoutbutton);

        // for navigationView
        imgBtn_menuIcon = findViewById(R.id.imgBtn_menuIcon);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        frm3 = findViewById(R.id.frame3);

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
                    colorChangeAnim.setDuration(2500); // 2.5 seconds for fade-in and fade-out

                    int startColor = Color.BLACK;
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
                    handler.postDelayed(this, 3000); // Delay for 3 seconds before showing the next quote
                }
            }
        };

// Start the text update process
        handler.post(updateTextRunnable);

        // Start the text update process
        handler.post(updateTextRunnable);

        // Add click listeners for your elements
        takeattendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the click for taking attendance
            }
        });

        absentstu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FacultyDashBoard.this, AbsentList.class));
            }
        });

        frm3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click for the third activity
                String userID = getIntent().getStringExtra("USER_ID");
                Intent intent = new Intent(FacultyDashBoard.this, MyClass.class);
                intent.putExtra("USER_ID", userID);
                startActivity(intent);
            }
        });

        // Handle other click listeners...

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
