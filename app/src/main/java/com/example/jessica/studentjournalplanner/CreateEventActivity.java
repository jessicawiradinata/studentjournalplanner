package com.example.jessica.studentjournalplanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by timothyalfares on 9/27/2016.
 */
public class CreateEventActivity extends AppCompatActivity
{
    private EditText dateField;
    private EditText locationField;
    private EditText timeField;
    private EditText nameField;
    private EditText descField;
    private Button createBtn;
    private DatabaseReference dataRef;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        dateField = (EditText) findViewById(R.id.dateField);
        locationField = (EditText) findViewById(R.id.locationField);
        timeField = (EditText) findViewById(R.id.timeField);
        nameField = (EditText) findViewById(R.id.nameField);
        descField = (EditText) findViewById(R.id.descField);
        createBtn = (Button) findViewById(R.id.createBtn);
        dataRef = FirebaseDatabase.getInstance().getReference().child("Events");

        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String date = dateField.getText().toString();
                String location = locationField.getText().toString();
                String time = timeField.getText().toString();
                String name = nameField.getText().toString();
                String desc = descField.getText().toString();


                DatabaseReference newSession = dataRef.push();
                newSession.child("Name").setValue(name);
                newSession.child("Description").setValue(desc);
                newSession.child("Location").setValue(location);
                newSession.child("Date").setValue(date);
                newSession.child("Time").setValue(time);
            }
        });
    }
}