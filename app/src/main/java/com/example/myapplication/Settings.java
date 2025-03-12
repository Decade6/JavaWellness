//settings fragment
package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Settings#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Settings extends Fragment {

    TextView nameField;
    TextView ageField;
    TextView goalField;
    Button saveProfile;



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Settings() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment settings.
     */
    // TODO: Rename and change types and number of parameters
    public static Settings newInstance(String param1, String param2) {
        Settings fragment = new Settings();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        SharedPreferences mPref = getContext().getSharedPreferences("user_profile", getContext().MODE_PRIVATE);

        View v;
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        nameField = v.findViewById(R.id.namefield);
        ageField = v.findViewById(R.id.agefield);
        goalField = v.findViewById(R.id.goalfield);
        saveProfile = v.findViewById(R.id.saveProfile);

        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = mPref.edit();
                editor.putString("name", nameField.getText().toString().trim());
                editor.putInt("age", Integer.parseInt(ageField.getText().toString().trim()));
                editor.putInt("goal", Integer.parseInt(goalField.getText().toString().trim()));
                editor.putBoolean("profile_saved", true);
                editor.commit();
                Toast.makeText(getContext(),"Profile Saved!", Toast.LENGTH_SHORT).show();

                profile profile = new profile();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.nav, profile).commit();
                //find a way to change the display
            }
        });



        return v;
    }
}