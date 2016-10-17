package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ChapterCreation extends AppCompatActivity {

    EditText storyTitleEditText;
    EditText storyAuthorEditText;
    EditText storySummaryEditText;
    EditText storyGenreEditText;
    EditText storyTagsEditText;
    Button addSceneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_creation);

//        TODO: Update db with changes to Title, Author, Summary, Genre and Tags when exiting this event (or with new button?)
        storyTitleEditText = (EditText) findViewById(R.id.storyTitleEditText);
        storyAuthorEditText = (EditText) findViewById(R.id.storyAuthorEditText);
        storySummaryEditText = (EditText) findViewById(R.id.storySummaryEditText);
        storyGenreEditText = (EditText) findViewById(R.id.storyGenreEditText);
        storyTagsEditText = (EditText) findViewById(R.id.storyTitleEditText);

        addSceneButton = (Button) findViewById(R.id.addSceneButton);
//        TODO: Add new scene and pull for id
        addSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
