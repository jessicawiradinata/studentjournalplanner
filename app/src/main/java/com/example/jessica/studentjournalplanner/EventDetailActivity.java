package com.example.jessica.studentjournalplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by timothyalfares on 9/27/2016.
 */
public class EventDetailActivity extends AppCompatActivity
{
    private TextView nameField;
    private TextView descField;
    private TextView locationField;
    private TextView dateField;
    private TextView timeField;
    private Button addEventBtn;
    private Firebase fRoot;
    private String link;
    private DatabaseReference mDatabase;
    private ImageButton journalButton;
    private ImageButton homeButton;
    private ImageButton browseButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        setReference();
        Bundle bundle = getIntent().getExtras();
        link = bundle.getString("stuff");
        fRoot = new Firebase("https://journalplanner-25d71.firebaseio.com/Events/"+link);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String users = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(users).child("Events");
        fRoot.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, String> map = dataSnapshot.getValue(Map.class);
                getData(map);
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(EventDetailActivity.this);
                alertDialog.setTitle("Do you want to add event to your list?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        submitData();
                        Toast.makeText(EventDetailActivity.this, "Event Added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EventDetailActivity.this, HomeActivity.class ));
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }
        });

        setupButton();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setReference() {
        nameField = (TextView) findViewById(R.id.nameField);
        nameField.setEnabled(false);
        descField = (TextView) findViewById(R.id.descField);
        descField.setEnabled(false);
        locationField = (TextView) findViewById(R.id.locationField);
        locationField.setEnabled(false);
        dateField = (TextView) findViewById(R.id.dateField);
        dateField.setEnabled(false);
        timeField = (TextView) findViewById(R.id.timeField);
        timeField.setEnabled(false);
        addEventBtn = (Button) findViewById(R.id.addEventBtn);
    }

    public void getData(Map<String, String> map)
    {
        String name = map.get("Name");
        String desc = map.get("Description");
        String location = map.get("Location");
        String date = map.get("Date");
        String time = map.get("Time");
        nameField.setText(name);
        descField.setText(desc);
        locationField.setText(location);
        dateField.setText(date);
        timeField.setText(time);
    }

    public void submitData()
    {
        String date = dateField.getText().toString();
        String location = locationField.getText().toString();
        String time = timeField.getText().toString();
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();
        DatabaseReference childRef = mDatabase.child(link);
        childRef.child("Name").setValue(name);
        childRef.child("Description").setValue(desc);
        childRef.child("Location").setValue(location);
        childRef.child("Date").setValue(date);
        childRef.child("Time").setValue(time);
    }


    private void setupButton(){
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        browseButton = (ImageButton) findViewById(R.id.browseButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(EventDetailActivity.this, HomeActivity.class));
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(EventDetailActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(EventDetailActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(EventDetailActivity.this, MyJournalActivity.class));
            }
        });
    }
}
