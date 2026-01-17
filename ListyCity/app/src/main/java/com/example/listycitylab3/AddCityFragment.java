package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);

        void editCity(City prev, City next);
    }

    private AddCityDialogListener listener;

    static AddCityFragment newInstance(City city) {
        Bundle args = new Bundle();
        args.putSerializable("city", city);
        AddCityFragment fragment = new AddCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);

        Bundle bundle = getArguments();
        if (bundle != null) {
            City cityToEdit = (City) bundle.getSerializable("city");
            editProvinceName.setText(cityToEdit.getProvince());
            editCityName.setText(cityToEdit.getName());
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add/edit a city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Ok", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    if (bundle != null) {
                        // edit city
                        City cityToEdit = (City) bundle.getSerializable("city");
                        listener.editCity(cityToEdit, new City(cityName, provinceName));
                    } else {
                        // add city
                        listener.addCity(new City(cityName, provinceName));
                    }
                })
                .create();
    }
}
