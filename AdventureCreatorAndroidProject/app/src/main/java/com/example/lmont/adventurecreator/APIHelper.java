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
    public final static String ALL_URL = "https://infinite-shelf-21417.herokuapp.com/adventure/all";
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

    public void updateStory(Models.Story story, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("title", story.title);
            jsonBody.put("description", story.description);
            jsonBody.put("genre", story.genre);
            jsonBody.put("tags", story.tags);
            jsonBody.put("type", story.type);
        } catch (JSONException e) {
            Log.d(TAG, "updateStory: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, STORY_URL + "/" + story._id, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void updateChapter(Models.Chapter chapter, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("storyID", chapter.storyID);
            jsonBody.put("title", chapter.title);
            jsonBody.put("summary", chapter.summary);
            jsonBody.put("type", chapter.type);
        } catch (JSONException e) {
            Log.d(TAG, "updateChapter: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, CHAPTER_URL + "/" + chapter._id, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void updateScene(Models.Scene scene, Response.Listener listener, Response.ErrorListener errorListener) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("chapterID", scene.chapterID);
            jsonBody.put("title", scene.title);
            jsonBody.put("body", scene.body);
            jsonBody.put("journalText", scene.journalText);
            jsonBody.put("flagModifiers", scene.flagModifiers);
        } catch (JSONException e) {
            Log.d(TAG, "updateScene: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, SCENE_URL + "/" + scene._id, jsonBody, listener, errorListener);
        requestQueue.add(jsonObjectRequest);
    }

    public void updateTransition(Models.Transition transition, Response.Listener listener, Response.ErrorListener errorListener) {
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
            Log.d(TAG, "updateTransition: " + e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, TRANSITION_URL + "/" + transition._id, jsonBody, listener, errorListener);
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

    public void downloadAll(final Response.Listener<Models.RemoteData> listener, Response.ErrorListener errorListener) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ALL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                listener.onResponse(gson.fromJson(response.toString(), Models.RemoteData.class));
            }
        }, errorListener);
        requestQueue.add(jsonObjectRequest);
    }
}
