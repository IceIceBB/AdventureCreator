package com.example.lmont.adventurecreator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

public class SceneEditor extends AppCompatActivity {

    RadioButton actionNodeRadioButton;
    RadioButton autoNodeRadioButton;
    RadioButton modifierNodeRadioButton;

    EditText sceneTitleEditText;
    EditText journalTextEditText;
    EditText modifiersEditText;
    EditText bodyEditText;

    Button addTransitionButton;

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

        //        TODO: Add new transition (and pull for ID?)
        addTransitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
