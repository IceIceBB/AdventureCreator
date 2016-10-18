package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;


public class SceneCreation extends AppCompatActivity {

    String storyId;
    String chapterId;

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

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_creation);

//        TODO: Update db with changes to Title, Goal and Summary when exiting this event (or with new button?)
        chapterTitleEditText = (EditText) findViewById(R.id.chapterTitleEditText);
        chapterGoalEditText = (EditText) findViewById(R.id.chapterGoalEditText);
        chapterSummaryEditText = (EditText) findViewById(R.id.chapterSummaryEditText);

        addSceneButton = (Button) findViewById(R.id.addSceneButton);
        sceneNodeListView = (ListView) findViewById(R.id.sceneNodeListView);

        allSceneTitles = new ArrayList<>();
        allSceneIds = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allSceneTitles);

//        TODO: Add new scene and pull for ID.
        addSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTitlesAndIds();
                Models.Scene newScene = new Models.Scene(
                        "Scene " + (allScenesArray.length+1),
                        "Journal Text",
                        "Flag Modifiers",
                        "Scene Body Text",
                        chapterId);
                    addScene(newScene);
            }
        });

        sceneNodeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(SceneCreation.this, SceneEditor.class);

                intent.putExtra("storyId", storyId);
                intent.putExtra("chapterId", chapterId);
                intent.putExtra("sceneId", allSceneIds.get(position));
//                TODO: Pass scene node type
//                intent.putExtra("sceneNodeType", ???);
                intent.putExtra("selectedSceneTitle", allScenesArray[position].title);
                intent.putExtra("selectedSceneJournalText", allScenesArray[position].journalText);
                intent.putExtra("selectedSceneModifiers", allScenesArray[position].flagModifiers);
                intent.putExtra("selectedSceneBodyText", allScenesArray[position].body);

                readChapterFormFields();
                Models.Chapter updatedChapter = new Models.Chapter(chapterTitle, chapterSummary, chapterGoal, storyId);

                updateChapter(updatedChapter);

                startActivity(intent);
            }
        });

        getChapterDetails();

        getAllTitlesAndIds();
        sceneNodeListView.setAdapter(arrayAdapter);
    }

    //        TODO: Get data and populate list view with Scene titles
    public void getChapterDetails() {
        Intent chapterIntent = getIntent();
        storyId = chapterIntent.getStringExtra("storyId");
        chapterId = chapterIntent.getStringExtra("selectedChapterId");
        chapterTitle = chapterIntent.getStringExtra("selectedChapterTitle");
        chapterGoal = chapterIntent.getStringExtra("selectedChapterGoal");
        chapterSummary = chapterIntent.getStringExtra("selectedChapterSummary");
        setChapterFormFields();
    }

    public void getAllTitlesAndIds() {
        allScenesArray = GameHelper.getInstance(this).getScenesForChapter(chapterId);

        for (int i = 0; i < allScenesArray.length; i++) {
            Models.Scene sceneAtI = allScenesArray[i];
            allSceneTitles.add(sceneAtI.title);
            allSceneIds.add(sceneAtI._id);
        }
    }

    //    TODO: Use this to update the database with user edits
    public void readChapterFormFields() {
        chapterTitle = chapterTitleEditText.getText().toString();
        chapterGoal = chapterGoalEditText.getText().toString();
        chapterSummary = chapterSummaryEditText.getText().toString();
    }

    public void setChapterFormFields() {
        chapterTitleEditText.setText(chapterTitle);
        chapterGoalEditText.setText(chapterGoal);
        chapterSummaryEditText.setText(chapterSummary);
    }

    public void addScene(Models.Scene scene) {
        GameHelper.getInstance(SceneCreation.this).addScene(scene, new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                getAllTitlesAndIds();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    public void updateChapter(Models.Chapter chapter){
        GameHelper.getInstance(SceneCreation.this).updateChapter(chapter, new Response.Listener<Models.Chapter>(){
            @Override
            public void onResponse(Models.Chapter response) {
                getAllTitlesAndIds();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();

        readChapterFormFields();
        Models.Chapter updatedChapter = new Models.Chapter(chapterTitle, chapterSummary, chapterGoal, storyId);

        updateChapter(updatedChapter);

    }

}
