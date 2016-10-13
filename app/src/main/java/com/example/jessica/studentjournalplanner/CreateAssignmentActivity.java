package com.example.jessica.studentjournalplanner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by timothyalfares on 9/27/2016.
 */
public class CreateAssignmentActivity extends AppCompatActivity implements AssignmentManagement
{
    private EditText dueDateField;
    private EditText subjectField;
    private EditText nameField;
    private EditText descField;
    private Button createBtn;
    private DatabaseReference dataRef;
    private ImageButton journalButton;
    private ImageButton homeButton;
    private ImageButton browseButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_assignment);

        dueDateField = (EditText) findViewById(R.id.dueDateField);
        subjectField = (EditText) findViewById(R.id.subjectField);
        nameField = (EditText) findViewById(R.id.nameField);
        descField = (EditText) findViewById(R.id.descField);
        createBtn = (Button) findViewById(R.id.createBtn);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        dataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid).child("Assignments");

        createBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CreateAssignmentActivity.this);
                alertDialog.setTitle("Do you want to add assignment to your list?");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDialog.setNegativeButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        createAssignment();
                        Toast.makeText(CreateAssignmentActivity.this, "Assignment added", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(CreateAssignmentActivity.this, HomeActivity.class ));
                    }
                });
                alertDialog.create();
                alertDialog.show();
            }

        });

        setupButton();
    }

    public void createAssignment()
    {
        String due = dueDateField.getText().toString();
        String subject = subjectField.getText().toString();
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();
        DatabaseReference newSession = dataRef.push();
        newSession.child("Name").setValue(name);
        newSession.child("Subject").setValue(subject);
        newSession.child("Description").setValue(desc);
        newSession.child("dueDate").setValue(due);
    }

    private void setupButton(){
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        browseButton = (ImageButton) findViewById(R.id.browseButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(CreateAssignmentActivity.this, HomeActivity.class));
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(CreateAssignmentActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CreateAssignmentActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(CreateAssignmentActivity.this, MyJournalActivity.class));
            }
        });
    }

}