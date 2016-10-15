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

        apiHelper.downloadAll(new Response.Listener<Models.RemoteData>() {
            @Override
            public void onResponse(Models.RemoteData response) {
                addText(response.toString());
            }
        }, null);

//        apiHelper.downloadStory(
//                "57fd2445be28a8485ceec97f", new Response.Listener<Models.Story>() {
//                    @Override
//                    public void onResponse(Models.Story response) {
//                        addText(response.toString());
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        apiHelper.downloadChapters(
//                "57fd2445be28a8485ceec97f",
//                new Response.Listener<Models.Chapter[]>() {
//                    @Override
//                    public void onResponse(Models.Chapter[] response) {
//                        addText(Arrays.toString(response));
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        apiHelper.downloadScenes(
//                "57fd24afbe28a8485ceec981",
//                new Response.Listener<Models.Scene[]>() {
//                    @Override
//                    public void onResponse(Models.Scene[] response) {
//                        addText(Arrays.toString(response));
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });
//
//        apiHelper.downloadTransitions(
//                "57fd24cbbe28a8485ceec983",
//                new Response.Listener<Models.Transition[]>() {
//                    @Override
//                    public void onResponse(Models.Transition[] response) {
//                        addText(Arrays.toString(response));
//                    }
//                }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//
//                    }
//                });

//        dbHelper.addStory(new Models.Story("t", "d", "g", "t", "t"), new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//                ((TextView) findViewById(R.id.main_scoreText)).setText(response.toString());
//            }
//        });
//        dbHelper.addChapter(new Models.Chapter("t", "s", "t", "s"), new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//                ((TextView) findViewById(R.id.main_scoreText)).setText(response.toString());
//            }
//        });
//        dbHelper.addScene(new Models.Scene("t", "j", "f", "b", "c"), new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//                ((TextView) findViewById(R.id.main_scoreText)).setText(response.toString());
//            }
//        });
//        dbHelper.addTransition(new Models.Transition("t", "v", "f", "a", "c", 69, "f", "t"), new Response.Listener() {
//            @Override
//            public void onResponse(Object response) {
//                ((TextView) findViewById(R.id.main_scoreText)).setText(response.toString());
//            }
//        });
    }

    public void addText(String text) {
        TextView textView = (TextView) findViewById(R.id.main_scoreText);
        textView.setText(textView.getText() + text + "\n\n");
    }
}
