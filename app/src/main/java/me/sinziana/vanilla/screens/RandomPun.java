package me.sinziana.vanilla.screens;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import me.sinziana.vanilla.PunDBHelper;
import me.sinziana.vanilla.R;

public class RandomPun extends Fragment {

    public RandomPun() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.random_pun);
            ab.invalidateOptionsMenu();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View _potd = inflater.inflate(R.layout.random_puns, container, false);

        TextView _textView = (TextView) _potd.findViewById(R.id.pun);
        Button _moreButton = (Button) _potd.findViewById(R.id.more);

        PunDBHelper pd = new PunDBHelper(this.getContext());

        //_textView.setText(pd.getFirstEntry());

        _moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // TODO
            }
        });

        return _potd;
    }
}
