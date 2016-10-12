package com.example.lmont.adventurecreator;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lmont on 10/10/2016.
 */

public class APIHelper {

    public final static String STORY_URL = "https://infinite-shelf-21417.herokuapp.com/adventure/story";
    public final static String CHAPTER_URL = "https://infinite-shelf-21417.herokuapp.com/adventure/chapter";
    public final static String SCENE_URL = "https://infinite-shelf-21417.herokuapp.com/adventure/scene";
    public final static String TRANSITION_URL = "https://infinite-shelf-21417.herokuapp.com/adventure/transition";
    public final static String SWOOGLE_URL = "http://swoogle.umbc.edu/StsService/GetStsSim?operation=api";
    public final static String TAG = "API_HELPER";

    public static APIHelper instance;

    private RequestQueue requestQueue;

    private Context context;

    public static APIHelper getInstance(Context context) {
        if (instance == null)
            instance = new APIHelper(context.getApplicationContext());

        return instance;
    }

    private APIHelper(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public void getWordComparisonValue(String word1, String word2, Response.Listener listener, Response.ErrorListener errorListener) {
        // Instantiate the RequestQueue.
        String url = SWOOGLE_URL + "&phrase1=" + word1 + "&phrase2=" + word2;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, listener, errorListener);
        // Add the request to the RequestQueue.
        requestQueue.add(stringRequest);
    }

    public void addStory(Models.Story story, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("title", story.title);
            jsonBody.put("description", story.description);
            jsonBody.put("genre", story.genre);
            jsonBody.put("tags", story.tags);
            jsonBody.put("type", story.type);
        } catch (JSONException e) {
            Log.d(TAG, "addStory: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, STORY_URL, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void addChapter(Models.Chapter chapter, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("storyID", chapter.storyID);
            jsonBody.put("title", chapter.title);
            jsonBody.put("summary", chapter.summary);
            jsonBody.put("type", chapter.type);
        } catch (JSONException e) {
            Log.d(TAG, "addChapter: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, CHAPTER_URL, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void addScene(Models.Scene scene, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("chapterID", scene.chapterID);
            jsonBody.put("title", scene.title);
            jsonBody.put("body", scene.body);
            jsonBody.put("journalText", scene.journalText);
            jsonBody.put("flagModifiers", scene.flagModifiers);
        } catch (JSONException e) {
            Log.d(TAG, "addScene: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SCENE_URL, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void addTransition(Models.Transition transition, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("fromSceneID", transition.fromSceneID);
            jsonBody.put("toSceneID", transition.toSceneID);
            jsonBody.put("type", transition.type);
            jsonBody.put("verb", transition.verb);
            jsonBody.put("flag", transition.flag);
            jsonBody.put("attribute", transition.attribute);
            jsonBody.put("comparator", transition.comparator);
            jsonBody.put("challengeLevel", transition.challengeLevel);
        } catch (JSONException e) {
            Log.d(TAG, "addTransition: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, TRANSITION_URL, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void downloadStory(String storyID, final Response.Listener<Models.Story> listener, Response.ErrorListener errorListener) {
        String getURL = STORY_URL + "/" + storyID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Models.Story story = gson.fromJson(response.toString(), Models.StoryResponse.class).story;
                listener.onResponse(story);
                AdventureDBHelper.getInstance(context).addStory(story);
            }
        }, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void downloadChapters(String storyID, final Response.Listener<Models.Chapter[]> listener, Response.ErrorListener errorListener) {
        String getURL = CHAPTER_URL + "/" + storyID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Models.Chapter[] array = gson.fromJson(response.toString(), Models.ChapterResponseArray.class).chapters;
                listener.onResponse(array);
                for (Models.Chapter chapter: array) {
                    AdventureDBHelper.getInstance(context).addChapter(chapter);
                }
            }
        }, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void downloadScenes(String chapterID, final Response.Listener<Models.Scene[]> listener, Response.ErrorListener errorListener) {
        String getURL = SCENE_URL + "/" + chapterID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Models.Scene[] array = gson.fromJson(response.toString(), Models.SceneResponseArray.class).scenes;
                listener.onResponse(array);
                for (Models.Scene scene: array) {
                    AdventureDBHelper.getInstance(context).addScene(scene);
                }
            }
        }, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void downloadTransitions(String sceneID, final Response.Listener<Models.Transition[]> listener, Response.ErrorListener errorListener) {
        String getURL = TRANSITION_URL + "/" + sceneID;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, getURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Models.Transition[] array = gson.fromJson(response.toString(), Models.TransitionResponseArray.class).transitions;
                listener.onResponse(array);
                for (Models.Transition transition: array) {
                    AdventureDBHelper.getInstance(context).addTransition(transition);
                }
            }
        }, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void downloadFullGame(final String gameID, final Response.Listener<Models.Story> listener, Response.ErrorListener errorListener) {
        final Models.Story[] story = new Models.Story[1];
        final int[] one = new int[1];
        final int[] two = new int[1];
        final int[] counter = new int[1];
        counter[0] = 0;
        downloadStory(gameID, new Response.Listener<Models.Story>() {
            @Override
            public void onResponse(Models.Story response) {
                story[0] = response;
                downloadChapters(response._id,
                        new Response.Listener<Models.Chapter[]>() {
                            @Override
                            public void onResponse(Models.Chapter[] response) {
                                one[0] = response.length;
                                story[0].chapters = response;
                                for (Models.Chapter chapter: response) {
                                    final int[] x = new int[1];
                                    x[0] = 0;
                                    downloadScenes(chapter._id, new Response.Listener<Models.Scene[]>() {
                                        @Override
                                        public void onResponse(Models.Scene[] response) {
                                            two[0] = response.length;
                                            story[0].chapters[x[0]].scenes = response;
                                            for (Models.Scene scene: response) {
                                                final int[] y = new int[1];
                                                y[0]=0;
                                                downloadTransitions(scene._id, new Response.Listener<Models.Transition[]>() {
                                                    @Override
                                                    public void onResponse(Models.Transition[] response) {
                                                        counter[0]++;
                                                        story[0].chapters[x[0]].scenes[y[0]].transitions = response;
                                                        if ((one[0] * two[0])-2 == counter[0]) {
                                                            Log.d(TAG, "onResponse: SUCCESS");
                                                            listener.onResponse(story[0]);
                                                        }
                                                    }
                                                }, null);
                                                y[0]++;
                                            }
                                        }
                                    }, null);
                                    x[0]++;
                                }
                            }
                        }, null);
            }
        }, null);
    }
}
