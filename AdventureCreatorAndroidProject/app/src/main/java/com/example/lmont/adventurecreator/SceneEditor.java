package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;

public class SceneEditor extends AppCompatActivity {

    String storyId;
    String chapterId;
    String sceneId;

    RadioButton actionNodeRadioButton;
    RadioButton autoNodeRadioButton;
    RadioButton modifierNodeRadioButton;

    EditText sceneTitleEditText;
    EditText journalTextEditText;
    EditText modifiersEditText;
    EditText bodyEditText;

    String sceneTitle;
    String journalText;
    String modifiers;
    String bodyText;

    Button addTransitionButton;
    ListView transitionsListView;

    Models.Transition[] allTransitionsArray;
    ArrayList<String> allTransitionTitles;
    ArrayList<String> allTransitionIds;

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_editor);
//        TODO: Set number of transitions corespondingly, show/hide new transition button
        actionNodeRadioButton = (RadioButton) findViewById(R.id.actionNodeRadioButton);
        autoNodeRadioButton = (RadioButton) findViewById(R.id.autoNodeRadioButton);
        modifierNodeRadioButton = (RadioButton) findViewById(R.id.modifierNodeRadioButton);

//        TODO: Update db with changes to Title, Journal and Modifiers when exiting this event (or with new button?)
        sceneTitleEditText = (EditText) findViewById(R.id.sceneTitleEditText);
        journalTextEditText = (EditText) findViewById(R.id.journalTextEditText);
        modifiersEditText = (EditText) findViewById(R.id.modifiersEditText);
        bodyEditText = (EditText) findViewById(R.id.bodyEditText);

        addTransitionButton = (Button) findViewById(R.id.addTransitionButton);
        transitionsListView = (ListView) findViewById(R.id.transitionListView);

        allTransitionTitles = new ArrayList<>();
        allTransitionIds = new ArrayList<>();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTransitionTitles);

        //        TODO: Add new transition (and pull for ID?)
//        addTransitionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });

        //NO ON ITEM CLICK (Unless it's a dialogue fragment...)

        getSceneDetails();

        getAllTitlesAndIds();
        transitionsListView.setAdapter(arrayAdapter);
    }

    public void getSceneDetails(){
        Intent sceneIntent = getIntent();
        //TODO: set member strings = intent extras

        setSceneFormFields();
    }

    //TODO: Formulate this based on transition view needs... (And member variables above)
    public void getAllTitlesAndIds(){
        allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);

//        for (int i = 0; i <allTransitionsArray.length ; i++) {
//            Models.Transition transitionAtI = allTransitionsArray[i];
//            allTransitionTitles.add(transitionAtI);
//
//        }
    }

    public void readSceneFormFields(){
        sceneTitle = sceneTitleEditText.getText().toString();
        journalText = journalTextEditText.getText().toString();
        modifiers = modifiersEditText.getText().toString();
        bodyText = bodyEditText.getText().toString();
    }

    public void setSceneFormFields(){
        sceneTitleEditText.setText(sceneTitle);
        journalTextEditText.setText(journalText);
        modifiersEditText.setText(modifiers);
        bodyEditText.setText(bodyText);

    }
}
