package com.example.lmont.adventurecreator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;

//import static com.example.lmont.adventurecreator.R.id.actionOneToSceneIdEditText;
//import static com.example.lmont.adventurecreator.R.id.autoToSceneIdEditText;
//import static com.example.lmont.adventurecreator.R.id.modifierFailToSceneIdEditText;
//import static com.example.lmont.adventurecreator.R.id.modifierPassToSceneIdEditText;
//import static edu.cmu.lti.jawjaw.pobj.POS.a;
//import static edu.cmu.lti.jawjaw.pobj.POS.v;

public class SceneEditor extends AppCompatActivity {

    //MEMBER VARIABLE STUFF
    //strings to catch intent from ChapterEditorAndSceneCreation
    String storyId;
    String chapterId;
    String sceneId;

    //scene title strings for spinner arry spinner and array spinner-id converter
    ArrayList<String> allSceneTitlesArrayList;
    ArrayList<String> allSceneIdsArrayList;

    //Refferences to find any or first Transition currently linked to the scene
    Models.Transition[] allTransitionsArray;
    Models.Transition firstTransition;

    //Node type confirmation numbers for storage and ignore based on radio button state
    String actionOneId;
    String autoId;
    String modifierPassId;
    String modifierFailId;

    //RadioGroup and aassociated children and state storage spaces
    RadioGroup nodeTypeRadioGroup;
    int selectedRadioButtonId;
    RadioButton selectedRadioButton;
    RadioButton actionNodeRadioButton;
    RadioButton autoNodeRadioButton;
    RadioButton modifierNodeRadioButton;

    //Action Node and associated views revealed by action buttone toggle state in OnClick
    LinearLayout actionNodeViews;
    EditText actionOneVerbsEditText;
    EditText actionOneFlagsEditText;
    Spinner actionOneToSceneIdSpinner;
    //    EditText actionOneToSceneIdEditText;
    String actionOneVerbs;
    String actionOneFlags;
    String actionOneToSceneId;

    //Auto Node and associated views revealed by action buttone toggle state in OnClick
    LinearLayout autoNodeViews;
    Spinner autoToSceneIdSpinner;
    //    EditText autoToSceneIdEditText;
    String autoToSceneId;

    //ModifierAkaConditional Node and associated views revealed by action buttone toggle state in OnClick
    LinearLayout modifierNodeViews;
    EditText modifierFlagsEditText;
    Spinner modifierPassToSceneIdSpinner;
    //    EditText modifierPassToSceneIdEditText;
    Spinner modifierFailToSceneIdSpinner;
    //    EditText modifierFailToSceneIdEditText;
    String modifierFlags;
    String modifierPassToSceneId;
    String modifierFailToSceneId;

    //Scene Edit/Update form fields
    EditText sceneTitleEditText;
    EditText journalTextEditText;
    EditText modifiersEditText;
    EditText bodyEditText;

    //Scene Reference/Search variable for passing
    String sceneTitle;
    String journalText;
    String modifiers;
    String bodyText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_editor);

        //Enstantiate array lists for spinner to select specific scene in chapter
        allSceneTitlesArrayList = new ArrayList<>();
        allSceneIdsArrayList = new ArrayList<>();

        //Find RadioGroup view and associated buttons to hide and show form fields and Spinners
        nodeTypeRadioGroup = (RadioGroup) findViewById(R.id.nodeTypeRadioGroup);
        actionNodeRadioButton = (RadioButton) findViewById(R.id.actionNodeRadioButton);
        autoNodeRadioButton = (RadioButton) findViewById(R.id.autoNodeRadioButton);
        modifierNodeRadioButton = (RadioButton) findViewById(R.id.modifierNodeRadioButton);

        //Find Action Node and associated form fields and Spinner to hide and show on Radio Button swap
        actionNodeViews = (LinearLayout) findViewById(R.id.actionNodeViews);
        actionOneVerbsEditText = (EditText) findViewById(R.id.actionOneVerbsEditText);
        actionOneFlagsEditText = (EditText) findViewById(R.id.actionOneFlagsEditText);
        actionOneToSceneIdSpinner = (Spinner) findViewById(R.id.actionOneToSceneIdSpinner);
//        actionOneToSceneIdEditText = (EditText) findViewById(actionOneToSceneIdEditText);

        //Find Auto Node and associated  Spinner to hide and show on Radio Button swap (formerly form field, lol)
        autoNodeViews = (LinearLayout) findViewById(R.id.autoNodeViews);
        autoToSceneIdSpinner = (Spinner) findViewById(R.id.autoToSceneIdSpinner);
//        autoToSceneIdEditText = (EditText) findViewById(autoToSceneIdEditText);

        //Find Modifier Node and associated form field and Spinners to hide and show on Radio Button swap
        modifierNodeViews = (LinearLayout) findViewById(R.id.modifierNodeViews);
        modifierFlagsEditText = (EditText) findViewById(R.id.modifierFlagsEditText);
        modifierPassToSceneIdSpinner = (Spinner) findViewById(R.id.modifierPassToSceneIdSpinner);
//        modifierPassToSceneIdEditText = (EditText) findViewById(modifierPassToSceneIdEditText);
        modifierFailToSceneIdSpinner = (Spinner) findViewById(R.id.modifierFailToSceneIdSpinner);
//        modifierFailToSceneIdEditText = (EditText) findViewById(modifierFailToSceneIdEditText);


//        TODOne: Update db with changes to Title, Journal and Modifiers when exiting this event (or with new button?)
        //Scene edit/update form fields
        sceneTitleEditText = (EditText) findViewById(R.id.sceneTitleEditText);
        journalTextEditText = (EditText) findViewById(R.id.journalTextEditText);
        modifiersEditText = (EditText) findViewById(R.id.modifiersEditText);
        bodyEditText = (EditText) findViewById(R.id.bodyEditText);

        //Detect click on any RadioButton
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

        //detect click on Action Node RadioButton, show/hide Transition views
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

        //Detect click on Auto Node RadioButton, show/hide Transition views
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

        //Detect click on Modifier Node RadioButton, show/hide Transition views
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

        getSceneDetails();
//        getTransitionIds();
        //TODOne: check associated transitions, set Radio button checked based on result

//        getAllFormFields();

//        getAllTitlesAndIds();

        Models.Scene[] allScenesArray = GameHelper.getInstance(this).getScenesForChapter(chapterId);
        getAllSceneTitlesAndIds(allScenesArray);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, allSceneTitlesArrayList);
        actionOneToSceneIdSpinner.setAdapter(spinnerAdapter);
        autoToSceneIdSpinner.setAdapter(spinnerAdapter);
        modifierPassToSceneIdSpinner.setAdapter(spinnerAdapter);
        modifierPassToSceneIdSpinner.setAdapter(spinnerAdapter);

        setTransitionFormFields();
    }

    public void getAllSceneTitlesAndIds(Models.Scene[] allScenesArray) {
        allSceneTitlesArrayList.removeAll(allSceneTitlesArrayList);
        allSceneIdsArrayList.removeAll(allSceneIdsArrayList);
        for (int i = 0; i < allScenesArray.length; i++) {
            allSceneTitlesArrayList.add(allScenesArray[i].title);
            allSceneIdsArrayList.add(allScenesArray[i]._id);
        }
    }

    //GetIntent from ChapterEditAndSceneCreation ListView for ID chain reference, get previous values for form fields and calculate previous Node Type RadioButton selection state, set Scene form fields
    public void getSceneDetails() {
        Intent sceneIntent = getIntent();
        //TODO: set member strings = intent extras
        storyId = sceneIntent.getStringExtra("storyId");
        chapterId = sceneIntent.getStringExtra("chapterId");
        sceneId = sceneIntent.getStringExtra("selectedSceneId");

        getAllFormFields();

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

        int selectedPosition = actionOneToSceneIdSpinner.getSelectedItemPosition();
        actionOneToSceneId = allSceneIdsArrayList.get(selectedPosition);
    }

    public void readAutoFormFields() {
        int selectedPosition = autoToSceneIdSpinner.getSelectedItemPosition();
        autoToSceneId = allSceneIdsArrayList.get(selectedPosition);
//        autoToSceneId = autoToSceneIdEditText.getText().toString();
    }

    public void readModifierFormFields() {
        modifierFlags = modifierFlagsEditText.getText().toString();

        int selectedPassPosition = modifierPassToSceneIdSpinner.getSelectedItemPosition();
        modifierPassToSceneId = allSceneIdsArrayList.get(selectedPassPosition);

//        int selectedFailPosition = modifierFailToSceneIdSpinner.getSelectedItemPosition();
//        modifierFailToSceneId = allSceneIdsArrayList.get(selectedFailPosition);
    }

    public void setSceneFormFields() {
        sceneTitleEditText.setText(sceneTitle);
        journalTextEditText.setText(journalText);
        modifiersEditText.setText(modifiers);
        bodyEditText.setText(bodyText);
    }

    //
    public void setTransitionFormFields() {
        allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
        if (allTransitionsArray.length > 0) {
            firstTransition = allTransitionsArray[0];
            String previousSpinnerSelectionId = firstTransition.toSceneID;

            Models.Scene[] allScenesArray = GameHelper.getInstance(this).getScenesForChapter(chapterId);
            getAllSceneTitlesAndIds(allScenesArray);
            int previousSpinnerSelectionPosition = allSceneIdsArrayList.indexOf(previousSpinnerSelectionId);

            selectedRadioButtonId = nodeTypeRadioGroup.getCheckedRadioButtonId();
            selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonId);
            if (selectedRadioButton == actionNodeRadioButton) {
                actionOneVerbsEditText.setText(actionOneVerbs);
                actionOneFlagsEditText.setText(actionOneFlags);
                actionOneToSceneIdSpinner.setSelection(previousSpinnerSelectionPosition, true);
//            actionOneToSceneIdEditText.setText(actionOneToSceneId);
            }

            if (selectedRadioButton == autoNodeRadioButton) {
                autoToSceneIdSpinner.setSelection(previousSpinnerSelectionPosition, false);
//            autoToSceneIdEditText.setText(autoToSceneId);
            }
            if (selectedRadioButton == modifierNodeRadioButton) {
                modifierFlagsEditText.setText(modifierFlags);

                modifierPassToSceneIdSpinner.setSelection(previousSpinnerSelectionPosition, false);
//            modifierPassToSceneIdEditText.setText(modifierPassToSceneId);

//            modifierFailToSceneIdSpinner.setSelection(secondPreviousSpinnerSelectionPosition);
//            modifierFailToSceneIdEditText.setText(modifierFailToSceneId);
            }
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

    //Get previous values for form fields and calculate previous RadioButton selection
    public void getAllFormFields() {
//        TODOne: Get a single scene as a Models.Scene object
        Models.Scene selectedScene = GameHelper.getInstance(this).getScene(sceneId);
//        Models.Scene selectedScene = allScenesArray[???];
        sceneTitle = selectedScene.title;
        journalText = selectedScene.journalText;
        modifiers = selectedScene.flagModifiers;
        bodyText = selectedScene.body;


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
        //TODO: Check for better space for this?
        setTransitionFormFields();
    }

    public void setRadioButtons(String sceneId) {
        allTransitionsArray = GameHelper.getInstance(this).getTransitionsForScenes(sceneId);
        if (allTransitionsArray.length > 0) {
            firstTransition = allTransitionsArray[0];

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
