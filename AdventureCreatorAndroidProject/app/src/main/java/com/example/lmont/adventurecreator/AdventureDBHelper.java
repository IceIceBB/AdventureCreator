package com.example.lmont.adventurecreator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by lmont on 9/25/2016.
 */
public class AdventureDBHelper extends SQLiteOpenHelper {

    public final static int VERSION = 4;
    public final static String
        TAG = "AdventureDBHelper",
        DB_NAME = "adventureDB",
        STORY_TABLE_NAME = "stories",
        CHAPTER_TABLE_NAME = "chapters",
        SCENES_TABLE_NAME = "scenes",
        TRANSITIONS_TABLE_NAME = "transitions";

    public final static String[]
        STORY_COLUMNS = new String[]{"_id", "title", "description", "genre", "type", "tags"},
        CHAPTER_COLUMNS = new String[]{"_id", "title", "summary", "type", "storyID"},
        SCENE_COLUMNS = new String[]{"_id", "title", "journalText", "flagModifiers", "body", "chapterID"},
        TRANSITION_COLUMNS = new String[]{"_id", "type", "verb", "flag", "attribute", "comparator", "challengeLevel", "fromSceneID", "toSceneID"};

    public final static String CREATE_STORY_TABLE =
        "CREATE TABLE IF NOT EXISTS " + STORY_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "description TEXT," +
        "genre TEXT," +
        "type TEXT," +
        "tags STRING)";

    public final static String CREATE_CHAPTER_TABLE =
        "CREATE TABLE IF NOT EXISTS " + CHAPTER_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "summary TEXT," +
        "type TEXT," +
        "storyID TEXT)";

    public final static String CREATE_SCENES_TABLE =
        "CREATE TABLE IF NOT EXISTS " + SCENES_TABLE_NAME + " (" +
        "_id TEXT," +
        "title TEXT," +
        "journalText TEXT," +
        "flagModifiers TEXT," +
        "body TEXT," +
        "chapterID TEXT)";

    public final static String CREATE_TRANSITIONS_TABLE =
        "CREATE TABLE IF NOT EXISTS " + TRANSITIONS_TABLE_NAME + " (" +
        "_id TEXT," +
        "type TEXT," +
        "verb TEXT," +
        "flag TEXT," +
        "attribute TEXT," +
        "comparator TEXT," +
        "challengeLevel INTEGER," +
        "fromSceneID TEXT," +
        "toSceneID TEXT)";

    private static AdventureDBHelper instance;

    private APIHelper apiHelper;

    public static AdventureDBHelper getInstance(Context context) {
        if (instance == null)
            instance = new AdventureDBHelper(context.getApplicationContext());

        return instance;
    }

    private AdventureDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        apiHelper = APIHelper.getInstance(context);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STORY_TABLE);
        db.execSQL(CREATE_CHAPTER_TABLE);
        db.execSQL(CREATE_SCENES_TABLE);
        db.execSQL(CREATE_TRANSITIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STORY_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CHAPTER_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SCENES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TRANSITIONS_TABLE_NAME);
        onCreate(db);
    }

    public void addStory(ContentValues cv) {
        getWritableDatabase().insert(STORY_TABLE_NAME, null, cv);
    }

    public void addStory(Models.Story story) {
        ContentValues cv = new ContentValues();
        cv.put("title", story.title);
        cv.put("description", story.description);
        cv.put("genre", story.genre);
        cv.put("tags", story.tags);
        cv.put("type", story.type);
        cv.put("_id", story._id);
        getWritableDatabase().insert(STORY_TABLE_NAME, null, cv);
    }

    public void updateStory(Models.Story story) {
        ContentValues cv = new ContentValues();
        cv.put("title", story.title);
        cv.put("description", story.description);
        cv.put("genre", story.genre);
        cv.put("tags", story.tags);
        cv.put("type", story.type);
        getWritableDatabase().update(STORY_TABLE_NAME, cv, "_id = '" + story._id + "'", null);
    }

    public Models.Story getStory(String storyID) {
        Cursor cursor = getReadableDatabase().query(STORY_TABLE_NAME, STORY_COLUMNS, "_id = " + storyID, null, null, null, null);
        cursor.moveToFirst();

        Models.Story story = new Models.Story(
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("description")),
                cursor.getString(cursor.getColumnIndex("genre")),
                cursor.getString(cursor.getColumnIndex("type")),
                cursor.getString(cursor.getColumnIndex("tags"))
        );

        story._id = cursor.getString(cursor.getColumnIndex("_id"));

        return story;
    }

    public Models.Story[] getAllStories() {
        Cursor cursor = getReadableDatabase().query(STORY_TABLE_NAME, STORY_COLUMNS, null, null, null, null, null);

        Models.Story[] stories = new Models.Story[cursor.getCount()];
        while (cursor.moveToNext()) {
            Models.Story story = new Models.Story(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("genre")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("tags"))
            );
            story._id = cursor.getString(cursor.getColumnIndex("_id"));

            stories[cursor.getPosition()] = story;
        }

        return stories;
    }

    public void addChapter(ContentValues cv) {
        getWritableDatabase().insert(CHAPTER_TABLE_NAME, null, cv);
    }

    public void addChapter(Models.Chapter chapter) {
        ContentValues cv = new ContentValues();
        cv.put("storyID", chapter.storyID);
        cv.put("title", chapter.title);
        cv.put("summary", chapter.summary);
        cv.put("type", chapter.type);
        cv.put("_id", chapter._id);
        getWritableDatabase().insert(CHAPTER_TABLE_NAME, null, cv);
    }

    public void updateChapter(Models.Chapter chapter) {
        ContentValues cv = new ContentValues();
        cv.put("storyID", chapter.storyID);
        cv.put("title", chapter.title);
        cv.put("summary", chapter.summary);
        cv.put("type", chapter.type);
        getWritableDatabase().update(CHAPTER_TABLE_NAME, cv, "_id = '" + chapter._id +"'", null);
    }

    public Models.Chapter getChapter(String chapterID) {
        Cursor cursor = getReadableDatabase().query(CHAPTER_TABLE_NAME, CHAPTER_COLUMNS, "_id = " + chapterID, null, null, null, null);
        cursor.moveToFirst();

        Models.Chapter chapter = new Models.Chapter(
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("summary")),
                cursor.getString(cursor.getColumnIndex("type")),
                cursor.getString(cursor.getColumnIndex("storyID"))
        );

        chapter._id = cursor.getString(cursor.getColumnIndex("_id"));

        return chapter;
    }

    public Models.Chapter[] getChaptersForStory(String storyID) {
        Cursor cursor = getReadableDatabase().query(CHAPTER_TABLE_NAME, CHAPTER_COLUMNS, "storyID = '" + storyID + "'", null, null, null, null);

        Models.Chapter[] chapters = new Models.Chapter[cursor.getCount()];
        while (cursor.moveToNext()) {
            Models.Chapter chapter = new Models.Chapter(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("summary")),
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("storyID"))
            );

            chapter._id = cursor.getString(cursor.getColumnIndex("_id"));
            chapters[cursor.getPosition()] = chapter;
        }

        return chapters;
    }

    public void addScene(ContentValues cv) {
        getWritableDatabase().insert(SCENES_TABLE_NAME, null, cv);
    }

    public void addScene(Models.Scene scene) {
        ContentValues cv = new ContentValues();
        cv.put("chapterID", scene.chapterID);
        cv.put("title", scene.title);
        cv.put("body", scene.body);
        cv.put("journalText", scene.journalText);
        cv.put("flagModifiers", scene.flagModifiers);
        cv.put("_id", scene._id);
        getWritableDatabase().insert(SCENES_TABLE_NAME, null, cv);
    }

    public void updateScene(Models.Scene scene) {
        ContentValues cv = new ContentValues();
        cv.put("chapterID", scene.chapterID);
        cv.put("title", scene.title);
        cv.put("body", scene.body);
        cv.put("journalText", scene.journalText);
        cv.put("flagModifiers", scene.flagModifiers);
        getWritableDatabase().update(SCENES_TABLE_NAME, cv, "_id = '" + scene._id + "'", null);
    }

    public Models.Scene getScene(String sceneID) {
        Cursor cursor = getReadableDatabase().query(SCENES_TABLE_NAME, SCENE_COLUMNS, "_id = " + sceneID, null, null, null, null);
        cursor.moveToFirst();

        Models.Scene scene = new Models.Scene(
                cursor.getString(cursor.getColumnIndex("title")),
                cursor.getString(cursor.getColumnIndex("journalText")),
                cursor.getString(cursor.getColumnIndex("flagModifiers")),
                cursor.getString(cursor.getColumnIndex("body")),
                cursor.getString(cursor.getColumnIndex("chapterID"))
        );

        scene._id = cursor.getString(cursor.getColumnIndex("_id"));

        return scene;
    }

    public Models.Scene[] getScenesForChapter(String sceneID) {
        Cursor cursor = getReadableDatabase().query(SCENES_TABLE_NAME, SCENE_COLUMNS, "chapterID = '" + sceneID + "'", null, null, null, null);

        Models.Scene[] scenes = new Models.Scene[cursor.getCount()];
        while (cursor.moveToNext()) {
            Models.Scene scene = new Models.Scene(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("journalText")),
                    cursor.getString(cursor.getColumnIndex("flagModifiers")),
                    cursor.getString(cursor.getColumnIndex("body")),
                    cursor.getString(cursor.getColumnIndex("chapterID"))
            );

            scene._id = cursor.getString(cursor.getColumnIndex("_id"));
            scenes[cursor.getPosition()] = scene;
        }

        return scenes;
    }

    public void addTransition(ContentValues cv) {
        getWritableDatabase().insert(TRANSITIONS_TABLE_NAME, null, cv);
    }

    public void addTransition(Models.Transition transition) {
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
        getWritableDatabase().insert(TRANSITIONS_TABLE_NAME, null, cv);
    }

    public void updateTransition(Models.Transition transition) {
        ContentValues cv = new ContentValues();
        cv.put("fromSceneID", transition.fromSceneID);
        cv.put("toSceneID", transition.toSceneID);
        cv.put("type", transition.type);
        cv.put("verb", transition.verb);
        cv.put("flag", transition.flag);
        cv.put("attribute", transition.attribute);
        cv.put("comparator", transition.comparator);
        cv.put("challengeLevel", transition.challengeLevel);
        getWritableDatabase().update(TRANSITIONS_TABLE_NAME, cv, "_id = '" + transition._id + "'", null);
    }

    public Models.Transition getTransition(String transitionID) {
        Cursor cursor = getReadableDatabase().query(TRANSITIONS_TABLE_NAME, TRANSITION_COLUMNS, "_id = " + transitionID + "", null, null, null, null);
        cursor.moveToFirst();

        Models.Transition transition = new Models.Transition(
                cursor.getString(cursor.getColumnIndex("type")),
                cursor.getString(cursor.getColumnIndex("verb")),
                cursor.getString(cursor.getColumnIndex("flag")),
                cursor.getString(cursor.getColumnIndex("attribute")),
                cursor.getString(cursor.getColumnIndex("comparator")),
                cursor.getInt(cursor.getColumnIndex("challengeLevel")),
                cursor.getString(cursor.getColumnIndex("fromSceneID")),
                cursor.getString(cursor.getColumnIndex("toSceneID"))
        );

        transition._id = cursor.getString(cursor.getColumnIndex("_id"));

        return transition;
    }

    public Models.Transition[] getTransitionsForScene(String fromSceneID) {
        Cursor cursor = getReadableDatabase().query(TRANSITIONS_TABLE_NAME, TRANSITION_COLUMNS, "fromSceneID = '" + fromSceneID + "'", null, null, null, null);

        Models.Transition[] transitions = new Models.Transition[cursor.getCount()];
        while (cursor.moveToNext()) {
            Models.Transition transition = new Models.Transition(
                    cursor.getString(cursor.getColumnIndex("type")),
                    cursor.getString(cursor.getColumnIndex("verb")),
                    cursor.getString(cursor.getColumnIndex("flag")),
                    cursor.getString(cursor.getColumnIndex("attribute")),
                    cursor.getString(cursor.getColumnIndex("comparator")),
                    cursor.getInt(cursor.getColumnIndex("challengeLevel")),
                    cursor.getString(cursor.getColumnIndex("fromSceneID")),
                    cursor.getString(cursor.getColumnIndex("toSceneID"))
            );

            transition._id = cursor.getString(cursor.getColumnIndex("_id"));
            transitions[cursor.getPosition()] = transition;
        }
        return transitions;
    }

    public void deleteAll() {
        getWritableDatabase().delete(STORY_TABLE_NAME,null,null);
        getWritableDatabase().delete(CHAPTER_TABLE_NAME,null,null);
        getWritableDatabase().delete(SCENES_TABLE_NAME,null,null);
        getWritableDatabase().delete(TRANSITIONS_TABLE_NAME,null,null);
    }
}
