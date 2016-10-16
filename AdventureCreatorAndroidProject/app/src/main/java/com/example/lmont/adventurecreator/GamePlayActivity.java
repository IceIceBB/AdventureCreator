package com.example.lmont.adventurecreator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

public class GamePlayActivity extends AppCompatActivity {

    ViewSwitcher bookmark;
    ImageView bookmarkHollow;
    ImageView bookmarkSolid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        bookmark = (ViewSwitcher) findViewById(R.id.bookmark);
        bookmarkHollow = (ImageView) findViewById(R.id.bookmarkHollow);
        bookmarkSolid = (ImageView) findViewById(R.id.bookmarkSolid);

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookmark.getCurrentView() != bookmarkHollow){
                    bookmark.showPrevious();
                }else if(bookmark.getCurrentView() != bookmarkSolid){
                    bookmark.showNext();
                }
            }
        });
    }
}
