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
import me.sinziana.vanilla.puns.ComputerPuns;
import me.sinziana.vanilla.puns.ElevatorPuns;
import me.sinziana.vanilla.puns.FoodPuns;
import me.sinziana.vanilla.puns.SpacePuns;

/**
 * Created by sinziana on 1/14/17.
 */

public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View mainFragmentView = inflater.inflate(R.layout.fragment_main, container, false);

        Button computerButton = (Button) mainFragmentView.findViewById(R.id.computer);
        computerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                goToFragment(new ComputerPuns());
            }
        });

        Button foodButton = (Button) mainFragmentView.findViewById(R.id.food);
        foodButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new FoodPuns());
            }
        });

        Button spaceButton = (Button) mainFragmentView.findViewById(R.id.space);
        spaceButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new SpacePuns());
            }
        });

        Button elevatorButton = (Button) mainFragmentView.findViewById(R.id.elevator);
        elevatorButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v) {
                goToFragment(new ElevatorPuns());
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
