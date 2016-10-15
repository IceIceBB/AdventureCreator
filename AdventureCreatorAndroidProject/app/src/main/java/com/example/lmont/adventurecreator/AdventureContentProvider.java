package com.example.lmont.adventurecreator;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by lmont on 9/25/2016.
 */

public class AdventureContentProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.lmont.adventurecreator.AdventureContentProvider";
    public static final Uri CONTENT_URI = Uri.parse(("content://") + AUTHORITY);
    public static final Uri CONTENT_URI_STORIES = Uri.parse(("content://") + AUTHORITY + "/" + AdventureDBHelper.STORY_TABLE_NAME);
    public static final Uri CONTENT_URI_CHAPTERS = Uri.parse(("content://") + AUTHORITY + "/" + AdventureDBHelper.CHAPTER_TABLE_NAME);
    public static final Uri CONTENT_URI_SCENES = Uri.parse(("content://") + AUTHORITY + "/" + AdventureDBHelper.SCENES_TABLE_NAME);
    public static final Uri CONTENT_URI_TRANSITIONS = Uri.parse(("content://") + AUTHORITY + "/" + AdventureDBHelper.TRANSITIONS_TABLE_NAME);
    public static final int STORIES = 1, CHAPTERS = 2, SCENES = 3, TRANSITIONS = 4;
    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private AdventureDBHelper dbHelper;

    static {
        uriMatcher.addURI(AUTHORITY, AdventureDBHelper.STORY_TABLE_NAME, STORIES);
        uriMatcher.addURI(AUTHORITY, AdventureDBHelper.CHAPTER_TABLE_NAME, CHAPTERS);
        uriMatcher.addURI(AUTHORITY, AdventureDBHelper.SCENES_TABLE_NAME, SCENES);
        uriMatcher.addURI(AUTHORITY, AdventureDBHelper.TRANSITIONS_TABLE_NAME, TRANSITIONS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = AdventureDBHelper.getInstance(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case STORIES:
                dbHelper.addStory(values);
                break;
            case CHAPTERS:
                dbHelper.addChapter(values);
                break;
            case SCENES:
                dbHelper.addScene(values);
                break;
            case TRANSITIONS:
                dbHelper.addTransition(values);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
