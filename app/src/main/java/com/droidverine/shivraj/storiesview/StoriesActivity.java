package com.droidverine.shivraj.storiesview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;

public class StoriesActivity extends AppCompatActivity implements StoriesView.StoriesListener {

    private static final int PROGRESS_COUNT = 6;
    boolean isSpeakButtonLongPressed;
    private StoriesView storiesProgressView;
    private ImageView image;
    ArrayList<String> res=new ArrayList<>();

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stories);
        res.add("http://myappapi.esy.es/upload/Name.jpg");
        res.add("http://myappapi.esy.es/upload/Murder.jpg");
        res.add("http://myappapi.esy.es/upload/hrushikesh%20kudtatdakar.jpg");
        storiesProgressView = (StoriesView) findViewById(R.id.stories);
        storiesProgressView.setStoriesCount(res.size());
        storiesProgressView.setStoryDuration(1500L);
        storiesProgressView.setStoriesListener(this);
        image = (ImageView) findViewById(R.id.image);
        storiesProgressView.startStories();
        storiesProgressView.pause();
        Glide.with(getApplicationContext()).load(res.get(counter)).listener(new RequestListener<String, GlideDrawable>() {

            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                storiesProgressView.resume();
                return false;
            }
        }).into(image);
       // image.setImageResource(resources[counter]);
        View reverse = findViewById(R.id.reverse);
        View hold = findViewById(R.id.hold);
        hold.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                isSpeakButtonLongPressed = true;
                storiesProgressView.pause();
                return true;
            }
        });
        hold.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.onTouchEvent(motionEvent);
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    storiesProgressView.resume();
                    if (isSpeakButtonLongPressed) {
                        isSpeakButtonLongPressed = false;
                    }
                }
                return false;
            }
        });
        reverse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.reverse();
            }
        });
        // bind skip view
        View skip = findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storiesProgressView.skip();
            }
        });
    }
    @Override
    public void onNext() {
        storiesProgressView.pause();
       // Glide.with(getApplicationContext()).load(res.get(++counter)).into(image);
        Glide.with(getApplicationContext()).load(res.get(++counter)).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                storiesProgressView.resume();
                return false;

            }
        }).into(image);
                // image.setImageResource(resources[++counter]);
    }
    @Override
    public void onPrev() {
        if (counter - 1 < 0) return;
        Glide.with(getApplicationContext()).load(res.get(--counter)).into(image);
       // image.setImageResource(resources[--counter]);
    }
    @Override
    public void onComplete() {
        startActivity(new Intent(this,MainActivity.class));
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onPause() {
      //  image.setImageResource(resources[counter]);
        Glide.with(getApplicationContext()).load(res.get(counter)).into(image);

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        storiesProgressView.destroy();
        super.onDestroy();
    }
}

