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

import java.util.ArrayList;

public class ChapterCreation extends AppCompatActivity {

    String storyId;

    EditText storyTitleEditText;
    EditText storyAuthorEditText;
    EditText storySummaryEditText;
    EditText storyGenreEditText;
    EditText storyTagsEditText;

    String storyTitle;
    String storyAuthor;
    String storySummary;
    String storyGenre;
    String storyTags;


    Button addSceneButton;
    ListView chaptersListView;


    Models.Chapter[] allChaptersArray;
    ArrayList<String> allChapterTitles;
    ArrayList<String> allChapterIds;

    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allChapterTitles);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_creation);

        getStoryDetails();
        setStoryFormFields();


//        TODO: Update db with changes to Title, Author, Summary, Genre and Tags when exiting this activity (or with new button?)
        storyTitleEditText = (EditText) findViewById(R.id.storyTitleEditText);
        storyAuthorEditText = (EditText) findViewById(R.id.storyAuthorEditText);
        storySummaryEditText = (EditText) findViewById(R.id.storySummaryEditText);
        storyGenreEditText = (EditText) findViewById(R.id.storyGenreEditText);
        storyTagsEditText = (EditText) findViewById(R.id.storyTitleEditText);

        chaptersListView = (ListView) findViewById(R.id.chaptersListView);

        addSceneButton = (Button) findViewById(R.id.addSceneButton);
//        TODO: Add new scene and pull for id
        addSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTitlesAndIds();
                Models.Chapter newChapter = new Models.Chapter(
                        "Chapter " + allChaptersArray.length + 1,
                        "Chapter Summary",
                        "Chapter Goal",
                        storyId);

//                TODO: Fix these two so they aren't breaking the code (Something about Listener)
//                Response.Listener<Models.Chapter> listener = new Response.Listener<>();
//                GameHelper.getInstance(ChapterCreation.this).addChapter(newChapter, listener);
                getAllTitlesAndIds();
            }
        });

        getAllTitlesAndIds();
        chaptersListView.setAdapter(arrayAdapter);

//        TODO: Transition to Chapter Creation with Story id and Chapter id as Intent Extras
        chaptersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(ChapterCreation.this, ChapterCreation.class);

                intent.putExtra("storyId", storyId);
                intent.putExtra("selectedChapterId", allChapterIds.get(position));
                startActivity(intent);
            }
        });

    }


//    TODO: Get data and populate list view with Chapter titles
    public void getStoryDetails() {
        Intent storyIntent = getIntent();
        storyId = storyIntent.getStringExtra("selectedStoryId");
        storyTitle = storyIntent.getStringExtra("selectedStoryTitle");
        storySummary = storyIntent.getStringExtra("selectedStorySummary");
        storyGenre = storyIntent.getStringExtra("selectedStoryGenre");
        storyTags = storyIntent.getStringExtra("selectedStoryTags");
    }

    public void getAllTitlesAndIds() {
        allChaptersArray = GameHelper.getInstance(this).getChaptersForStory(storyId);

        for (int i = 0; i < allChaptersArray.length; i++) {
            Models.Chapter chapterAtI = allChaptersArray[i];
            allChapterTitles.add(chapterAtI.title);
            allChapterIds.add(chapterAtI._id);
        }
    }


//    TODO: Use this to update the database with user edits
    public void readStoryFormFields(){
        storyTitle = storyTitleEditText.getText().toString();
        storyAuthor = storyAuthorEditText.getText().toString();
        storySummary = storySummaryEditText.getText().toString();
        storyGenre = storyGenreEditText.getText().toString();
        storyTags = storyTagsEditText.getText().toString();
    }

    public void setStoryFormFields(){
        storyTitleEditText.setText(storyTitle);
        storySummaryEditText.setText(storySummary);
        storyGenreEditText.setText(storyGenre);
        storyTagsEditText.setText(storyTags);
    }
}
