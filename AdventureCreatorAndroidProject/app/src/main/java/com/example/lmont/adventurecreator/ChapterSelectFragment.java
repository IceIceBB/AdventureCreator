package com.example.lmont.adventurecreator;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by klaus_000 on 10/17/2016.
 */

public class ChapterSelectFragment extends DialogFragment {

    ListView chapterList;
    public Models.Story story;
    Context context;

    public ChapterSelectFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        getDialog().setTitle("Select a Chapter:");

        chapterList = (ListView) rootView.findViewById(R.id.chapterList);

        String[] stringNames = new String[story.chapters.length];
        for(int x=0; x < story.chapters.length; x++) {
            stringNames[x] = story.chapters[x].title;
        }

        chapterList.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, stringNames));

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Player.getInstance().loadGame(story.chapters[i], story.genre);
                Intent intent = new Intent(view.getContext(), GamePlayActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
