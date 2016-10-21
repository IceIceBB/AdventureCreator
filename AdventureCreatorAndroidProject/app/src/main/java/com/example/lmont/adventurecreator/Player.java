package com.example.lmont.adventurecreator;

import android.content.Context;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lmont on 10/16/2016.
 */

public class Player {

    private static Player instance;

    private Context context;
    private Models.Story story;
    private int chapterNum;
    private Models.Chapter chapter;
    private Models.Scene currentScene;
    private ArrayList<String> modifiers;
    private String journalText;
    private int journalCounter;
    private String username;

    public String genre = "";

    public static Player getInstance() {
        if (instance == null)
            instance = new Player();

        return instance;
    }

    private Player() {}

    public Models.Scene loadGame(Models.Story story, int chapterNum, String genre, Context context) {

        String savedInstanceSerialized = GameHelper.getInstance(context).loadGame(story._id, chapterNum);
        this.context = context;
        this.story = story;
        this.genre = genre;
        this.chapter = story.chapters[chapterNum];

        if (savedInstanceSerialized != null) {
            loadGame(savedInstanceSerialized);
            return currentScene;
        } else {
            journalText = "----------------------\n";
            journalCounter = 0;
            modifiers = new ArrayList<>();
            for (Models.Scene scene : chapter.scenes) {
                if (scene.title.equals("intro")) {
                    if (scene.flagModifiers != null)
                        addModifier(scene.flagModifiers);

                    addJournalText(scene.journalText);
                    currentScene = scene;
                    return currentScene;
                }
            }
        }
        return null;
    }

    public String getStoryTitle() {
        return story.title;
    }

    public Models.Scene getNextScene(String sceneID) {
        for(Models.Scene scene : chapter.scenes) {
            if (scene._id.equals(sceneID)) {

                if (scene.flagModifiers != null)
                    addModifier(scene.flagModifiers);

                addJournalText(scene.journalText);

                currentScene = scene;
                return scene;
            }
        }
        return null;
    }

    public Models.Scene getCurrentScene() {
        return currentScene;
    }

    public void addJournalText(String newjournalText) {
        journalCounter++;
        journalText += journalCounter + ". " + newjournalText + "\n" + "----------------------\n";
    }

    public String getJournalText() {
        return journalText;
    }

    public void addModifier(String modifier) {
        String[] stringModifiers = modifier.split(",");
        for(String stringModifier : stringModifiers)
            if (!modifiers.contains(stringModifier))
                modifiers.add(stringModifier);
    }

    public boolean checkIfPlayerHasModifier(String modifier) {
        boolean contains = false;
        String[] stringModifiers = modifier.split(",");
        for(String stringModifier : stringModifiers) {
            if (!modifiers.contains(stringModifier)) {
                return false;
            }
        }
        return true;
    }

    public void saveGame() {
        SavedInstance savedInstance = new SavedInstance(
                story._id,
                chapterNum,
                currentScene._id,
                modifiers,
                journalText,
                journalCounter
        );

        String serializedObject = "";
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(savedInstance);
            so.flush();
            serializedObject = new String(Base64.encode(bo.toByteArray(), 1));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        GameHelper.getInstance(context).saveGame(story._id, chapterNum, serializedObject);
    }

    public void loadGame(String serializedObject) {
        SavedInstance obj = null;
        try {
            byte b[] = Base64.decode(serializedObject.getBytes(), 1);
            ByteArrayInputStream bi = new ByteArrayInputStream(b);
            ObjectInputStream si = new ObjectInputStream(bi);
            obj = (SavedInstance) si.readObject();
        } catch (Exception e) {
            System.out.println(e);
        }

        journalText = obj.journalText;
        journalCounter = obj.journalNum;
        modifiers = obj.modifiers;
        for (Models.Scene scene : chapter.scenes) {
            if (scene._id.equals(obj.sceneID)) {

                if (scene.flagModifiers != null)
                    addModifier(scene.flagModifiers);

                currentScene = scene;
            }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Player{" +
                "chapter=" + chapter.title +
                ", scene=" + currentScene.title +
                ", modifiers=" + modifiers +
                ", journalText='" + journalText + '\'' +
                '}';
    }
}
