package com.example.lmont.adventurecreator;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by klaus_000 on 10/15/2016.
 */

public class GameLibraryFragment extends Fragment {

    public Models.Story story;

    public static Fragment newInstance(GameLibraryActivity context, int pos, float scale) {
        Bundle b = new Bundle();
        b.putInt("pos", pos);
        b.putFloat("scale", scale);
        return Fragment.instantiate(context, GameLibraryFragment.class.getName(), b);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        LinearLayout l = (LinearLayout) inflater.inflate(R.layout.fragment_game_library, container, false);

        int pos = this.getArguments().getInt("pos");
        Button tv = (Button) l.findViewById(R.id.storyCover);
        //tv.setText("Position = " + pos);
        tv.setText(story.title);

        GameLibraryLinearLayout root = (GameLibraryLinearLayout) l.findViewById(R.id.root);
        float scale = this.getArguments().getFloat("scale");
        root.setScaleBoth(scale);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChapterSelectFragment chapterDialog = new ChapterSelectFragment();
                chapterDialog.story = GameHelper.getInstance(getContext()).getFullStory(story._id);
                chapterDialog.context = getContext();

                chapterDialog.setStyle(JournalDialogFragment.STYLE_NORMAL, R.style.CustomDialog);
                chapterDialog.show(getActivity().getFragmentManager(), "Select a Chapter");
            }
        });

        return l;
    }
}
