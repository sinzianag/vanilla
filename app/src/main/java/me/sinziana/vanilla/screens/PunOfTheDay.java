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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import me.sinziana.vanilla.DbPunStorage;
import me.sinziana.vanilla.PunUtils;
import me.sinziana.vanilla.R;

public class PunOfTheDay extends Fragment {

    private int _punOffset;
    private TextView _pun;
    private TextView _date;

    private Button _olderButton;
    private Button _newerButton;

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

        _punOffset = 0;

        View _potd = inflater.inflate(R.layout.pun_of_the_day, container, false);

        _pun = (TextView) _potd.findViewById(R.id.pun);

        _date = (TextView) _potd.findViewById(R.id.date);


        _olderButton = (Button) _potd.findViewById(R.id.older);
        _newerButton = (Button) _potd.findViewById(R.id.newer);


        _olderButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateWithOlderPun();
            }
        });

        _newerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateWithNewerPun();
            }
        });

        updateUI();

        return _potd;
    }

    private void updateUI() {
        setCurrentPun();
        setCurrentDate();
        updateButtonVisibility();
    }

    private void updateWithOlderPun() {
        if((PunUtils.daysSinceLaunch() + _punOffset) > 0) {
            _punOffset--;
            updateUI();
        }
    }

    private void updateWithNewerPun() {
        if (_punOffset < 0) {
            _punOffset++;
            updateUI();
        }
    }

    private void updateButtonVisibility() {
        if ((PunUtils.daysSinceLaunch() + _punOffset) <= 0) {
            _olderButton.setEnabled(false);
        } else {
            _olderButton.setEnabled(true);
        }

        if (_punOffset == 0) {
            _newerButton.setEnabled(false);
        } else {
            _newerButton.setEnabled(true);
        }
    }

    private void setCurrentPun() {
            DbPunStorage db = new DbPunStorage(this.getActivity());
            _pun.setText(db.getPunForDayIndex(PunUtils.daysSinceLaunch() +_punOffset));
    }

    private void setCurrentDate() {
        _date.setText(new SimpleDateFormat("MM - dd - yyyy").format(PunUtils.getDateWithOffset(_punOffset)));
    }

}
