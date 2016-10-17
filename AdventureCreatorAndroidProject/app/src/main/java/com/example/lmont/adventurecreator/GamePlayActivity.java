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

    enum SceneType {end, action, auto, conditional};

    public static final String SCENEID_KEY = "SCENEID_KEY";
    ViewSwitcher bookmark;
    ImageView bookmarkHollow;
    ImageView bookmarkSolid;
    ImageView journal;
    TextView sceneText;
    Button hintButton;
    Button nextSceneButton;
    boolean hintReady;
    boolean hintShowing;
    ListView optionsList;

    Models.Transition[] transitions;
    Player player;
    SceneType sceneType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        setup();
    }

    private void setup() {
        // Variable Setup
        hintReady = false;
        hintShowing = false;
        bookmark = (ViewSwitcher) findViewById(R.id.bookmark);
        bookmarkHollow = (ImageView) findViewById(R.id.bookmarkHollow);
        bookmarkSolid = (ImageView) findViewById(R.id.bookmarkSolid);
        journal = (ImageView) findViewById(R.id.journalIcon);
        sceneText = (TextView) findViewById(R.id.sceneText);
        hintButton = (Button) findViewById(R.id.hintButton);
        optionsList = (ListView) findViewById(R.id.options);
        nextSceneButton = (Button) findViewById(R.id.nextScene);
        String[] options = null;

        // Instantiate Player
        player = Player.getInstance();

        // Get Scene Type
        transitions = player.getCurrentScene().transitions;

        if (transitions[0].type == null) {
            sceneType = SceneType.end;
        } else {
            switch (transitions[0].type) {
                case "check_fail":
                    sceneType = SceneType.conditional;
                    break;
                case "check_pass":
                    sceneType = SceneType.conditional;
                    break;
                case "auto":
                    sceneType = SceneType.auto;
                    break;
                case "action":
                    sceneType = SceneType.action;
                    break;
            }
        }

        // Setup Scene Text
        sceneText.setText(player.getCurrentScene().body);

        // Setup options
        if (sceneType == SceneType.action) {
            options = new String[player.getCurrentScene().transitions.length];
            for(int x=0; x<player.getCurrentScene().transitions.length; x++) {
                options[x] = player.getCurrentScene().transitions[x].verb;
            }
        }
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
        optionsList.setAdapter(optionsAdapter);

        setOnClickListeners();
    }

    private void setOnClickListeners() {
        nextSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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
}
