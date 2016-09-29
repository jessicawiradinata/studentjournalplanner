package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ImageButton journalButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setupBrowseButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void setupBrowseButton(){
        ImageButton browseButton = (ImageButton) findViewById(R.id.browseButton);
        ImageButton addButton = (ImageButton) findViewById(R.id.addButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(HomeActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, MyAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, ViewEventActivity.class));
            }
        });
    }
}
