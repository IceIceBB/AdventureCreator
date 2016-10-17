package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;


public class SceneCreation extends AppCompatActivity {

    EditText chapterTitleEditText;
    EditText chapterGoalEditText;
    EditText chapterSummaryEditText;

    String chapterTitle;
    String chapterGoal;
    String chapterSummary;

    Button addSceneButton;
    ListView sceneNodeListView;

    Models.Scene[] allScenesArray;
    ArrayList<String> allSceneTitles;
    ArrayList<String> allSceneIds;

    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allSceneTitles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creation);

        getChapterDetails();
        setChapterFormFields();

//        TODO: Update db with changes to Title, Goal and Summary when exiting this event (or with new button?)
        chapterTitleEditText = (EditText) findViewById(R.id.chapterTitleEditText);
        chapterGoalEditText = (EditText) findViewById(R.id.chapterGoalEditText);
        chapterSummaryEditText = (EditText) findViewById(R.id.chapterSummaryEditText);

        addSceneButton = (Button) findViewById(R.id.addSceneButton);
        sceneNodeListView = (ListView) findViewById(R.id.sceneNodeListView);
//        TODO: Add new scene and pull for ID.
        addSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTitlesAndIds():
                Models.Scene newScene = new Models.Scene(
                        "Scene " + allScenesArray.length + 1,
                        "Journal Text",
                        "Flag Modifiers",
                        "Scene Body Text",
                        chapterId);

//                TODO: Fix these two so they aren't breaking the code (Something about Listener)
//                Response.Listener<Models.Chapter> listener = new Response.Listener<>();
//                GameHelper.getInstance(ChapterCreation.this).addChapter(newChapter, listener);
            }
        });
    }

//        TODO: Get data and populate list view with Scene titles
    public void getChapterDetails(){
        Intent chapterIntent = getIntent();
        storyId = ;
        chapterId = ;
    }

    public void getAllTitlesAndIds(){
        allScenesArray = GameHelper.getInstance(this).getScenesForChapter(chapterId);

        for (int i = 0; i <allScenesArray.length ; i++) {
            Models.Scene sceneAtI = allScenesArray[i];
            allSceneTitles.add(sceneAtI.title);
            allSceneIds.add(sceneAtI._id);
        }
    }



}
