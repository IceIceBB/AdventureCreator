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

    public GameLibraryPagerAdapter fantasyAdapter;
    public ViewPager fantasyPager;
    public GameLibraryPagerAdapter sciFiAdapter;
    public ViewPager sciFiPager;
    public GameLibraryPagerAdapter horrorAdapter;
    public ViewPager horrorPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_library);

        fantasyPager = (ViewPager) findViewById(R.id.fantasyCarousel);

        fantasyAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager());
        fantasyPager.setAdapter(fantasyAdapter);
        fantasyPager.setPageTransformer(false, fantasyAdapter);

        // Set current item to the middle page so we can fling to both
        // directions left and right
        fantasyPager.setCurrentItem(FIRST_PAGE);

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        fantasyPager.setOffscreenPageLimit(5);

        // Set margin for pages as a negative number, so a part of next and
        // previous pages will be showed
        fantasyPager.setPageMargin(-600);

        sciFiPager = (ViewPager) findViewById(R.id.sciFiCarousel);
        sciFiAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager());
        sciFiPager.setAdapter(sciFiAdapter);
        sciFiPager.setPageTransformer(false, sciFiAdapter);
        sciFiPager.setCurrentItem(FIRST_PAGE);
        sciFiPager.setOffscreenPageLimit(5);
        sciFiPager.setPageMargin(-600);

        horrorPager = (ViewPager) findViewById(R.id.horrorCarousel);
        horrorAdapter = new GameLibraryPagerAdapter(this, this.getSupportFragmentManager());
        horrorPager.setAdapter(horrorAdapter);
        horrorPager.setPageTransformer(false, horrorAdapter);
        horrorPager.setCurrentItem(FIRST_PAGE);
        horrorPager.setOffscreenPageLimit(5);
        horrorPager.setPageMargin(-600);
    }
}
