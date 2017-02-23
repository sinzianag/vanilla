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

import me.sinziana.vanilla.DbPunStorage;
import me.sinziana.vanilla.R;

public class RandomPun extends Fragment {

    private DbPunStorage _db;
    private TextView _punView;
    private final String PUN_KEY = "punKey";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        ActionBar ab = ((AppCompatActivity)getActivity()).getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.random_pun);
            ab.invalidateOptionsMenu();
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outBundle) {
        outBundle.putString(PUN_KEY, (String)_punView.getText());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View _potd = inflater.inflate(R.layout.random_puns, container, false);
        _punView = (TextView) _potd.findViewById(R.id.pun);
        _db = new DbPunStorage(this.getActivity());

        String savedPun = "";
        if (savedInstanceState != null) {
            savedPun = savedInstanceState.getString(PUN_KEY);
        }

        if (savedPun != null && !savedPun.isEmpty()) {
            _punView.setText(savedPun);
        } else {
            setRandomPun();
        }

        Button moreButton = (Button) _potd.findViewById(R.id.more);
        moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setRandomPun();
            }
        });

        return _potd;
    }

    private void setRandomPun() {
        _punView.setText(_db.getRandomPun());
    }
}
