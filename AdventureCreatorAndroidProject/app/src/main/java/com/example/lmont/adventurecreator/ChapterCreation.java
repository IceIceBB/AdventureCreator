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


    Button addChapterButton;
    ListView chaptersListView;


    Models.Chapter[] allChaptersArray;
    ArrayList<String> allChapterTitles;
    ArrayList<String> allChapterIds;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter_creation);

//        TODO: Update db with changes to Title, Author, Summary, Genre and Tags when exiting this activity (or with new button?)
        storyTitleEditText = (EditText) findViewById(R.id.storyTitleEditText);
        storyAuthorEditText = (EditText) findViewById(R.id.storyAuthorEditText);
        storySummaryEditText = (EditText) findViewById(R.id.storySummaryEditText);
        storyGenreEditText = (EditText) findViewById(R.id.storyGenreEditText);
        storyTagsEditText = (EditText) findViewById(R.id.storyTagsEditText);

        addChapterButton = (Button) findViewById(R.id.addChapterButton);
        chaptersListView = (ListView) findViewById(R.id.chaptersListView);

        allChapterTitles = new ArrayList<>();
        allChapterIds = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allChapterTitles);


//        TODO: Add new chapter and pull for id
        addChapterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTitlesAndIds();
                Models.Chapter newChapter = new Models.Chapter(
                        "Chapter " + (allChaptersArray.length+1),
                        "Chapter Summary",
                        "Chapter Goal",
                        storyId);
                    addChapter(newChapter);
            }
        });



//        TODO: Transition to Chapter Creation with Story id and Chapter id as Intent Extras
        chaptersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(ChapterCreation.this, SceneCreation.class);

                intent.putExtra("storyId", storyId);
                intent.putExtra("selectedChapterId", allChapterIds.get(position));
                intent.putExtra("selectedChapterTitle", allChaptersArray[position].title);
                intent.putExtra("selectedChapterGoal", allChaptersArray[position].type);
                intent.putExtra("selectedChapterSummary", allChaptersArray[position].summary);
                startActivity(intent);
            }
        });

        getStoryDetails();

        getAllTitlesAndIds();
        chaptersListView.setAdapter(arrayAdapter);
    }


//    TODO: Get data and populate list view with Chapter titles
    public void getStoryDetails() {
        Intent storyIntent = getIntent();
        storyId = storyIntent.getStringExtra("selectedStoryId");
        storyTitle = storyIntent.getStringExtra("selectedStoryTitle");
        storySummary = storyIntent.getStringExtra("selectedStorySummary");
        storyGenre = storyIntent.getStringExtra("selectedStoryGenre");
        storyTags = storyIntent.getStringExtra("selectedStoryTags");
        setStoryFormFields();
    }

    public void getAllTitlesAndIds() {
        allChaptersArray = GameHelper.getInstance(this).getChaptersForStory(storyId);

        allChapterIds.removeAll(allChapterIds);
        allChapterTitles.removeAll(allChapterTitles);

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

    public void addChapter(Models.Chapter chapter) {
        GameHelper.getInstance(ChapterCreation.this).addChapter(chapter, new Response.Listener<Models.Chapter>() {
            @Override
            public void onResponse(Models.Chapter response) {
                getAllTitlesAndIds();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }
}
