package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BrowseActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_browse);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mRecyclerView = (RecyclerView) findViewById(R.id.event_recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        setupButton();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Event,BrowseActivity.EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, BrowseActivity.EventViewHolder>(
                Event.class,
                R.layout.item_event,
                EventViewHolder.class,
                mDatabase
        )
        {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position)
            {
                boolean check = checkDate(model.getDate());
                if (check)
                {
                    viewHolder.deleteLayout();
                }
                else {
                    viewHolder.setDate(model.getDate());
                    viewHolder.setLocation(model.getLocation());
                    viewHolder.setName(model.getName());
                    viewHolder.setBtn();
                    final String key = getRef(position).getKey();
                    viewHolder.viewBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(BrowseActivity.this, EventDetailActivity.class);
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
    }

    public static class EventViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        Button viewBtn;

        public EventViewHolder(View itemView)
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

        public void setLocation(String location)
        {
            TextView eventLocation = (TextView) mView.findViewById(R.id.location2);
            eventLocation.setText(location);
        }

        public void setBtn()
        {
            viewBtn = (Button) mView.findViewById(R.id.viewBtn);
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
                startActivity(new Intent(BrowseActivity.this, HomeActivity.class));
            }
        });

        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(BrowseActivity.this, BrowseActivity.class));
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(BrowseActivity.this, CreateAssignmentActivity.class));
            }
        });
        journalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(BrowseActivity.this, MyJournalActivity.class));
            }
        });
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
}
