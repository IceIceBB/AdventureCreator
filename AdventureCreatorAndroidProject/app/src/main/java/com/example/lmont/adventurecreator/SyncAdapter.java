package com.example.lmont.adventurecreator;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProvider;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by lmont on 9/25/2016.
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {
    private static String TAG = SyncAdapter.class.getCanonicalName();

    // Global variables
    // Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Set up the sync adapter
     */
    public SyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();
    }


    /**
     * Set up the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     */
    public SyncAdapter(
            Context context,
            boolean autoInitialize,
            boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /*
         * If your app uses a content resolver, get an instance of it
         * from the incoming Context
         */
        mContentResolver = context.getContentResolver();

    }


    // Set to true to clear
    private static boolean first = false;
    /**
     * This is the method that contains the logic for our sync.
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
     */
    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        AdventureDBHelper.getInstance(getContext()).deleteAll();
        APIHelper.getInstance(getContext()).downloadAll(new Response.Listener<Models.RemoteData>() {
            @Override
            public void onResponse(Models.RemoteData response) {
                Log.d("LEO", "onPerformSync: " + response.toString());
                for (Models.Story story : response.stories) {
                    ContentValues cv = new ContentValues();
                    cv.put("title", story.title);
                    cv.put("description", story.description);
                    cv.put("genre", story.genre);
                    cv.put("tags", story.tags);
                    cv.put("type", story.type);
                    cv.put("_id", story._id);
                    mContentResolver.insert(AdventureContentProvider.CONTENT_URI_STORIES, cv);
                }
                for (Models.Chapter chapter : response.chapters) {
                    ContentValues cv = new ContentValues();
                    cv.put("storyID", chapter.storyID);
                    cv.put("title", chapter.title);
                    cv.put("summary", chapter.summary);
                    cv.put("type", chapter.type);
                    cv.put("_id", chapter._id);
                    mContentResolver.insert(AdventureContentProvider.CONTENT_URI_CHAPTERS, cv);
                }
                for (Models.Scene scene : response.scenes) {
                    ContentValues cv = new ContentValues();
                    cv.put("chapterID", scene.chapterID);
                    cv.put("title", scene.title);
                    cv.put("body", scene.body);
                    cv.put("journalText", scene.journalText);
                    cv.put("flagModifiers", scene.flagModifiers);
                    cv.put("_id", scene._id);
                    mContentResolver.insert(AdventureContentProvider.CONTENT_URI_SCENES, cv);
                }
                for (Models.Transition transition : response.transitions) {
                    ContentValues cv = new ContentValues();
                    cv.put("fromSceneID", transition.fromSceneID);
                    cv.put("toSceneID", transition.toSceneID);
                    cv.put("type", transition.type);
                    cv.put("verb", transition.verb);
                    cv.put("flag", transition.flag);
                    cv.put("attribute", transition.attribute);
                    cv.put("comparator", transition.comparator);
                    cv.put("challengeLevel", transition.challengeLevel);
                    cv.put("_id", transition._id);
                    mContentResolver.insert(AdventureContentProvider.CONTENT_URI_TRANSITIONS, cv);
                }
            }
        }, null);
    }

    /**
     * Helper method for turning an InputStream into a String.
     * @param inStream
     * @return String with the contents of the InputStream.
     * @throws IOException
     */
    private static String getInputData(InputStream inStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

        String data = null;

        while ((data = reader.readLine()) != null){
            builder.append(data);
        }

        reader.close();

        return builder.toString();
    }
}