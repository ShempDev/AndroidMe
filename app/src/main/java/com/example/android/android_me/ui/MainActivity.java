package com.example.android.android_me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    //Variables to hold the index value of the body part selected in master list.
    private int headIndex;
    private int bodyIndex;
    private int legIndex;
    //Variable to hold true/false if in two-pane display mode.
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //If androidMe's linearLayout exists then in two-pane mode.
        if (getResources().getBoolean(R.bool.two_panes)) {
            mTwoPane = true;
            //Since we are in two-pane mode, need to inflate the fragment.
            if (savedInstanceState == null) { //Only create new fragments if none exist.
                //Create a BodyPartFragments and set images for each body segment.
                BodyPartFragment headFragment = new BodyPartFragment();
                headFragment.setmImageIds(AndroidImageAssets.getHeads());
                //headFragment.setmListIndex(headIndex);
                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setmImageIds(AndroidImageAssets.getBodies());
                //bodyFragment.setmListIndex(bodyIndex);
                BodyPartFragment legFragment = new BodyPartFragment();
                legFragment.setmImageIds(AndroidImageAssets.getLegs());
                //legFragment.setmListIndex(legIndex);

                FragmentManager fragmentManager = getSupportFragmentManager();
                // Fragment transactions to add the body part containers for UI display.
                fragmentManager.beginTransaction().add(R.id.head_container, headFragment).commit();
                fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();
                fragmentManager.beginTransaction().add(R.id.leg_container, legFragment).commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    public void onImageSelected(int position) {
        //Determine the body part selected and the part index.
        // divide by twelve gives 0 - 2 for body part; remainder 12 gives 0 - 11 for part index.
        switch (position/12) {
            case 0: headIndex = position % 12;
                break;
            case 1: bodyIndex = position % 12;
                break;
            case 2: legIndex = position % 12;
                break;
            default: break;
        }
        //Only call the fragment intent when not in two pane mode.
        if (!mTwoPane) {
            //Bundle up the body part indexes to send to the AndroidMe fragment.
            Bundle bundle = new Bundle();
            bundle.putInt("headIndex", headIndex);
            bundle.putInt("bodyIndex", bodyIndex);
            bundle.putInt("legIndex", legIndex);
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        } else {
            //Update the android me body part image.
            BodyPartFragment newBodyPart = new BodyPartFragment();
            switch (position/12) {
                case 0: //head clicked
                    newBodyPart.setmImageIds(AndroidImageAssets.getHeads());
                    newBodyPart.setmListIndex(headIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newBodyPart)
                            .commit();
                    break;
                case 1: //new body selected
                    newBodyPart.setmImageIds(AndroidImageAssets.getBodies());
                    newBodyPart.setmListIndex(bodyIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newBodyPart)
                            .commit();
                    break;
                case 2: //new leg selected
                    newBodyPart.setmImageIds(AndroidImageAssets.getLegs());
                    newBodyPart.setmListIndex(legIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newBodyPart)
                            .commit();
                    break;
                default: break;
            }
        }
    }

}
