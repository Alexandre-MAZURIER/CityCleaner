package etu.ihm.citycleaner.ui.filter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

import etu.ihm.citycleaner.MainActivity;
import etu.ihm.citycleaner.R;


public class DialogFilter extends AppCompatDialogFragment {

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_filter_trash, null);

        Button cancel = view.findViewById(R.id.cancel_but);
        Button validate = view.findViewById(R.id.validate_button);

        SharedPreferences share = getActivity().getSharedPreferences("checkBox", Context.MODE_PRIVATE);

        final CheckBox green = view.findViewById(R.id.green);
        final SharedPreferences.Editor editorGreen = share.edit();

        CheckBox plastic = view.findViewById(R.id.plastic);
        SharedPreferences.Editor editorPlastic = share.edit();

        CheckBox furniture = view.findViewById(R.id.furniture);
        SharedPreferences.Editor editorFurnitur = share.edit();

        CheckBox other = view.findViewById(R.id.other);
        SharedPreferences.Editor editorOther = share.edit();



        builder.setView(view);

//        if(green.isChecked()) {
//            green.setChecked(true);
//            editorGreen.putBoolean("keyGreen", true);
//        }



//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });
//        validate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                editorGreen.commit();
//            }
//        });


        green.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(green.isChecked()) {
                    editorGreen.putBoolean("keyGreen", true);
                    //editorGreen.apply();
                    green.setChecked(true);
                }
                else {
                    editorGreen.putBoolean("keyGreen", false);
                    green.setChecked(false);
                }

            }
        });
        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                editorGreen.apply();

            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
    return builder.create();
    }

}
