package com.example.lmont.adventurecreator;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.Response;

import java.util.ArrayList;


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
    EditText userInputEditText;
    ProgressBar loadingProgressBar;
    Context context;

    Models.Transition[] transitions;
    Player player;
    SceneType sceneType;
    String[] options;

    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String genre = getIntent().getStringExtra("genre");
        if (genre.equals("scifi")){
            setTheme(R.style.SciFiTheme);
        }else if (genre.equals("fantasy")){
            setTheme(R.style.FantasyTheme);
        }else if (genre.equals("horror")) {
            setTheme(R.style.HorrorTheme);
        }
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
        userInputEditText = (EditText) findViewById(R.id.editText);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading_progressBar);
        options = null;
        context = this;

        // Instantiate Player
        player = Player.getInstance();

        // Get Scene Type
        transitions = player.getCurrentScene().transitions;

        if (transitions.length == 0) {
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
            ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, options);
            optionsList.setAdapter(optionsAdapter);
        } else {
            options = new String[0];
            userInputEditText.setVisibility(View.GONE);
            hintButton.setVisibility(View.GONE);
            if (sceneType == SceneType.end) {
                nextSceneButton.setText("THE END");
            }
        }

        setOnClickListeners();
    }

    private void showLoading() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        nextSceneButton.setVisibility(View.INVISIBLE);
    }

    private void hideLoading() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        nextSceneButton.setVisibility(View.VISIBLE);
    }

    private void playActionScene() {
        final boolean[] isFound = {false};
        final String userInput = userInputEditText.getText().toString();
        final ArrayList<Models.Transition> values = new ArrayList();
        final int[] loadCounter = {0};
        showLoading();
        for(final Models.Transition transition : transitions) {
            GameHelper.getInstance(context).wordSimilarityValue(userInput, transition.verb, new Response.Listener<Float>() {
                @Override
                public void onResponse(Float response) {
                loadCounter[0]++;
                if (response > .3) {
                    values.add(transition);
                }
                if (loadCounter[0] >= transitions.length) {
                    hideLoading();
                    switch (values.size()) {
                        case 0:
                            // No Match
                            return;

                        case 1:
                            // One Match
                            player.getNextScene(values.get(0).toSceneID);
                            Intent intent = new Intent(GamePlayActivity.this, GamePlayActivity.class);
                            startActivity(intent);
                            break;

                        default:
                            // Multiple Matches
                            // Hide Hint button
                            hintButton.setVisibility(View.INVISIBLE);
                            // Set up valid options list
                            String[] validVerbs = new String[values.size()];
                            for(int x=0; x<values.size(); x++) {
                                validVerbs[x] = values.get(x).verb;
                            }
                            ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, validVerbs);
                            optionsList.setAdapter(optionsAdapter);

                            optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    player.getNextScene(values.get(position).toSceneID);
                                    Intent intent = new Intent(GamePlayActivity.this, GamePlayActivity.class);
                                    startActivity(intent);
                                }
                            });
                            // Show Valid Options
                            showHint();
                            break;
                    }
                }
                }
            }, null);
        }
    }

    private void playNonActionScene() {
        // case end
        Intent intent = new Intent(GamePlayActivity.this, MainActivity.class);
        switch (sceneType) {
            case auto:
                player.getNextScene(transitions[0].toSceneID);
                intent = new Intent(GamePlayActivity.this, GamePlayActivity.class);
            break;

            case conditional:
                if (player.checkIfPlayerHasModifier(transitions[0].flag)) {
                    if (transitions[0].type.equals("check_pass")) {
                        player.getNextScene(transitions[0].toSceneID);
                    } else {
                        player.getNextScene(transitions[1].toSceneID);
                    }
                } else {
                    if (transitions[0].type.equals("check_fail")) {
                        player.getNextScene(transitions[0].toSceneID);
                    } else {
                        player.getNextScene(transitions[1].toSceneID);
                    }
                }
                intent = new Intent(GamePlayActivity.this, GamePlayActivity.class);
            break;
        }
        startActivity(intent);
    }

    private void setOnClickListeners() {

        optionsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                player.getNextScene(transitions[position].toSceneID);
                Intent intent = new Intent(GamePlayActivity.this, GamePlayActivity.class);
                startActivity(intent);
            }
        });

        nextSceneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sceneType != SceneType.action) {
                    playNonActionScene();
                } else {
                    playActionScene();
                }
            }
        });

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bookmark.getCurrentView() != bookmarkHollow){
                    return;
                }else if(bookmark.getCurrentView() != bookmarkSolid){
                    bookmark.showNext();
                    player.saveGame();
                    Toast.makeText(context, "Progress Saved", Toast.LENGTH_SHORT).show();
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
//                    int colorFrom = getResources().getColor(R.color.colorPrimary);
//                    int colorTo = getResources().getColor(R.color.colorAccent);
                    int colorFrom = getHintColor("primary");
                    int colorTo = getHintColor("accent");
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
                    showHint();
                }
                else if (!hintReady&&hintShowing){
                    hideHint();
                }
            }
        });
    }

    private void hideHint() {
//        int colorFrom = getResources().getColor(R.color.colorAccent);
//        int colorTo = getResources().getColor(R.color.colorPrimary);
        int colorFrom = getHintColor("accent");
        int colorTo = getHintColor("primary");
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                hintButton.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        colorAnimation.start();
        hintShowing = false;
        nextSceneButton.setVisibility(View.VISIBLE);
        optionsList.setVisibility(View.GONE);
    }

    private void showHint() {
        hintReady = false;
        hintShowing = true;
        userInputEditText.setVisibility(View.INVISIBLE);
        nextSceneButton.setVisibility(View.INVISIBLE);
        optionsList.setVisibility(View.VISIBLE);
        optionsList.bringToFront();
    }

    private int getHintColor(String item){
        if (item.equals("primary")) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
            int color = typedValue.data;
        }else if (item.equals("accent")){
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
            int color = typedValue.data;
        }
        return color;
    }
}
