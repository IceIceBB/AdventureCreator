package com.example.lmont.adventurecreator;

import android.content.ContentValues;
import android.content.Context;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

/**
 * Created by lmont on 10/14/2016.
 */

public class GameHelper {

    private static GameHelper instance;

    private APIHelper apiHelper;
    private AdventureDBHelper dbHelper;
    private Context context;

    public static GameHelper getInstance(Context context) {
        if (instance == null)
            instance = new GameHelper(context.getApplicationContext());

        return instance;
    }

    private GameHelper(Context context) {
        this.context = context;

        apiHelper = APIHelper.getInstance(context);
        dbHelper = AdventureDBHelper.getInstance(context);
    }

    public void addStory(final Models.Story story, final Response.Listener<Models.Story> listener) {
        apiHelper.addStory(
                story,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Gson gson = new Gson();
                        Models.StoryResponse storyResponse = gson.fromJson(response.toString(), Models.StoryResponse.class);
                        ContentValues cv = new ContentValues();
                        cv.put("title", story.title);
                        cv.put("creator", story.creator);
                        cv.put("description", story.description);
                        cv.put("genre", story.genre);
                        cv.put("tags", story.tags);
                        cv.put("type", story.type);
                        cv.put("_id", storyResponse.story._id);
                        dbHelper.getWritableDatabase().insert(dbHelper.STORY_TABLE_NAME, null, cv);
                        listener.onResponse(storyResponse.story);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public void addChapter(final Models.Chapter chapter, final Response.Listener<Models.Chapter> listener) {
        apiHelper.addChapter(
                chapter,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Gson gson = new Gson();
                        Models.ChapterResponse chapterResponse = gson.fromJson(response.toString(), Models.ChapterResponse.class);
                        ContentValues cv = new ContentValues();
                        cv.put("storyID", chapter.storyID);
                        cv.put("title", chapter.title);
                        cv.put("summary", chapter.summary);
                        cv.put("type", chapter.type);
                        cv.put("_id", chapterResponse.chapter._id);
                        dbHelper.getWritableDatabase().insert(dbHelper.CHAPTER_TABLE_NAME, null, cv);
                        listener.onResponse(chapterResponse.chapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public void addScene (final Models.Scene scene, final Response.Listener<Models.Scene> listener) {
        apiHelper.addScene(
                scene,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Gson gson = new Gson();
                        Models.SceneResponse sceneResponse = gson.fromJson(response.toString(), Models.SceneResponse.class);
                        ContentValues cv = new ContentValues();
                        cv.put("chapterID", scene.chapterID);
                        cv.put("title", scene.title);
                        cv.put("body", scene.body);
                        cv.put("journalText", scene.journalText);
                        cv.put("flagModifiers", scene.flagModifiers);
                        cv.put("_id", sceneResponse.scene._id);
                        dbHelper.getWritableDatabase().insert(dbHelper.SCENES_TABLE_NAME, null, cv);
                        listener.onResponse(sceneResponse.scene);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public void addTransition(final Models.Transition transition, final Response.Listener<Models.Transition> listener) {
        apiHelper.addTransition(
                transition,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response) {
                        Gson gson = new Gson();
                        Models.TransitionResponse transitionResponse = gson.fromJson(response.toString(), Models.TransitionResponse.class);
                        ContentValues cv = new ContentValues();
                        cv.put("fromSceneID", transition.fromSceneID);
                        cv.put("toSceneID", transition.toSceneID);
                        cv.put("type", transition.type);
                        cv.put("verb", transition.verb);
                        cv.put("flag", transition.flag);
                        cv.put("attribute", transition.attribute);
                        cv.put("comparator", transition.comparator);
                        cv.put("challengeLevel", transition.challengeLevel);
                        cv.put("_id", transitionResponse.transition._id);
                        dbHelper.getWritableDatabase().insert(dbHelper.TRANSITIONS_TABLE_NAME, null, cv);
                        listener.onResponse(transitionResponse.transition);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
    }

    public void updateStory(Models.Story story, final Response.Listener<Models.Story> listener) {
        apiHelper.updateStory(story, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Models.Story storyUpdated = gson.fromJson(response.toString(), Models.Story.class);
                dbHelper.updateStory(storyUpdated);
                listener.onResponse(storyUpdated);
            }
        }, null);
    }

    public void updateChapter(Models.Chapter chapter, final Response.Listener<Models.Chapter> listener) {
        apiHelper.updateChapter(chapter, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Models.Chapter chapterUpdated = gson.fromJson(response.toString(), Models.Chapter.class);
                dbHelper.updateChapter(chapterUpdated);
                listener.onResponse(chapterUpdated);
            }
        }, null);
    }

    public void updateScene(Models.Scene scene, final Response.Listener<Models.Scene> listener) {
        apiHelper.updateScene(scene, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Models.Scene sceneUpdated = gson.fromJson(response.toString(), Models.Scene.class);
                dbHelper.updateScene(sceneUpdated);
                listener.onResponse(sceneUpdated);
            }
        }, null);
    }

    public void updateTransition(Models.Transition transition, final Response.Listener<Models.Transition> listener) {
        apiHelper.updateTransition(transition, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                Gson gson = new Gson();
                Models.Transition transitionUpdated = gson.fromJson(response.toString(), Models.Transition.class);
                dbHelper.updateTransition(transitionUpdated);
                listener.onResponse(transitionUpdated);
            }
        }, null);
    }

    public Models.Story[] getAllStories() {
        return dbHelper.getAllStories();
    }

    public Models.Chapter[] getChaptersForStory(String gameID) {
        return dbHelper.getChaptersForStory(gameID);
    }

    public Models.Scene[] getScenesForChapter(String chapterID) {
        return dbHelper.getScenesForChapter(chapterID);
    }

    public Models.Transition[] getTransitionsForScenes(String fromSceneID) {
        return dbHelper.getTransitionsForScene(fromSceneID);
    }

    public Models.Story getFullStory(String gameID) {
        Models.Story story = dbHelper.getStory(gameID);

        story.chapters = getChaptersForStory(gameID);

        for(Models.Chapter chapter : story.chapters) {
            chapter.scenes = getScenesForChapter(chapter._id);

            for(Models.Scene scene : chapter.scenes) {
                scene.transitions = getTransitionsForScenes(scene._id);
            }
        }

        return story;
    }
    public void refreshDB(Response.Listener listener) {
        dbHelper.deleteAll(false);
        ContentResolverHelper.instance.requestSync(null);
        apiHelper.downloadAll(listener, null);
    }

    public void wordSimilarityValue(String word1, String word2, final Response.Listener<Float> listener, Response.ErrorListener errorListener) {
        apiHelper.getWordComparisonValue(word1, word2, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                listener.onResponse(Float.valueOf(response.toString()));
            }
        }, errorListener);
    }

    public void saveGame(String storyID, int chapterNum, String serializedSavedInstance) {
        dbHelper.addSavedGame(storyID, chapterNum, serializedSavedInstance);
    }

    public String loadGame(String storyID, int chapterNum) {
        return dbHelper.getSavedGame(storyID, chapterNum);
    }

    public Models.Story getStory(String storyID) {
        return dbHelper.getStory(storyID);
    }

    public Models.Chapter getChapter(String chapterID) {
        return dbHelper.getChapter(chapterID);
    }

    public Models.Scene getScene(String sceneID) {
        return dbHelper.getScene(sceneID);
    }

    public Models.Transition getTransitions(String transitionID) {
        return dbHelper.getTransition(transitionID);
    }

    public void deleteTransition(final String transitionID, final Response.Listener listener) {
        apiHelper.deleteTransition(transitionID, new Response.Listener() {

            @Override
            public void onResponse(Object response) {
                dbHelper.deleteTransition(transitionID);
                listener.onResponse(response);
            }
        }, null);
    }

    public void deleteStory(final String storyID, final Response.Listener listener) {
        apiHelper.deleteStory(storyID, new Response.Listener() {

            @Override
            public void onResponse(Object response) {
                dbHelper.deleteStory(storyID);
                listener.onResponse(response);
            }
        }, null);
    }

    public void deleteChapter(final String chapterID, final Response.Listener listener) {
        apiHelper.deleteChapter(chapterID, new Response.Listener() {

            @Override
            public void onResponse(Object response) {
                dbHelper.deleteChapter(chapterID);
                listener.onResponse(response);
            }
        }, null);
    }

    public void deleteScene(final String sceneID, final Response.Listener listener) {
        apiHelper.deleteScene(sceneID, new Response.Listener() {

            @Override
            public void onResponse(Object response) {
                dbHelper.deleteScene(sceneID);
                listener.onResponse(response);
            }
        }, null);
    }
}
