package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class StoryCreation extends AppCompatActivity {
    Button newStoryButton;
    ListView storyListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_story_creation);

        newStoryButton = (Button) findViewById(R.id.newStoryButton);
        storyListView = (ListView) findViewById(R.id.storyListView);

//        TODO: Add new story and pull for ID.
        newStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        TODO: Get data and populate list view with Story titles (additional info?)
    }
}
