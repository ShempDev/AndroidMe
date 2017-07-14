package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by jeremy on 7/7/17.
 */

public class MasterListFragment extends Fragment {

    // Mandatory constructor
    public MasterListFragment() {
    }

    //Call back methods to get position of grid item selected.
    public OnImageClickListener mCallback;
    public interface OnImageClickListener {
        void onImageSelected(int position);
    }

    @Override //Link the call back methods with the container activity.
    public void onAttach(Context context) { //onAttach(Context) api >= 23 only.
        super.onAttach(context);
        //Make sure container activity implemented the callback interface.
        //If not; throw an exception.
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "did not implement OnImageClickListener interface.");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Inflate the master list layout fragment.
        final View rootView = inflater.inflate(R.layout.fragment_master_list, container, false);

        //Get a pointer to the gridview layout.
        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);
        //Setup an adapter to populate the gridview with ALL body part images.
        MasterListAdapter mAdapter = new MasterListAdapter(getActivity(), AndroidImageAssets.getAll());
        //apply adapter to gridview.
        gridView.setAdapter(mAdapter);

        //Set a click listener on the gridview items and pass position to the callback method.
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onImageSelected(position);
            }
        });
        return rootView;
    }
}
