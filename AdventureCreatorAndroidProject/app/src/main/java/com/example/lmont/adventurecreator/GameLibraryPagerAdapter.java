package com.example.lmont.adventurecreator;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by klaus_000 on 10/15/2016.
 */

public class GameLibraryPagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {

    public final static float BIG_SCALE = 1.3f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private GameLibraryLinearLayout cur = null;
    private GameLibraryLinearLayout next = null;
    private GameLibraryActivity context;
    private FragmentManager fm;
    private float scale;
    Models.Story[] stories;

    public GameLibraryPagerAdapter(GameLibraryActivity context, FragmentManager fm, Models.Story[] stories) {
        super(fm);
        this.fm = fm;
        this.context = context;
        this.stories = stories;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        if (position == GameLibraryActivity.FIRST_PAGE)
            scale = BIG_SCALE;
        else
            scale = SMALL_SCALE;

        position = position % GameLibraryActivity.STORIES;
        GameLibraryFragment gameLibraryFragment = (GameLibraryFragment) GameLibraryFragment.newInstance(context, position, scale);
        gameLibraryFragment.story = stories[position];
        return gameLibraryFragment;
    }

    @Override
    public int getCount() {
        return stories.length;
        //return GameLibraryActivity.STORIES * GameLibraryActivity.LOOPS;
    }

    @Override
    public void transformPage(View page, float position) {
        GameLibraryLinearLayout myLinearLayout = (GameLibraryLinearLayout) page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);
    }
}
