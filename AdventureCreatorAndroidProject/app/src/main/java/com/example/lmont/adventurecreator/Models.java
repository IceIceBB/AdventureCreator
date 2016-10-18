package com.example.lmont.adventurecreator;

import java.util.Arrays;

/**
 * Created by lmont on 10/10/2016.
 */

public class Models {

    public static class StoryResponseArray {
        public Story[] stories;
    }

    public static class StoryResponse {
        public Story story;
    }

    public static class Story {
        public String _id;
        public String title;
        public String creator;
        public String description;
        public String genre;
        public String type;
        public String tags;
        public Chapter[] chapters;

        public Story(String title, String creator, String description, String genre, String type, String tags) {
            this.title = title;
            this.creator = creator;
            this.description = description;
            this.genre = genre;
            this.type = type;
            this.tags = tags;
        }

        @Override
        public String toString() {
            return "Story{" +
                    "_id='" + _id + '\'' +
                    ", title='" + title + '\'' +
                    ", description='" + description + '\'' +
                    ", genre='" + genre + '\'' +
                    ", type='" + type + '\'' +
                    ", tags='" + tags + '\'' +
                    ", chapters=" + Arrays.toString(chapters) +
                    '}' + "\n";
        }
    }

    public static class ChapterResponseArray {
        public Chapter chapters[];
    }

    public static class ChapterResponse {
        public Chapter chapter;
    }

    public static class Chapter {
        public String _id;
        public String title;
        public String summary;
        public String type;
        public String storyID; // Foreign Key
        public Scene[] scenes;

        public Chapter(String title, String summary, String type, String storyID) {
            this.title = title;
            this.summary = summary;
            this.type = type;
            this.storyID = storyID;
        }

        @Override
        public String toString() {
            return "Chapter{" +
                    "_id='" + _id + '\'' +
                    ", title='" + title + '\'' +
                    ", summary='" + summary + '\'' +
                    ", type='" + type + '\'' +
                    ", storyID='" + storyID + '\'' +
                    ", scenes=" + Arrays.toString(scenes) +
                    '}' + "\n";
        }
    }

    public static class SceneResponseArray {
        public Scene[] scenes;
    }

    public static class SceneResponse {
        public Scene scene;
    }

    public static class Scene {
        public String _id;
        public String title;
        public String journalText;
        public String flagModifiers;
        public String body;
        public String chapterID; // Foreign Key
        public Transition[] transitions;

        public Scene(String title, String journalText, String flagModifiers, String body, String chapterID) {
            this.title = title;
            this.journalText = journalText;
            this.flagModifiers = flagModifiers;
            this.body = body;
            this.chapterID = chapterID;
        }

        @Override
        public String toString() {
            return "Scene{" +
                    "_id='" + _id + '\'' +
                    ", title='" + title + '\'' +
                    ", journalText='" + journalText + '\'' +
                    ", flagModifiers='" + flagModifiers + '\'' +
                    ", body='" + body + '\'' +
                    ", chapterID='" + chapterID + '\'' +
                    ", transitions=" + Arrays.toString(transitions) +
                    '}' + "\n";
        }
    }

    public static class TransitionResponseArray {
        public Transition[] transitions;
    }

    public static class TransitionResponse {
        public Transition transition;
    }

    public static class Transition {
        String _id;
        public String type; // Action, Modifier, Blank_Action
        public String verb; // User verb
        public String flag; // For modifier
        public String attribute; // Iced Boxed
        public String comparator; // Ice Boxed
        public int challengeLevel; // Ice Boxed
        public String fromSceneID; // Foreign Key
        public String toSceneID;   // Foreign Key

        public Transition(String type, String verb, String flag, String attribute, String comparator, int challengeLevel, String fromSceneID, String toSceneID) {
            this.type = type;
            this.verb = verb;
            this.flag = flag;
            this.attribute = attribute;
            this.comparator = comparator;
            this.challengeLevel = challengeLevel;
            this.fromSceneID = fromSceneID;
            this.toSceneID = toSceneID;
        }

        @Override
        public String toString() {
            return "Transition{" +
                    "_id='" + _id + '\'' +
                    ", type='" + type + '\'' +
                    ", verb='" + verb + '\'' +
                    ", flag='" + flag + '\'' +
                    ", attribute='" + attribute + '\'' +
                    ", comparator='" + comparator + '\'' +
                    ", challengeLevel=" + challengeLevel +
                    ", fromSceneID='" + fromSceneID + '\'' +
                    ", toSceneID='" + toSceneID + '\'' +
                    '}' + "\n";
        }
    }

    public static class RemoteData {
        Story[] stories;
        Chapter[] chapters;
        Scene[] scenes;
        Transition[] transitions;

        @Override
        public String toString() {
            return "RemoteData{" + "\n" +
                    "stories=" + Arrays.toString(stories) + "\n" + "\n" +
                    ", chapters=" + Arrays.toString(chapters) + "\n" + "\n" +
                    ", scenes=" + Arrays.toString(scenes) + "\n" + "\n" +
                    ", transitions=" + Arrays.toString(transitions) +
                    '}';
        }
    }
}
