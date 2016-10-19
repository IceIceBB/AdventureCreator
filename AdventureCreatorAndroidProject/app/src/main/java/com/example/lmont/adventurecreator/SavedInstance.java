package com.example.lmont.adventurecreator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by lmont on 10/18/2016.
 */

public class SavedInstance implements Serializable{

    String storyID;
    int chapterNum;
    String sceneID;
    ArrayList<String> modifiers;
    String journalText;
    int journalNum;

    public SavedInstance(String storyID, int chapterNum, String sceneID, ArrayList<String> modifiers, String journalText, int journalNum) {
        this.storyID = storyID;
        this.chapterNum = chapterNum;
        this.sceneID = sceneID;
        this.modifiers = modifiers;
        this.journalText = journalText;
        this.journalNum = journalNum;
    }

    @Override
    public String toString() {
        return "SavedInstance{" +
                "storyID='" + storyID + '\'' +
                ", chapterNum=" + chapterNum +
                ", sceneID='" + sceneID + '\'' +
                ", modifiers=" + modifiers +
                ", journalText='" + journalText + '\'' +
                ", journalNum=" + journalNum +
                '}';
    }
}
