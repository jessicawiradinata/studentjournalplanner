package com.example.jessica.studentjournalplanner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class BrowseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        setupHomeButton();
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
