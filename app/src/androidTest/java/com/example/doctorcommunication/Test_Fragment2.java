package com.example.doctorcommunication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Test_Fragment2 extends Fragment {

    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.test_fragment2, container, false);


        Button btn_frag1 = view.findViewById(R.id.btn_frag1);
        btn_frag1.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            Test_Fragment1 fragment1 = new Test_Fragment1();
            transaction.replace(R.id.frameLayout, fragment1);
            transaction.commit();
        });

        return view;
    }
}