package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by timothyalfares on 9/27/2016.
 */
public class CreateAssignmentActivity extends AppCompatActivity
{
    private EditText dueDateField;
    private EditText subjectField;
    private EditText nameField;
    private EditText descField;
    private Button createBtn;
    private DatabaseReference dataRef;

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
            public void onClick(View v)
            {
                createAssignment();
                startActivity(new Intent(CreateAssignmentActivity.this, HomeActivity.class ));
            }
        });
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

}