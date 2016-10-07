package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

/**
 * Created by timothyalfares on 9/30/2016.
 */
public class MyJournalActivity extends AppCompatActivity
{
    private ImageButton journalButton;
    private ImageButton homeButton;
    private ImageButton browseButton;
    private ImageButton addButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_journal);

        View viewAss = findViewById(R.id.viewAss);

        View viewEvent= findViewById(R.id.viewEvent);

        View assHistory = findViewById(R.id.assHistory);

        View eventHistory = findViewById(R.id.eventHistory);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {

                }
            }
        };
        final TextView username = (TextView) findViewById(R.id.username);
        final TextView email = (TextView) findViewById(R.id.email);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Firebase fRoot = new Firebase("https://journalplanner-25d71.firebaseio.com/Users/"+user.getUid());
        fRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                String fname = map.get("firstName");
                String lname = map.get("lastName");
                username.setText(fname +" " + lname);
                String emailAddress = map.get("email");
                email.setText(emailAddress);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });

        setupButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void viewAss(View view) {
        Intent viewAssIntent = new Intent(this, MyAssignmentActivity.class);
        startActivity(viewAssIntent);
    }

    public void viewEvent(View view) {
        Intent viewEventIntent = new Intent(this, ViewEventActivity.class);
        startActivity(viewEventIntent);
    }

    public void assHistory(View view) {
        Intent assHistoryIntent = new Intent(this, AssignmentHistoryActivity.class);
        startActivity(assHistoryIntent);
    }

    public void eventHistory(View view) {
        Intent eventHistoryIntent = new Intent(this, EventHistoryActivity.class);
        startActivity(eventHistoryIntent);
    }

    public void logout(View view) {
        startSignOut();
        Toast.makeText(MyJournalActivity.this, "Logging out...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MyJournalActivity.this, MainActivity.class));
    }

    private void startSignOut() {
        mAuth.signOut();
    }

    private void setupButton(){
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        browseButton = (ImageButton) findViewById(R.id.browseButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MyJournalActivity.this, HomeActivity.class));
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MyJournalActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyJournalActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyJournalActivity.this, MyJournalActivity.class));
            }
        });
    }
}
