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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by klaus_000 on 10/17/2016.
 */

public class ChapterSelectFragment extends DialogFragment {

    ListView chapterList;
    TextView preview;
    public Models.Story story;
    Context context;
    int chapterToLoad = -1;

    public ChapterSelectFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chapters, container, false);
        getDialog().setTitle(story.title);

        chapterList = (ListView) rootView.findViewById(R.id.chapterList);
        preview = (TextView) rootView.findViewById(R.id.chapters_preview_textview);

        String[] stringNames = new String[story.chapters.length];
        for(int x=0; x < story.chapters.length; x++) {
            stringNames[x] = story.chapters[x].title;
        }

        chapterList.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, stringNames));

        chapterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                chapterToLoad = i;
                preview.setText("Preview:\n" + story.chapters[i].summary);
            }
        });

        ((Button) rootView.findViewById(R.id.chaptersselect_cancel_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((Button) rootView.findViewById(R.id.chaptersselect_confirm_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chapterToLoad < 0) return;
                Player.getInstance().loadGame(story, chapterToLoad, story.genre, context);
                Intent intent = new Intent(v.getContext(), GamePlayActivity.class);
                intent.putExtra("genre", story.genre);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
            }
        });

        return rootView;
    }
}
