package com.example.lmont.adventurecreator;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    APIHelper apiHelper;
    ContentResolverHelper contentObserver;
    EditText editText2;
    EditText editText1;
    AdventureDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setup();
        test();
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
