package com.example.lmont.adventurecreator;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * Created by klaus_000 on 10/17/2016.
 */

public class ChapterSelectFragment extends DialogFragment {

    ListView chapterList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        getDialog().setTitle("Select a Chapter:");

        chapterList = (ListView) rootView.findViewById(R.id.chapterList);
        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(view.getContext(), GamePlayActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
