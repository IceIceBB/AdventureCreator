package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;

public class SceneEditor extends AppCompatActivity {

    String storyId;
    String chapterId;
    String sceneId;

    String actionOneId;
    String autoId;
    String modifierPassId;
    String modifierFailId;

    RadioGroup nodeTypeRadioGroup;
    int selectedRadioButtonId;
    RadioButton selectedRadioButton;
    RadioButton actionNodeRadioButton;
    RadioButton autoNodeRadioButton;
    RadioButton modifierNodeRadioButton;

    LinearLayout actionNodeViews;
    EditText actionOneVerbsEditText;
    EditText actionOneFlagsEditText;
    EditText actionOneToSceneIdEditText;
    String actionOneVerbs;
    String actionOneFlags;
    String actionOneToSceneId;

    LinearLayout autoNodeViews;
    EditText autoToSceneIdEditText;
    String autoToSceneId;

    LinearLayout modifierNodeViews;
    EditText modifierFlagsEditText;
    EditText modifierPassToSceneIdEditText;
    EditText modifierFailToSceneIdEditText;
    String modifierFlags;
    String modifierPassToSceneId;
    String modifierFailToSceneId;

    EditText sceneTitleEditText;
    EditText journalTextEditText;
    EditText modifiersEditText;
    EditText bodyEditText;

    String sceneTitle;
    String journalText;
    String modifiers;
    String bodyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_editor);

//        TODOn't: Set number of transitions corespondingly, show/hide new transition button
        nodeTypeRadioGroup = (RadioGroup) findViewById(R.id.nodeTypeRadioGroup);
        actionNodeRadioButton = (RadioButton) findViewById(R.id.actionNodeRadioButton);
        autoNodeRadioButton = (RadioButton) findViewById(R.id.autoNodeRadioButton);
        modifierNodeRadioButton = (RadioButton) findViewById(R.id.modifierNodeRadioButton);

        actionNodeViews = (LinearLayout) findViewById(R.id.actionNodeViews);
        actionOneVerbsEditText = (EditText) findViewById(R.id.actionOneVerbsEditText);
        actionOneFlagsEditText = (EditText) findViewById(R.id.actionOneFlagsEditText);
        actionOneToSceneIdEditText = (EditText) findViewById(R.id.actionOneToSceneIdEditText);

        autoNodeViews = (LinearLayout) findViewById(R.id.autoNodeViews);
        autoToSceneIdEditText = (EditText) findViewById(R.id.autoToSceneIdEditText);

        modifierNodeViews = (LinearLayout) findViewById(R.id.modifierNodeViews);
        modifierFlagsEditText = (EditText) findViewById(R.id.modifierFlagsEditText);
        modifierPassToSceneIdEditText = (EditText) findViewById(R.id.modifierPassToSceneIdEditText);
        modifierFailToSceneIdEditText = (EditText) findViewById(R.id.modifierFailToSceneIdEditText);


//        TODOne: Update db with changes to Title, Journal and Modifiers when exiting this event (or with new button?)
        sceneTitleEditText = (EditText) findViewById(R.id.sceneTitleEditText);
        journalTextEditText = (EditText) findViewById(R.id.journalTextEditText);
        modifiersEditText = (EditText) findViewById(R.id.modifiersEditText);
        bodyEditText = (EditText) findViewById(R.id.bodyEditText);

        nodeTypeRadioGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
                if (selectedRadioButton == actionNodeRadioButton) {
                    actionNodeViews.setVisibility(View.VISIBLE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == autoNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.VISIBLE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == modifierNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.VISIBLE);
                }

            }
        });

        actionNodeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
                if (selectedRadioButton == actionNodeRadioButton) {
                    actionNodeViews.setVisibility(View.VISIBLE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == autoNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.VISIBLE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == modifierNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.VISIBLE);
                }
            }
        });

        autoNodeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
                if (selectedRadioButton == actionNodeRadioButton) {
                    actionNodeViews.setVisibility(View.VISIBLE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == autoNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.VISIBLE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == modifierNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.VISIBLE);
                }
            }
        });

        modifierNodeRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
                selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
                if (selectedRadioButton == actionNodeRadioButton) {
                    actionNodeViews.setVisibility(View.VISIBLE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == autoNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.VISIBLE);
                    modifierNodeViews.setVisibility(View.GONE);
                }
                if (selectedRadioButton == modifierNodeRadioButton) {
                    actionNodeViews.setVisibility(View.GONE);
                    autoNodeViews.setVisibility(View.GONE);
                    modifierNodeViews.setVisibility(View.VISIBLE);
                }
            }
        });

        //GET INTENT EXTRAS
        getSceneDetails();
//        getTransitionIds();
        //TODOne: check associated transitions, set Radio button checked based on result

        //GET THEN SET FORM FIELDS and SET RADIO BUTTONS BASED ON FIRST TRANSITION FOR SCENE's TYPE
        getAllFormFields();

//        getAllTitlesAndIds();
    }

    public void getSceneDetails() {
        Intent sceneIntent = getIntent();
        //TODO: set member strings = intent extras
        storyId = sceneIntent.getStringExtra("storyId");
        chapterId = sceneIntent.getStringExtra("chapterId");
        sceneId = sceneIntent.getStringExtra("selectedSceneId");

        setSceneFormFields();
    }


    public void readSceneFormFields() {
        sceneTitle = sceneTitleEditText.getText().toString();
        journalText = journalTextEditText.getText().toString();
        modifiers = modifiersEditText.getText().toString();
        bodyText = bodyEditText.getText().toString();
    }

    public void readActionFormFields() {
        actionOneVerbs = actionOneVerbsEditText.getText().toString();
        actionOneFlags = actionOneFlagsEditText.getText().toString();
        actionOneToSceneId = actionOneToSceneIdEditText.getText().toString();
    }

    public void readAutoFormFields() {
        autoToSceneId = autoToSceneIdEditText.getText().toString();
    }

    public void readModifierFormFields() {
        modifierFlags = modifierFlagsEditText.getText().toString();
        modifierPassToSceneId = modifierPassToSceneIdEditText.getText().toString();
        modifierFailToSceneId = modifierFailToSceneIdEditText.getText().toString();
    }

    public void setSceneFormFields() {
        sceneTitleEditText.setText(sceneTitle);
        journalTextEditText.setText(journalText);
        modifiersEditText.setText(modifiers);
        bodyEditText.setText(bodyText);
    }

    public void setTransitionFormFields() {
        selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
        if (selectedRadioButton == actionNodeRadioButton) {
            actionOneVerbsEditText.setText(actionOneVerbs);
            actionOneFlagsEditText.setText(actionOneFlags);
            actionOneToSceneIdEditText.setText(actionOneToSceneId);
        }
        if (selectedRadioButton == autoNodeRadioButton) {
            autoToSceneIdEditText.setText(autoToSceneId);
        }
        if (selectedRadioButton == modifierNodeRadioButton) {
            modifierFlagsEditText.setText(modifierFlags);
            modifierPassToSceneIdEditText.setText(modifierPassToSceneId);
            modifierFailToSceneIdEditText.setText(modifierFailToSceneId);
        }

    }

    public void getTransitionId() {
        Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
        if (allTransitionsArray.length > 0) {
            Models.Transition firstTransition = allTransitionsArray[0];
            if (firstTransition.type.equals("action")) {
                actionOneId = firstTransition._id;
            }
            if (firstTransition.type.equals("auto")) {
                autoId = firstTransition._id;
            }
            if (firstTransition.type.equals("conditional")) {
                modifierPassId = firstTransition._id;
                modifierFailId = allTransitionsArray[1]._id;
            }
        }
    }

    //TODOne: Hard-code these fields and show/hide based on radial buttons?
    public void addTransition(Models.Transition transition) {
        GameHelper.getInstance(SceneEditor.this).addTransition(transition, new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                //TODO: Add call back functionality?
                Toast.makeText(SceneEditor.this, "Transition Created", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updateScene(Models.Scene scene) {
        GameHelper.getInstance(SceneEditor.this).updateScene(scene, new Response.Listener<Models.Scene>() {
            @Override
            public void onResponse(Models.Scene response) {
                //TODO: Add call back functionality?
                Toast.makeText(getBaseContext(), "Scene Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateTransition(Models.Transition transition) {
        GameHelper.getInstance(SceneEditor.this).updateTransition(transition, new Response.Listener<Models.Transition>() {
            @Override
            public void onResponse(Models.Transition response) {
                //TODO: Add call back functionality?
                Toast.makeText(getBaseContext(), "Transition Updated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAllFormFields() {
//        TODOne: Get a single scene as a Models.Scene object
        Models.Scene selectedScene = GameHelper.getInstance(this).getScene(sceneId);
//        Models.Scene selectedScene = allScenesArray[???];
        sceneTitle = selectedScene.title;
        journalText = selectedScene.journalText;
        modifiers = selectedScene.flagModifiers;
        bodyText = selectedScene.body;
        setSceneFormFields();

        //TODO: check associated transitions, set Radio button checked based on result, update view
        setRadioButtons(sceneId);
        Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
        selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
        if (selectedRadioButton == actionNodeRadioButton) {
            actionOneVerbs = allTransitionsArray[0].verb;
            actionOneFlags = allTransitionsArray[0].flag;
            actionOneToSceneId = allTransitionsArray[0].toSceneID;
        }
        if (selectedRadioButton == autoNodeRadioButton) {
            autoToSceneId = allTransitionsArray[0].toSceneID;
        }
        if (selectedRadioButton == modifierNodeRadioButton) {
            modifierFlags = allTransitionsArray[0].flag;
            modifierPassToSceneId = allTransitionsArray[0].toSceneID;
            modifierFailToSceneId = allTransitionsArray[1].toSceneID;
        }
        setTransitionFormFields();
    }

    public void setRadioButtons(String sceneId) {
        Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
        if (allTransitionsArray.length > 0) {
            Models.Transition firstTransition = allTransitionsArray[0];
            if (firstTransition.type.equals("action")) {
                actionNodeRadioButton.setChecked(true);
                actionNodeViews.setVisibility(View.VISIBLE);
                autoNodeViews.setVisibility(View.GONE);
                modifierNodeViews.setVisibility(View.GONE);
            }
            if (firstTransition.type.equals("auto")) {
                autoNodeRadioButton.setChecked(true);
                actionNodeViews.setVisibility(View.GONE);
                autoNodeViews.setVisibility(View.VISIBLE);
                modifierNodeViews.setVisibility(View.GONE);
            }
            if (firstTransition.type.equals("conditional")) {
                modifierNodeRadioButton.setChecked(true);
                actionNodeViews.setVisibility(View.GONE);
                autoNodeViews.setVisibility(View.GONE);
                modifierNodeViews.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onBackPressed() {

        readSceneFormFields();
        Models.Scene updatedScene = new Models.Scene(sceneTitle, journalText, modifiers, bodyText, chapterId);
        updatedScene._id = sceneId;

        //TODOne: Set if statment for all three to check which type is selected with Radio Button
        //TODOne: Set if statment to create/delete if it doesn't already exist in that type
        selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
        selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);

        if (selectedRadioButton == actionNodeRadioButton) {
            Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
            if (allTransitionsArray.length < 1) {
                readActionFormFields();
                Models.Transition newActionTransition = new Models.Transition("action", actionOneVerbs, actionOneFlags, "", "", 0, sceneId, actionOneToSceneId);
                addTransition(newActionTransition);
            } else
                readActionFormFields();
            Models.Transition updatedActionTransition = new Models.Transition("action", actionOneVerbs, actionOneFlags, "", "", 0, sceneId, actionOneToSceneId);
            //TODO: get transition id at same time as set view, check it here
            getTransitionId();
            updatedActionTransition._id = actionOneId;
            updateTransition(updatedActionTransition);
        }

        if (selectedRadioButton == autoNodeRadioButton) {
            Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
            if (allTransitionsArray.length < 1) {
                readActionFormFields();
                Models.Transition newAutoTransition = new Models.Transition("auto", "", "", "", "", 0, sceneId, autoToSceneId);
                addTransition(newAutoTransition);
            } else
                readAutoFormFields();
            Models.Transition updatedAutoTransition = new Models.Transition("auto", "", "", "", "", 0, sceneId, autoToSceneId);
            getTransitionId();
            updatedAutoTransition._id = autoId;
            updateTransition(updatedAutoTransition);
        }

        if (selectedRadioButton == modifierNodeRadioButton) {
            Models.Transition[] allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
            if (allTransitionsArray.length < 1) {
                readModifierFormFields();
                Models.Transition newModifierPassTransition = new Models.Transition("conditional", "", modifierFlags, "", "", 0, sceneId, modifierPassToSceneId);
                Models.Transition newModifierFailTransition = new Models.Transition("conditional", "", modifierFlags, "", "", 0, sceneId, modifierFailToSceneId);
                addTransition(newModifierPassTransition);
                addTransition(newModifierFailTransition);
            } else
                readModifierFormFields();
            Models.Transition updatedModifierPassTransition = new Models.Transition("conditional", "", modifierFlags, "", "", 0, sceneId, modifierPassToSceneId);
            Models.Transition updatedModifierFailTransition = new Models.Transition("conditional", "", modifierFlags, "", "", 0, sceneId, modifierFailToSceneId);
            getTransitionId();
            updatedModifierPassTransition._id = modifierPassId;
            updatedModifierFailTransition._id = modifierFailId;
            updateTransition(updatedModifierPassTransition);
            updateTransition(updatedModifierFailTransition);
        }

        updateScene(updatedScene);
        super.onBackPressed();

    }

}
