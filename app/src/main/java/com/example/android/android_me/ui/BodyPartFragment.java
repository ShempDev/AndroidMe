package com.example.android.android_me.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.android_me.R;

import java.util.ArrayList;
import java.util.List;

;

/**
 * Created by jeremy on 7/1/17.
 */

public class BodyPartFragment extends Fragment {

    //Log TAG variable
    private final static String TAG = "BodyPartFragment";
    //Save state variables
    private final static String IMAGE_ID_LIST = "image_ids";
    private final static String LIST_INDEX = "list_index";
    //Variables to store and set the image list id and the list index of image.
    private List<Integer> mImageIds;
    private int mListIndex;

    //Constructor needed to instantiate the fragment class.
    public BodyPartFragment() {

    }
/*
** Inflates the fragment view to display image resources.
 */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //If a Saved State exists, set the variables to the saved data.
        if (savedInstanceState != null) {//yes we have saved data.
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
            }
        //Inflate the body parts fragment layout.
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);
        //Get the imageView from the layout.
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);
        //Set the image to display per set image id/index.
        if (mImageIds != null) {
            imageView.setImageResource(mImageIds.get(mListIndex));

            //set an onClick listener to update the body part image.
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Increment the image ID index to update view.
                    if (mListIndex < mImageIds.size() - 1) {
                        mListIndex++;
                    } else {
                        mListIndex = 0; //reset the index if at end of list.
                    }
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            Log.v("INFO", "Requested image is null");
        }

        return rootView;
    }

    public void setmImageIds(List<Integer> list) {
        mImageIds = list;
    }

    public void setmListIndex(int index) {
        mListIndex = index;
    }

    @Override
    public void onSaveInstanceState(Bundle currentState) {
        //Save the current state of the Image Id List and list index.
        currentState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        currentState.putInt(LIST_INDEX, mListIndex);
    }
}
