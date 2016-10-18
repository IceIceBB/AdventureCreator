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
        setContentView(R.layout.activity_story_creation);

        newStoryButton = (Button) findViewById(R.id.newStoryButton);
        storyListView = (ListView) findViewById(R.id.storyListView);

        allStoryTitles = new ArrayList<>();
        allStoryIds = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, allStoryTitles);


//        TODO: Add new story and pull for ID.
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
//                getAllTitlesAndIds();
            }
        });


// LEO'S EXAMPLE
//    public void testCreateStory() {
//        gameHelper.addStory(new Models.Story(
//                "The Bottle & The Key",
//                "Paul",
//                "Can you escape the room?",
//                "horror",
//                "puzzle",
//                "short, key, mystery"),
//                new Response.Listener<Models.Story>() {
//            @Override
//            public void onResponse(Models.Story response) {
//                testStory[0] = response._id;
//                testCreateChapter(response._id);
//            }
//        });

        getAllTitlesAndIds();
        storyListView.setAdapter(arrayAdapter);


//        TODOne: Transition to Chapter Creation with Story id as Intent Extra
        storyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent intent = new Intent(StoryCreation.this, ChapterCreation.class);

                intent.putExtra("selectedStoryId", allStoryIds.get(position));
                intent.putExtra("selectedStoryTitle", allStoriesArray[position].title);
//                TODO: get author name from user somehow?
//                intent.putExtra("selectedStoryAuthor", allStoriesArray[position].author);
                intent.putExtra("selectedStorySummary", allStoriesArray[position].description);
                intent.putExtra("selectedStoryGene", allStoriesArray[position].genre);
                intent.putExtra("selectedStoryTags", allStoriesArray[position].tags);
                startActivity(intent);
            }
        });
    }


    //    TODO: Get data and populate list view with Story titles (additional info?)
    public void getAllTitlesAndIds() {
        allStoriesArray = GameHelper.getInstance(this).getAllStories();

        allStoryIds.removeAll(allStoryIds);
        allStoryTitles.removeAll(allStoryTitles);

        for (int i = 0; i < allStoriesArray.length; i++) {

            Models.Story storyAtI = allStoriesArray[i];
            allStoryTitles.add(storyAtI.title);
//            TODO: Use this to pass id for selected activity through on item click
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


}
