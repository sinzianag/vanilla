package me.sinziana.vanilla;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.sinziana.vanilla.puns.AnimalPuns;
import me.sinziana.vanilla.puns.BatteryPuns;
import me.sinziana.vanilla.puns.ElevatorPuns;
import me.sinziana.vanilla.puns.SpacePuns;
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

        Button batteryButton = (Button) mainFragmentView.findViewById(R.id.battery);
        batteryButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new BatteryPuns());
            }
        });

        Button animalButton = (Button) mainFragmentView.findViewById(R.id.animal);
        animalButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new AnimalPuns());
            }
        });

        return mainFragmentView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void goToFragment(Fragment newFragment) {

            System.out.println("Button Pressed");
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);
            transaction.commit();
    }

}
