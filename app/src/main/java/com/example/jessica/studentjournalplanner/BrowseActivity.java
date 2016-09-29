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

public class BrowseActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        setupHomeButton();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Events");
        mRecyclerView = (RecyclerView) findViewById(R.id.event_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        /*
        });*/
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Event,EventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, EventViewHolder>(
                Event.class,
                R.layout.item_event,
                EventViewHolder.class,
                mDatabase
        )
        {
            @Override
            protected void populateViewHolder(EventViewHolder viewHolder, Event model, int position) {
                viewHolder.setDate(model.getDate());
                viewHolder.setLocation(model.getLocation());
                viewHolder.setName(model.getName());
                viewHolder.setBtn();
                final String key = getRef(position).getKey();
                viewHolder.viewBtn.setOnClickListener(new View.OnClickListener()
                {
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

    }

    private void setupHomeButton(){
        ImageButton browseButton = (ImageButton) findViewById(R.id.homeButton);
        browseButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(BrowseActivity.this, HomeActivity.class));
            }
        });
    }
}
