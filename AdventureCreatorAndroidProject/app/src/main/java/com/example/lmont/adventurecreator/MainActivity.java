package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    APIHelper apiHelper;
    ContentResolverHelper contentObserver;
    EditText editText2;
    EditText editText1;
    AdventureDBHelper dbHelper;
    Button libraryButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
//        test();
        testUpdateRoutes();
    }

    private void testUpdateRoutes() {
        Models.Transition tr = new Models.Transition("a", "a", "a", "a", "a", 0, "a", "a");
        tr._id = "58052b47b84afb00116dc429";
        Models.Scene sc = new Models.Scene("a", "a", "a", "a", "a");
        sc._id = "58052b47b84afb00116dc423";
        Models.Chapter c = new Models.Chapter("t", "s", "t", "id");
        c._id = "58052b47b84afb00116dc422";
        Models.Story s = new Models.Story("The B", "Can You", "Mystery", "Short", "Puzzle");
        s._id = "58052b46b84afb00116dc421";

        gameHelper.updateStory(s, new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {
                addText(response.toString());
            }
        });
        gameHelper.updateChapter(c, new Response.Listener<Models.Chapter>() {
            @Override
            public void onResponse(Models.Chapter response) {
                addText(response.toString());
            }
        });
        gameHelper.updateScene(sc, new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                addText(response.toString());
            }
        });
        gameHelper.updateTransition(tr, new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                addText(response.toString());
            }
        });
    }

    private void setup() {
        apiHelper = APIHelper.getInstance(this);
        contentObserver = ContentResolverHelper.getInstance(this);
        dbHelper = AdventureDBHelper.getInstance(this);
        gameHelper = GameHelper.getInstance(this);

        libraryButton = (Button)findViewById(R.id.libraryButton);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameLibraryActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    public void test() {
        editText1 = (EditText)findViewById(R.id.main_wordOneEditText);
        editText2 = (EditText)findViewById(R.id.main_wordTwoEditText);
        Button compareButton = (Button)findViewById(R.id.main_sendButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Models.Transition[] transitions = GameHelper.getInstance(getApplicationContext()).getTransitionsForScenes("57fdbf659ed97a256090b98b");
            for (Models.Transition tranny: transitions) {
                addText(tranny.toString());
            }

            ((ProgressBar)findViewById(R.id.main_progressBar)).setVisibility(View.VISIBLE);
            apiHelper.getWordComparisonValue(
                editText1.getText().toString().replace(" ", "%20"),
                editText2.getText().toString().replace(" ", "%20"),
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        ((ProgressBar) findViewById(R.id.main_progressBar)).setVisibility(View.INVISIBLE);
                        addText((String) response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                }
            });
            }
        });

        dbHelper.deleteAll();
    }

    public void addText(String text) {
        TextView textView = (TextView) findViewById(R.id.main_scoreText);
        textView.setText(textView.getText() + text + "\n\n");
    }
}
