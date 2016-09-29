package com.example.jessica.studentjournalplanner;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.firebase.client.Firebase;

public class FireApp extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}