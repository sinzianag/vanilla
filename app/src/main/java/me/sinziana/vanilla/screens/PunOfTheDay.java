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

import java.text.SimpleDateFormat;
import java.util.Date;

import me.sinziana.vanilla.PunDBHelper;
import me.sinziana.vanilla.R;

public class PunOfTheDay extends Fragment {

   public PunOfTheDay() {

   }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.pun_of_the_day);
            ab.invalidateOptionsMenu();
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View _potd = inflater.inflate(R.layout.pun_of_the_day, container, false);

        TextView _textView = (TextView) _potd.findViewById(R.id.pun);
        TextView _date = (TextView) _potd.findViewById(R.id.date);
        _date.setText(new SimpleDateFormat("MM - dd - yyyy").format(new Date()));
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
