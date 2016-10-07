package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager2;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase2;
    private ImageButton journalButton;
    private ImageButton homeButton;
    private ImageButton browseButton;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mRecyclerView = (RecyclerView) findViewById(R.id.myEvent_recyclerView2);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String users = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(users).child("Assignments");
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Users").child(users).child("Events");
        mRecyclerView2 = (RecyclerView) findViewById(R.id.myEvent_recyclerView);
        mRecyclerView2.setHasFixedSize(true);
        mLayoutManager2 = new LinearLayoutManager(this);
        mRecyclerView2.setLayoutManager(mLayoutManager2);
        setupButton();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Assignment,MyAssignmentViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Assignment, MyAssignmentViewHolder>(
                Assignment.class,
                R.layout.item_event_home,
                MyAssignmentViewHolder.class,
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
                    viewHolder.setName(model.getName());
                    viewHolder.setBtn();
                    final String key = getRef(position).getKey();
                    viewHolder.viewList.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(HomeActivity.this, AssignmentDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("stuff", key);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                }
            }
        };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

        FirebaseRecyclerAdapter<Event,MyEventViewHolder> firebaseRecyclerAdapter2 = new FirebaseRecyclerAdapter<Event, MyEventViewHolder>(
                Event.class,
                R.layout.item_event_home,
                MyEventViewHolder.class,
                mDatabase2
        )
        {
            @Override
            protected void populateViewHolder(MyEventViewHolder viewHolder, Event model, int position) {
                boolean check = checkDate(model.getDate());
                if (check){
                    viewHolder.deleteLayout();
                }
                else {
                    viewHolder.setDate(model.getDate());
                    viewHolder.setName(model.getName());
                    viewHolder.setBtn();
                    final String key = getRef(position).getKey();
                    viewHolder.viewList.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(HomeActivity.this, EventDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("stuff", key);
                            i.putExtras(bundle);
                            startActivity(i);
                        }
                    });
                }
            }
        };
        mRecyclerView2.setAdapter(firebaseRecyclerAdapter2);
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
        View viewList;

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

        public void setBtn()
        {
            viewList = mView.findViewById(R.id.viewList);
        }

        public void deleteLayout()
        {
            View view = mView.findViewById(R.id.list_layout);
            view.setVisibility(View.GONE);
        }
    }

    public static class MyEventViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        View viewList;

        public MyEventViewHolder(View itemView)
        {
            super(itemView);
            mView = itemView;
        }

        public void setDate(String date)
        {
            TextView eventDate = (TextView) mView.findViewById(R.id.date2);
            eventDate.setText(date);
        }

        public void setName(String name)
        {
            TextView eventTitle = (TextView) mView.findViewById(R.id.title2);
            eventTitle.setText(name);
        }

        public void setBtn()
        {
            viewList = mView.findViewById(R.id.viewList);;
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
                startActivity(new Intent(HomeActivity.this, HomeActivity.class));
            }
        });

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
                startActivity(new Intent(HomeActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(HomeActivity.this, MyJournalActivity.class));
            }
        });
    }

}