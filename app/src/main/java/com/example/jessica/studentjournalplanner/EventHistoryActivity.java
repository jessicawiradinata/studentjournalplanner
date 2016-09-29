package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by timothyalfares on 9/28/2016.
 */
public class EventHistoryActivity extends AppCompatActivity
{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_history);
        mRecyclerView = (RecyclerView) findViewById(R.id.myEvent_recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String users = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(users).child("Events");
        Date today = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentData = sdf.format(today);
        Log.v("test", currentData);
        boolean checkThisDate = checkDate("12/10/2016");
        Log.v("test date", "" + checkThisDate);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        FirebaseRecyclerAdapter<Event,MyEventViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Event, MyEventViewHolder>(
                Event.class,
                R.layout.item_event,
                MyEventViewHolder.class,
                mDatabase
        )
        {
            @Override
            protected void populateViewHolder(MyEventViewHolder viewHolder, Event model, int position) {
                boolean check = checkDate(model.getDate());
                if (check) {
                    viewHolder.setDate(model.getDate());
                    viewHolder.setLocation(model.getLocation());
                    viewHolder.setName(model.getName());
                    viewHolder.setBtn();
                }
                else {
                    viewHolder.deleteLayout();
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

    public static class MyEventViewHolder extends RecyclerView.ViewHolder
    {
        View mView;
        Button viewBtn;

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

}
