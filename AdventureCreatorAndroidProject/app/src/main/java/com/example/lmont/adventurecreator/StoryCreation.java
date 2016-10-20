package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.Response;

import java.util.ArrayList;

public class StoryCreation extends AppCompatActivity {
    Button newStoryButton;
    ListView storyListView;

    Models.Story[] allStoriesArray;
    ArrayList<String> allStoryTitles;
    ArrayList<String> allStoryIds;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide(Gravity.LEFT));

        setContentView(R.layout.activity_story_creation);

        newStoryButton = (Button) findViewById(R.id.newStoryButton);
        storyListView = (ListView) findViewById(R.id.storyListView);

        allStoryTitles = new ArrayList<>();
        allStoryIds = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allStoryTitles);

//        TODOne: Add new story and pull for ID.
        newStoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAllTitlesAndIds();
                Models.Story newStory = new Models.Story(
                        "Story " + (allStoriesArray.length+1),
                        "Story Author",
                        "Story Summary",
                        "Story Genre",
                        "Story Type",
                        "Story Tags");

                addStory(newStory);
            }
        });

        getAllTitlesAndIds();
        storyListView.setAdapter(arrayAdapter);

//        TODOne: Transition to Chapter Creation with Story id as Intent Extra
        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(StoryCreation.this, ChapterCreation.class);

                intent.putExtra("selectedStoryId", allStoryIds.get(position));

                startActivity(intent);
            }
        });
    }

//    TODOne: Get data and populate list view with Story titles (additional info?)
    public void getAllTitlesAndIds() {
        allStoriesArray = GameHelper.getInstance(this).getAllStories();

        allStoryIds.removeAll(allStoryIds);
        allStoryTitles.removeAll(allStoryTitles);

        for (int i = 0; i < allStoriesArray.length; i++) {

            Models.Story storyAtI = allStoriesArray[i];
            allStoryTitles.add(storyAtI.title);
//            TODOne: Use this to pass id for selected activity through on item click
            allStoryIds.add(storyAtI._id);
        }
    }

    public void addStory(Models.Story story) {
        GameHelper.getInstance(StoryCreation.this).addStory(story, new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {
                getAllTitlesAndIds();
                arrayAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllTitlesAndIds();
        arrayAdapter.notifyDataSetChanged();
    }
}
