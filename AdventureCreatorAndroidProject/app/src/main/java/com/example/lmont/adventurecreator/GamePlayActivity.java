package com.example.lmont.adventurecreator;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class GamePlayActivity extends AppCompatActivity {

    ViewSwitcher bookmark;
    ImageView bookmarkHollow;
    ImageView bookmarkSolid;
    ImageView journal;
    TextView sceneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        bookmark = (ViewSwitcher) findViewById(R.id.bookmark);
        bookmarkHollow = (ImageView) findViewById(R.id.bookmarkHollow);
        bookmarkSolid = (ImageView) findViewById(R.id.bookmarkSolid);
        journal = (ImageView) findViewById(R.id.journalIcon);
        sceneText = (TextView) findViewById(R.id.sceneText);

        baconText(sceneText);

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

        journal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                JournalDialogFragment journalFragment = new JournalDialogFragment();
                journalFragment.setStyle(JournalDialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                journalFragment.show(fm, "Journal Fragment");
            }
        });
    }

    private void baconText(TextView sceneText) {
        sceneText.setText("Bacon ipsum dolor amet prosciutto chicken hamburger alcatra biltong capicola meatball doner fatback filet mignon shank kevin leberkas.\n\nTenderloin brisket corned beef pork belly filet mignon cow.\n\nPork short loin ball tip pig hamburger pork loin porchetta biltong andouille alcatra sirloin brisket corned beef rump chuck.\n\nCow doner salami porchetta pancetta sausage pork loin rump leberkas.\n\nChuck shank corned beef tail doner shoulder strip steak sirloin sausage pork chop filet mignon.\n\nShort ribs tail tongue, tri-tip pork belly biltong bacon short loin burgdoggen.");
    }
}
