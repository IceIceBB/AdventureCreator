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
        test();
        libraryButton = (Button)findViewById(R.id.libraryButton);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameLibraryActivity.class);
                view.getContext().startActivity(intent);
            }
        });
    }

    private void setup() {
        apiHelper = APIHelper.getInstance(this);
        contentObserver = ContentResolverHelper.getInstance(this);
        dbHelper = AdventureDBHelper.getInstance(this);
    }

    public void test() {
        editText1 = (EditText)findViewById(R.id.main_wordOneEditText);
        editText2 = (EditText)findViewById(R.id.main_wordTwoEditText);
        Button compareButton = (Button)findViewById(R.id.main_sendButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ((ProgressBar)findViewById(R.id.main_progressBar)).setVisibility(View.VISIBLE);
            apiHelper.getWordComparisonValue(
                editText1.getText().toString().replace(" ", "%20"),
                editText2.getText().toString().replace(" ", "%20"),
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        ((ProgressBar) findViewById(R.id.main_progressBar)).setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.main_scoreText)).setText((String) response);
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

        GameHelper gameHelper = GameHelper.getInstance(this);
        Models.Story story = new Models.Story("Title", "Description", "Genre", "", "");
        gameHelper.addStory(story, new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {
                addText(response.toString());
            }
        });
    }

    public void addText(String text) {
        TextView textView = (TextView) findViewById(R.id.main_scoreText);
        textView.setText(textView.getText() + text + "\n\n");
    }
}
