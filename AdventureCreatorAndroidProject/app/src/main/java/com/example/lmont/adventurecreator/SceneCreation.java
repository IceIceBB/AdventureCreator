package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class SceneCreation extends AppCompatActivity {

    EditText chapterTitleEditText;
    EditText chapterGoalEditText;
    EditText chapterSummaryEditText;
    Button addSceneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creation);

//        TODO: Update db with changes to Title, Goal and Summary when exiting this event (or with new button?)
        chapterTitleEditText = (EditText) findViewById(R.id.chapterTitleEditText);
        chapterGoalEditText = (EditText) findViewById(R.id.chapterGoalEditText);
        chapterSummaryEditText = (EditText) findViewById(R.id.chapterSummaryEditText);

        addSceneButton = (Button) findViewById(R.id.addSceneButton);
//        TODO: Add new scene and pull for ID.
        addSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

//        TODO: Get data and populate list view with Scene titles

}
