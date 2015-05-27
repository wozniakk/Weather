package com.example.kamil.weather;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BasicInformationFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BasicInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BasicInformationFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BasicInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BasicInformationFragment newInstance(String param1, String param2) {
        BasicInformationFragment fragment = new BasicInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public BasicInformationFragment() {
        // Required empty public constructor
    }

    private static TextView textDate;
    private static TextView textDescription;
    private static TextView textTemperature;
    private static TextView textPressure;
    private static TextView textLocation;
    private static TextView textCoordinates;
    private static View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.basic_information, container, false);

        textDate = (TextView) view.findViewById(R.id.textDate);
        textDescription = (TextView) view.findViewById(R.id.textDescription);
        textTemperature = (TextView) view.findViewById(R.id.textTemperature);
        textPressure = (TextView) view.findViewById(R.id.textPressure);
        textLocation = (TextView) view.findViewById(R.id.textLocation);
        textCoordinates = (TextView) view.findViewById(R.id.textCoordinates);

        return refreshView();

    }

    public static View refreshView() {
        if (MainActivity.refreshed) {
            textDate.setText( MainActivity.currentBasic.date );
            textDescription.setText( MainActivity.currentBasic.description );
            textLocation.setText( MainActivity.currentBasic.location);
            textCoordinates.setText( MainActivity.currentBasic.latitude + ", " + MainActivity.currentBasic.longtitude);
            if (MainActivity.UNITS == "c") {
                textTemperature.setText( MainActivity.currentBasic.temperature + "° C" );
                textPressure.setText( MainActivity.currentBasic.pressure + " hPa" );
            } else {
                textTemperature.setText( MainActivity.currentBasic.temperature + "° F" );
                textPressure.setText( MainActivity.currentBasic.pressure + " in" );
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
