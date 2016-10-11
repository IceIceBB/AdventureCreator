package com.example.lmont.adventurecreator;

/**
 * Created by lmont on 10/10/2016.
 */

public class Models {

    public static class StoryResponse {
        public Story story;
    }

    public static class Story {
        public String _id;
        public String title;
        public String description;
        public String genre;
        public String type;
        public String tags;

        public Story(String title, String description, String genre, String type, String tags) {
            this.title = title;
            this.description = description;
            this.genre = genre;
            this.type = type;
            this.tags = tags;
        }
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

        public Chapter(String title, String summary, String type, String storyID) {
            this.title = title;
            this.summary = summary;
            this.type = type;
            this.storyID = storyID;
        }
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

        public Scene(String title, String journalText, String flagModifiers, String body, String chapterID) {
            this.title = title;
            this.journalText = journalText;
            this.flagModifiers = flagModifiers;
            this.body = body;
            this.chapterID = chapterID;
        }
    }

    public static class TransitionResponse {
        public Transition transition;
    }

    public static class Transition {
        String _id;
        public String type;
        public String verb;
        public String flag;
        public String attribute;
        public String comparator;
        public int challengeLevel;
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
    }
}
