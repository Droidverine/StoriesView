package com.droidverine.shivraj.storiesview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
 Button btnstories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnstories=(Button)findViewById(R.id.btnStories);
        btnstories.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this,StoriesActivity.class));
    }
}
