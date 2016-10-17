package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class GameLibraryActivity extends FragmentActivity {

    public final static int PAGES = 7;
    // You can choose a bigger number for LOOPS, but you know, nobody will fling
    // more than 1000 times just in order to testAddStoryAndReadStory your "infinite" ViewPager :D
    public final static int LOOPS = 1000;
    public final static int FIRST_PAGE = PAGES * LOOPS / 2;

    public GameLibraryPagerAdapter adapter;
    public GameLibraryPagerAdapter adapter2;
    public ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_library);

        pager = (ViewPager) findViewById(R.id.myviewpager);

        adapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager());
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
