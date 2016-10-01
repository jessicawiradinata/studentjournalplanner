package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
public class AssignmentDetailActivity extends AppCompatActivity {
    private EditText nameField;
    private EditText descField;
    private EditText subjectField;
    private EditText dateField;
    private Button deleteAssignmentBtn;
    private DatabaseReference dataRef;
    private Firebase fRoot;
    private String link;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_detail);
        setReference();
        Bundle bundle = getIntent().getExtras();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        link = bundle.getString("stuff");
        fRoot = new Firebase("https://journalplanner-25d71.firebaseio.com/Users/"+uid+"/Assignments/"+link);
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
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void setReference() {
        nameField = (EditText) findViewById(R.id.nameField);
        nameField.setEnabled(false);
        descField = (EditText) findViewById(R.id.descField);
        descField.setEnabled(false);
        subjectField = (EditText) findViewById(R.id.subjectField);
        subjectField.setEnabled(false);
        dateField = (EditText) findViewById(R.id.dateField);
        dateField.setEnabled(false);
        deleteAssignmentBtn = (Button) findViewById(R.id.addEventBtn);
    }

    public void getData(Map<String, String> map)
    {
        String name = map.get("Name");
        String desc = map.get("Description");
        String subject = map.get("Subject");
        String date = map.get("dueDate");
        nameField.setText(name);
        descField.setText(desc);
        subjectField.setText(subject);
        dateField.setText(date);
    }

    public void submitData()
    {
        String date = dateField.getText().toString();
        String subject = subjectField.getText().toString();
        String name = nameField.getText().toString();
        String desc = descField.getText().toString();
        DatabaseReference childRef = dataRef.child(link);
        childRef.child("Name").setValue(name);
        childRef.child("Description").setValue(desc);
        childRef.child("Subject").setValue(subject);
        childRef.child("dueDate").setValue(date);
    }

}
