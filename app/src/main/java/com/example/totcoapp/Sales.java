package com.example.totcoapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class Sales extends Fragment {

    String[] title = {"maize","millet"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_sales, container, false);

        TextView tt = view.findViewById(R.id.item);
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.two_line_list_item,title);
        adapter.setDropDownViewResource(android.R.layout.list_content);
        return view;
    }

}