package com.example.rossch.gv_maps;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SlideActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SlideActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SlideActivityFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_slide_activity, container, false);

        return rootView;
    }}
