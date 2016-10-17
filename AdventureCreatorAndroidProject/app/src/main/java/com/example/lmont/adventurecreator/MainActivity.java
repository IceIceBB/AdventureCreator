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
    GameHelper gameHelper;
    final String[] testStory = new String[1];
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
        gameHelper = GameHelper.getInstance(this);
    }

    public void test() {
        // Code for Spencer +++++++++++++++++++++
        testCreateStory();
        // ++++++++++++++++++++++++++++++++++++++

        editText1 = (EditText)findViewById(R.id.main_wordOneEditText);
        editText2 = (EditText)findViewById(R.id.main_wordTwoEditText);
        Button compareButton = (Button)findViewById(R.id.main_sendButton);
        compareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trannyCounter<6)
                    return;

                // Code for Paul --------------------
                Player player = Player.getInstance();
                Models.Story story = GameHelper.getInstance(getApplicationContext()).getFullStory(testStory[0]);
                Models.Scene currentScene = player.loadGame(story.chapters[0]);
                addText(currentScene.toString());

                addText("playerHasKey: " + player.checkIfPlayerHasModifier("key"));

                currentScene = player.getNextScene(currentScene.transitions[0].toSceneID);
                addText(currentScene.toString());

                currentScene = player.getNextScene(currentScene.transitions[0].toSceneID);
                addText(currentScene.toString());

                currentScene = player.getNextScene(currentScene.transitions[0].toSceneID);
                addText(currentScene.toString());

                currentScene = player.getNextScene(currentScene.transitions[0].toSceneID);
                addText(currentScene.toString());

                addText("playerHasKey: " + player.checkIfPlayerHasModifier("key"));

                addText(player.toString());

                //-----------------------------------

                ((ProgressBar)findViewById(R.id.main_progressBar)).setVisibility(View.VISIBLE);
                gameHelper.wordSimilarityValue(
                    editText1.getText().toString().replace(" ", "%20"),
                    editText2.getText().toString().replace(" ", "%20"),
                    new Response.Listener<Float>() {
                        @Override
                        public void onResponse(Float response) {
                            ((ProgressBar) findViewById(R.id.main_progressBar)).setVisibility(View.INVISIBLE);
                            addText(response.toString());
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

    public void testCreateStory() {
        gameHelper.addStory(new Models.Story(
                "The Bottle & The Key",
                "Can you escape the room?",
                "mystery",
                "puzzle",
                "short, key, mystery"
        ), new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {
                testStory[0] = response._id;
                testCreateChapter(response._id);
            }
        });
    }

    public void testCreateChapter(String storyID) {
        gameHelper.addChapter(new Models.Chapter(
                "Chapter 1",
                "The only chapter",
                "single chapter story",
                storyID
        ), new Response.Listener<Models.Chapter>() {
            @Override
            public void onResponse(Models.Chapter response) {
                testCreateScene(response._id);
            }
        });
    }

    final Models.Scene[] scenes = new Models.Scene[6];
    public void testCreateScene(String chapterID) {
        final boolean[] checks = new boolean[6];

        gameHelper.addScene(new Models.Scene(
                "intro",
                "you entered the room",
                null,
                "You find yourself in a small windowless room made out of smooth grey stone that dimly reflects the light from a single candle before you. Hardened rivulets of wax with a guttering flame at their core are fused with the rough grain of a heavy wooden desk, containing nothing else but a large and somewhat tarnished brass key, as well as a small faceted vial containing a green liquid. Ahead of you through the darkness, past two stone pillars on either side, you can see a dark wood door with ornate iron hinges.",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[0] = response;
                counter();
            }
        });

        gameHelper.addScene(new Models.Scene(
                "+key",
                "grabbed key",
                "key",
                "You reach out and grasp the key, noticing its weight as you slip it into your pocket.",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[1] = response;
                counter();
            }
        });

        gameHelper.addScene(new Models.Scene(
                "?key",
                "you try the key",
                null,
                "You walk over to the door, grasp the handle, and push.",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[2] = response;
                counter();
            }
        });

        gameHelper.addScene(new Models.Scene(
                "no key",
                "you couldn't open the door",
                null,
                "When the door does not open, you try pulling it toward you as well, to no avail. It feels locked, and you can’t help but notice a fairly large keyhole with a stream of light poking out of it.",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[3] = response;
                counter();
            }
        });

        gameHelper.addScene(new Models.Scene(
                "yes key",
                "you opened the door",
                null,
                "You make your way to the door, pulling the large key from your pocket. It fits perfectly into the keyhole, and the tumblers turn and click satisfyingly. Before you can even grasp the handle, the door swings outward ever so slightly, revealing a garden bathed in bright sunlight. Colorful flowers of every shape and size seem to glitter like gemstones as harmless insects buzz from bloom to bloom. A fountain gurgles in the center, filled with crystal clear water that trickles from the upturned vessel of a carved marble woman. As you step forward, you hear the swelling sound of thousands of voices in perfect harmony, and you realize you have reached the end of your journey, of all journeys. You are home.",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[4] = response;
                counter();
            }
        });

        gameHelper.addScene(new Models.Scene(
                "grab bottle",
                "you drank the bottle",
                null,
                "You uncork the bottle and drain its contents with one quick sip. The liquid tastes foul, and immediately you feel a burning sensation expanding out from your throat, quickly engulfing your whole body in excruciating pain. Invisible flames consume you inside and out, and before a minute has passed your vision darkens, the world condenses to a pinpoint before you, and you cannot help but feel you may have made a mistake somewhere…",
                chapterID
        ), new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                scenes[5] = response;
                counter();
            }
        });
    }

    int counter = 0;
    public void counter() {
        if (++counter >= 6) {
            testCreateTransitions(scenes);
        }
    }

    public int trannyCounter = 0;
    public void testCreateTransitions(Models.Scene[] scenes) {
        gameHelper.addTransition(new Models.Transition(
                "action",
                "grab key",
                null,
                null,
                null,
                0,
                scenes[0]._id,
                scenes[1]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "action",
                "open door",
                null,
                null,
                null,
                0,
                scenes[0]._id,
                scenes[2]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "action",
                "drink bottle",
                null,
                null,
                null,
                0,
                scenes[0]._id,
                scenes[5]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "auto",
                null,
                null,
                null,
                null,
                0,
                scenes[1]._id,
                scenes[0]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "check_fail",
                null,
                "key",
                null,
                null,
                0,
                scenes[2]._id,
                scenes[3]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "check_pass",
                null,
                "key",
                null,
                null,
                0,
                scenes[2]._id,
                scenes[4]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });

        gameHelper.addTransition(new Models.Transition(
                "auto",
                null,
                null,
                null,
                null,
                0,
                scenes[3]._id,
                scenes[0]._id
        ), new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                trannyCounter++;
            }
        });
    }

    public void addText(String text) {
        TextView textView = (TextView) findViewById(R.id.main_scoreText);
        textView.setText(textView.getText() + text + "\n\n");
    }
}
