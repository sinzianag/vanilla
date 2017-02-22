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

package me.sinziana.vanilla;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.sinziana.vanilla.screens.FavoritePuns;
import me.sinziana.vanilla.screens.PunCategories;
import me.sinziana.vanilla.screens.PunOfTheDay;
import me.sinziana.vanilla.screens.RandomPun;


public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        Button computerButton = (Button) mainFragmentView.findViewById(R.id.pun_of_the_day);
        computerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToFragment(new PunOfTheDay());
            }
        });

        Button foodButton = (Button) mainFragmentView.findViewById(R.id.random_pun);
        foodButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new RandomPun());
            }
        });

        Button spaceButton = (Button) mainFragmentView.findViewById(R.id.puns_categories);
        spaceButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new PunCategories());
            }
        });

        Button elevatorButton = (Button) mainFragmentView.findViewById(R.id.favorites);
        elevatorButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new FavoritePuns());
            }
        });

        DbPunStorage punStorage = new DbPunStorage(this.getActivity());
        return mainFragmentView;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void goToFragment(Fragment newFragment) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            //TODO Don't pass null
            transaction.addToBackStack(null);
            transaction.commit();
    }

}
