package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;

import static android.R.attr.id;

public class StoryCreation extends AppCompatActivity {
    Button newStoryButton;
    ListView storyListView;
    Models.Story[] allStoriesArray;
    ArrayList<String> allStoryTitles;
    ArrayList<String> allStoryIds;
    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
            this,
            android.R.layout.simple_list_item_1,
            allStoryTitles);

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
                Models.Story newStory = new Models.Story(
                        "Story " + allStoriesArray.length + 1,
                        "Story Summary",
                        "Story Genre",
                        "Story Author",
                        "Story Tags"
                );
                Response.Listener<Models.Story> listener = new Response.Listener<>();
                GameHelper.getInstance(StoryCreation.this).addStory(newStory, listener);
            }
        });
//        TODO: Get data and populate list view with Story titles (additional info?)
        allStoriesArray = GameHelper.getInstance(this).getAllStories();
        for (int i = 0; i < allStoriesArray.length; i++) {

            Models.Story storyAtI = allStoriesArray[i];
            allStoryTitles.add(storyAtI.title);
        }
        storyListView.setAdapter(arrayAdapter);

//        TODO: Use this to pass id for selected activity through on item click
        for (int i = 0; i < allStoriesArray.length ; i++) {
            Models.Story storyAtI = allStoriesArray[i];
            allStoryIds.add(storyAtI._id);
        }

//        TODO: Transition to Chapter Creation with Story id as Intent Extra
        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(StoryCreation.this, ChapterCreation.class);

                intent.putExtra("selectedStoryId",allStoryIds.get(position));
                startActivity(intent);
            }
        });
    }




}
