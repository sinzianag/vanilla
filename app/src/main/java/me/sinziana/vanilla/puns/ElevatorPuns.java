/*
 * MIT License
 *
 * Copyright (c) 2017 Sinziana Gafitanu
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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

public class ElevatorPuns extends Fragment {

    private static String FILE_NAME = "elevator.txt";
    private Iterator<String> _punIterator;
    private TextView _textView;
    private Button _moreButton;

    public ElevatorPuns() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.elevator_puns);
            ab.invalidateOptionsMenu();
        }
        _punIterator = PunReader.readPuns(FILE_NAME, getActivity()).iterator();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View _puns = inflater.inflate(R.layout.elevator_puns, container, false);
        _textView = (TextView) _puns.findViewById(R.id.pun);
        _moreButton = (Button) _puns.findViewById(R.id.more);

        if(_punIterator.hasNext()) {
            _textView.setText(_punIterator.next());
        }

        _moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(_punIterator.hasNext()) {
                    _textView.setText(_punIterator.next());
                }
                if (!_punIterator.hasNext()) {
                    _moreButton.setEnabled(false);
                }
            }
        });
        return _puns;
    }
}

