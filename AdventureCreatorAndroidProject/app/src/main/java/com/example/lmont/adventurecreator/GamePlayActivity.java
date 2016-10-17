package com.example.lmont.adventurecreator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

public class GamePlayActivity extends AppCompatActivity {

    ViewSwitcher bookmark;
    ImageView bookmarkHollow;
    ImageView bookmarkSolid;
    ImageView journal;
    TextView sceneText;
    Button hintButton;
    boolean hintReady;
    boolean hintShowing;
    ListView optionsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        setup();
    }

    private void setup() {
        hintReady = false;
        hintShowing = false;

        String[] options = {"option one", "option two", "option three", "option four"};

        bookmark = (ViewSwitcher) findViewById(R.id.bookmark);
        bookmarkHollow = (ImageView) findViewById(R.id.bookmarkHollow);
        bookmarkSolid = (ImageView) findViewById(R.id.bookmarkSolid);
        journal = (ImageView) findViewById(R.id.journalIcon);
        sceneText = (TextView) findViewById(R.id.sceneText);
        hintButton = (Button) findViewById(R.id.hintButton);
        optionsList = (ListView) findViewById(R.id.options);
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        optionsList.setAdapter(optionsAdapter);

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

        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!hintReady&&!hintShowing){
                    int colorFrom = getResources().getColor(R.color.colorAccent);
                    int colorTo = getResources().getColor(R.color.colorPrimary);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            hintButton.setBackgroundColor((int) animator.getAnimatedValue());
                        }
                    });

                    colorAnimation.start();
                    hintReady = true;
                    optionsList.setVisibility(View.GONE);
                }
                else if (hintReady&&!hintShowing){
                    optionsList.setVisibility(View.VISIBLE);
                    hintReady = false;
//                    hintShowing = true;
                }
                else if (!hintReady&&hintShowing){
                    int colorFrom = getResources().getColor(R.color.colorPrimary);
                    int colorTo = getResources().getColor(R.color.colorAccent);
                    ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                    colorAnimation.setDuration(250); // milliseconds
                    colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                        @Override
                        public void onAnimationUpdate(ValueAnimator animator) {
                            hintButton.setBackgroundColor((int) animator.getAnimatedValue());
                        }
                    });
                    colorAnimation.start();

                    optionsList.setVisibility(View.GONE);
                    hintShowing = false;
                }
            }
        });
    }

    private void baconText(TextView sceneText) {
        sceneText.setText("Bacon ipsum dolor amet prosciutto chicken hamburger alcatra biltong capicola meatball doner fatback filet mignon shank kevin leberkas." +
                "\n\nTenderloin brisket corned beef pork belly filet mignon cow." +
                "\n\nPork short loin ball tip pig hamburger pork loin porchetta biltong andouille alcatra sirloin brisket corned beef rump chuck." +
                "\n\nCow doner salami porchetta pancetta sausage pork loin rump leberkas." +
                "\n\nChuck shank corned beef tail doner shoulder strip steak sirloin sausage pork chop filet mignon." +
                "\n\nShort ribs tail tongue, tri-tip pork belly biltong bacon short loin burgdoggen."
        );
    }
}
