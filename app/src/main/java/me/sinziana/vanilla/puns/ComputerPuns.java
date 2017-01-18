package me.sinziana.vanilla.puns;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Iterator;

import me.sinziana.vanilla.PunReader;
import me.sinziana.vanilla.R;

public class ComputerPuns extends Fragment {

    public static String FILE_NAME = "computer_puns.txt";
    private View _puns;
    private Iterator<String> _punIterator;
    private TextView _textView;
    private Button _moreButton;

    public ComputerPuns() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        ab.setTitle(R.string.computer_puns);
        ab.invalidateOptionsMenu();
        _punIterator = PunReader.readPuns(FILE_NAME, getActivity()).iterator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        _puns = (View) inflater.inflate(R.layout.computer_puns, container, false);
        _textView = (TextView) _puns.findViewById(R.id.pun);
        _moreButton = (Button) _puns.findViewById(R.id.more);

        if(_punIterator.hasNext()) {
            _textView.setText((CharSequence) _punIterator.next());
        }

        _moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(_punIterator.hasNext()) {
                    _textView.setText((CharSequence) _punIterator.next());
                }
                if (!_punIterator.hasNext()) {
                    _moreButton.setEnabled(false);
                }
            }
        });
        return _puns;
    }
}

