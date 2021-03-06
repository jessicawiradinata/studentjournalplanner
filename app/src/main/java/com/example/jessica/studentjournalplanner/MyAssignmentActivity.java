package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyAssignmentActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    private ImageButton journalButton;
    private ImageButton homeButton;
    private ImageButton browseButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assignment);
        mRecyclerView = (RecyclerView) findViewById(R.id.myEvent_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String users = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(users).child("Assignments");
        setupButton();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Assignment,MyAssignmentActivity.MyAssignmentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Assignment, MyAssignmentActivity.MyAssignmentViewHolder>(
                Assignment.class,
                R.layout.item_assignment,
                MyAssignmentActivity.MyAssignmentViewHolder.class,
                mDatabase
        )
        {
            @Override
            protected void populateViewHolder(MyAssignmentViewHolder viewHolder, Assignment model, int position) {
                boolean check = checkDate(model.getDueDate());
                if (check)
                {
                    viewHolder.deleteLayout();
                }
                else {
                    viewHolder.setDate(model.getDueDate());
                    viewHolder.setSubject(model.getSubject());
                    viewHolder.setName(model.getName());
                    viewHolder.setBtn();
                    final String key = getRef(position).getKey();
                    viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(MyAssignmentActivity.this, AssignmentDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("stuff", key);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                    viewHolder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDatabase.child(key).removeValue();
                            Toast.makeText(MyAssignmentActivity.this, "Assignment Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private boolean checkDate(String date)
    {
        try
        {
            if (new SimpleDateFormat("dd/MM/yyyy").parse(date).before(new Date()))
            {
                return true;
            }
        }
        catch (java.text.ParseException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public static class MyAssignmentViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        Button viewBtn;
        Button deleteBtn;

        public MyAssignmentViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date)
        {
            TextView assignmentDate = (TextView) mView.findViewById(R.id.date2);
            assignmentDate.setText(date);
        }

        public void setName(String name)
        {
            TextView assignmentTitle = (TextView) mView.findViewById(R.id.title2);
            assignmentTitle.setText(name);
        }

        public void setSubject(String subject)
        {
            TextView assignmentSubject = (TextView) mView.findViewById(R.id.subject);
            assignmentSubject.setText(subject);
        }

        public void setBtn()
        {
            viewBtn = (Button) mView.findViewById(R.id.viewBtn);
            deleteBtn = (Button) mView.findViewById(R.id.deleteBtn);
        }

        public void deleteLayout()
        {
            View view = mView.findViewById(R.id.list_layout);
            view.setVisibility(View.GONE);
        }
    }

    private void setupButton(){
        homeButton = (ImageButton) findViewById(R.id.homeButton);
        browseButton = (ImageButton) findViewById(R.id.browseButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        journalButton = (ImageButton) findViewById(R.id.journalButton);

        homeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MyAssignmentActivity.this, HomeActivity.class));
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(MyAssignmentActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyAssignmentActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MyAssignmentActivity.this, MyJournalActivity.class));
            }
        });
    }


}
