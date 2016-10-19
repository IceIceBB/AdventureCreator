package com.example.lmont.adventurecreator;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by lmont on 10/16/2016.
 */

public class Player {

    private static Player instance;

    private Models.Chapter chapter;
    private Models.Scene currentScene;
    private ArrayList<String> modifiers;
    private String journalText;
    private int journalCounter;

    public String genre = "";

    public static Player getInstance() {
        if (instance == null)
            instance = new Player();

        return instance;
    }

    private Player() {}

    public Models.Scene loadGame(Models.Chapter chapter, String genre) {
        this.genre = genre;
        this.chapter = chapter;
        journalText = "----------------------\n";
        journalCounter = 0;
        modifiers = new ArrayList<>();
        for (Models.Scene scene : chapter.scenes) {
            if (scene.title.equals("intro")) {

                if (scene.flagModifiers != null)
                    addModifier(scene.flagModifiers);

                addJournalText(scene.journalText);

                currentScene = scene;
                return scene;
            }
        }
        return null;
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
