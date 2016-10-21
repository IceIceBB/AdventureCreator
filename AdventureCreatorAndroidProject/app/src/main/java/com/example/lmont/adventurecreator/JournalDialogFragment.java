package com.example.lmont.adventurecreator;

import android.app.DialogFragment;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by klaus_000 on 10/17/2016.
 */

public class JournalDialogFragment extends DialogFragment {

    TextView journal;
    Button closeJournal;
    Typeface typeface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_journal, container, false);

        getDialog().setTitle("Journal");
        journal = (TextView) rootView.findViewById(R.id.journal);
        closeJournal = (Button) rootView.findViewById(R.id.closeJournal);
        journal.setTypeface(typeface);
        journal.setTextSize(40);
        journal.setText(Player.getInstance().getJournalText());
        closeJournal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return rootView;

    }
}
