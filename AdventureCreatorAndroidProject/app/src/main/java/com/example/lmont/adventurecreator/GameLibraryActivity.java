package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class GameLibraryActivity extends FragmentActivity {

    public static int STORIES = 5;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to testAddStoryAndReadStory your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public final static int FIRST_PAGE = STORIES * LOOPS / 2;

    public GameLibraryPagerAdapter fantasyAdapter;
    public ViewPager fantasyPager;
    public GameLibraryPagerAdapter sciFiAdapter;
    public ViewPager sciFiPager;
    public GameLibraryPagerAdapter horrorAdapter;
    public ViewPager horrorPager;
    public GameLibraryPagerAdapter otherAdapter;
    public ViewPager otherPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_library);

        fantasyPager = (ViewPager) findViewById(R.id.fantasyCarousel);
        fantasyAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager(), findStoriesOfType("fantasy"));
        setupPager(fantasyPager, fantasyAdapter);

        sciFiPager = (ViewPager) findViewById(R.id.sciFiCarousel);
        sciFiAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager(), findStoriesOfType("scifi"));
        setupPager(sciFiPager, sciFiAdapter);

        horrorPager = (ViewPager) findViewById(R.id.horrorCarousel);
        horrorAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager(), findStoriesOfType("horror"));
        setupPager(horrorPager, horrorAdapter);

        otherPager = (ViewPager) findViewById(R.id.otherCarousel);
        otherAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager(), findStoriesOfType("other"));
        setupPager(otherPager, otherAdapter);
    }

    private Models.Story[] findStoriesOfType(String genre){
        Models.Story[] storyArray = GameHelper.getInstance(this).getAllStories();
        ArrayList<Models.Story> stories = new ArrayList<>();

        ArrayList<String> preset = new ArrayList<>();
        preset.add("fantasy");
        preset.add("scifi");
        preset.add("horror");

        for (int i = 0; i < storyArray.length; i++) {
            if (storyArray[i].genre.equals(genre)){
                stories.add(storyArray[i]);
            } else if (genre.equals("other") && !preset.contains(storyArray[i].genre)) {
                stories.add(storyArray[i]);
            }
        }

        Models.Story[] newStoryArray = new Models.Story[stories.size()];
        for(int x=0; x<stories.size(); x++) {
            newStoryArray[x] = stories.get(x);
        }

        return newStoryArray;
    }

    private void setupPager (ViewPager pager, GameLibraryPagerAdapter adapter){
        pager.setAdapter(adapter);
        pager.setPageTransformer(false, adapter);
        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(FIRST_PAGE);
        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        pager.setOffscreenPageLimit(5);
        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        pager.setPageMargin(-600);
    }
}
