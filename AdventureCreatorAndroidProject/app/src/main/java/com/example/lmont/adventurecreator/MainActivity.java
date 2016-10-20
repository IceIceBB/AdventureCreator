package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.android.volley.Response;

public class MainActivity extends AppCompatActivity {

    public final static String TAG = "MainActivity";

    APIHelper apiHelper;
    ContentResolverHelper contentObserverHelper;
    AdventureDBHelper dbHelper;
    Button libraryButton;
    Button storyCreatorButton;
    Button deleteDbButton;
    Button playButton;
    GameHelper gameHelper;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setup();
//        test(3);
//        test1UpdateRoutes();
    }

    private void startLoading() {
        progressBar.setVisibility(View.VISIBLE);
        libraryButton.setVisibility(View.GONE);
        storyCreatorButton.setVisibility(View.GONE);
        deleteDbButton.setVisibility(View.GONE);
        playButton.setVisibility(View.GONE);
    }


    private void doneLoading() {
        progressBar.setVisibility(View.GONE);
        libraryButton.setVisibility(View.VISIBLE);
        storyCreatorButton.setVisibility(View.VISIBLE);
        deleteDbButton.setVisibility(View.VISIBLE);
        playButton.setVisibility(View.VISIBLE);
    }

    private void setup() {
        playButton = (Button) findViewById(R.id.main_sendButton);
        apiHelper = APIHelper.getInstance(this);
        contentObserverHelper = ContentResolverHelper.getInstance(this);
        dbHelper = AdventureDBHelper.getInstance(this);
        gameHelper = GameHelper.getInstance(this);
        progressBar = (ProgressBar) findViewById(R.id.main_progressBar);

        libraryButton = (Button)findViewById(R.id.libraryButton);
        libraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), GameLibraryActivity.class);
                view.getContext().startActivity(intent);
            }
        });
        storyCreatorButton = (Button) findViewById(R.id.storyCreatorButton);
        storyCreatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), StoryCreation.class);
                view.getContext().startActivity(intent);
            }
        });

        deleteDbButton = (Button) findViewById(R.id.deleteDbButton);
        deleteDbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AdventureDBHelper.getInstance(MainActivity.this).deleteAll();
            }
        });
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLoading();
                contentObserverHelper.requestSync(new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        doneLoading();
                    }
                });
            }
        });
    }

    public void test(int genre) {
        startLoading();
        // Code for Spencer +++++++++++++++++++++
        testCreateStory(genre);
    }

    private void testUpdateRoutes() {
        Models.Transition tr = new Models.Transition("a", "a", "a", "a", "a", 0, "a", "a");
        tr._id = "58052b47b84afb00116dc429";
        Models.Scene sc = new Models.Scene("a", "a", "a", "a", "a");
        sc._id = "58052b47b84afb00116dc423";
        Models.Chapter c = new Models.Chapter("t", "s", "t", "id");
        c._id = "58052b47b84afb00116dc422";
        Models.Story s = new Models.Story("The B", "Creator", "Can You", "Mystery", "Short", "Puzzle");
        s._id = "58052b46b84afb00116dc421";

        gameHelper.updateStory(s, new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {

            }
        });
        gameHelper.updateChapter(c, new Response.Listener<Models.Chapter>() {
            @Override
            public void onResponse(Models.Chapter response) {

            }
        });
        gameHelper.updateScene( sc, new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {

            }
        });
        gameHelper.updateTransition(tr, new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {

            }
        });
    }

    public void testCreateStory(int genre) {
        switch (genre) {
            case 0:
                gameHelper.addStory(new Models.Story(
                "Scary Version: The Bottle & The Key",
                "Paul",
                "Can you escape the room?",
                "horror",
                "puzzle",
                "short, key, mystery"
                ), new Response.Listener<Models.Story>() {
                    @Override
                    public void onResponse(Models.Story response) {
                        testCreateChapter(response._id);
                    }
                });
                break;
            case 1:
                gameHelper.addStory(new Models.Story(
                        "Futuristic Version: The Bottle & The Key",
                        "Paul",
                        "Can you escape the room?",
                        "scifi",
                        "puzzle",
                        "short, key, mystery"
                ), new Response.Listener<Models.Story>() {
                    @Override
                    public void onResponse(Models.Story response) {
                        testCreateChapter(response._id);
                    }
                });
                break;
            case 2:
                gameHelper.addStory(new Models.Story(
                        "Fantastical Version: The Bottle & The Key",
                        "Paul",
                        "Can you escape the room?",
                        "fantasy",
                        "puzzle",
                        "short, key, mystery"
                ), new Response.Listener<Models.Story>() {
                    @Override
                    public void onResponse(Models.Story response) {
                        testCreateChapter(response._id);
                    }
                });
                break;
            default:
                gameHelper.addStory(new Models.Story(
                        "Mysterious Version: The Bottle & The Key",
                        "Paul",
                        "Can you escape the room?",
                        "mystery",
                        "puzzle",
                        "short, key, mystery"
                ), new Response.Listener<Models.Story>() {
                    @Override
                    public void onResponse(Models.Story response) {
                        testCreateChapter(response._id);
                    }
                });
                break;
        }
    }

    public void testCreateChapter(String storyID) {
        gameHelper.addChapter(new Models.Chapter(
                "Chapter 1",
                "The first chapter",
                "Part 1",
                storyID
        ), new Response.Listener<Models.Chapter>() {
            @Override
            public void onResponse(Models.Chapter response) {
                testCreateScene(response._id);
            }
        });
//        gameHelper.addChapter(new Models.Chapter(
//                "Chapter 2",
//                "The second chapter",
//                "Part 2!! It's like part 1, but part 2!",
//                storyID
//        ), new Response.Listener<Models.Chapter>() {
//            @Override
//            public void onResponse(Models.Chapter response) {
//                testCreateScene(response._id);
//            }
//        });
    }

    final Models.Scene[] scenes = new Models.Scene[6];
    public void testCreateScene(String chapterID) {
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

    public void testCreateTransitions(Models.Scene[] scenes) {
        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });

        startDownloadCounter();
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
                doneDownloadCounter();
            }
        });
    }

    int downloadCounter = 0;
    public void startDownloadCounter() {
        downloadCounter++;
    }

    public void doneDownloadCounter() {
        downloadCounter--;
        if (downloadCounter <= 0){
            doneLoading();
        }
    }

    int counter = 0;
    public void counter() {
        if (++counter >= 6) {
            testCreateTransitions(scenes);
        }
    }

}
