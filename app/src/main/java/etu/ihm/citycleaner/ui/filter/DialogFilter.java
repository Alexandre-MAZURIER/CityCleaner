package etu.ihm.citycleaner.ui.filter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import etu.ihm.citycleaner.MainActivity;
import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.map.MapFragment;


public class DialogFilter extends AppCompatDialogFragment {

    private Group currentGroup;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        //------------------------------------------------------------------------------------------

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        GroupManager groupManager = new GroupManager(getActivity());
        groupManager.open();
        if(Group.actualGroupId != -1) {
            this.currentGroup = groupManager.getGroup(Group.actualGroupId);
        }
        View view = inflater.inflate(R.layout.activity_filter_trash, null);
        if(currentGroup != null){
            CheckBox checkBox = view.findViewById(R.id.groupCheckBox);
            checkBox.setVisibility(0);
            checkBox.setText(currentGroup.getName());
        }
        SharedPreferences share = getActivity().getSharedPreferences("checkBox", Context.MODE_PRIVATE);

        final CheckBox green = view.findViewById(R.id.green);
        final SharedPreferences.Editor editorGreen = share.edit();

        final CheckBox plastic = view.findViewById(R.id.plastic);
        final SharedPreferences.Editor editorPlastic = share.edit();

        final CheckBox furniture = view.findViewById(R.id.furniture);
        final SharedPreferences.Editor editorFurniture = share.edit();

        final CheckBox other = view.findViewById(R.id.other);
        final SharedPreferences.Editor editorOther = share.edit();

        final CheckBox checkBox = view.findViewById(R.id.groupCheckBox);
        final SharedPreferences.Editor editorCheckBox = share.edit();

        builder.setView(view);

        if(share.getBoolean("keyGreen", true))
            green.setChecked(true);
        if(share.getBoolean("keyPlastic", true))
            plastic.setChecked(true);
        if(share.getBoolean("keyFurniture", true))
            furniture.setChecked(true);
        if(share.getBoolean("keyOther", true))
            other.setChecked(true);
        if(share.getBoolean("keyCheckBox", true))
            checkBox.setChecked(true);

        final MapFragment finalMapFragment = MainActivity.mapFragment;

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                editorGreen.putBoolean("keyGreen", green.isChecked());
                editorPlastic.putBoolean("keyPlastic", plastic.isChecked());
                editorFurniture.putBoolean("keyFurniture", furniture.isChecked());
                editorOther.putBoolean("keyOther", other.isChecked());
                editorCheckBox.putBoolean("keyCheckBox", checkBox.isChecked());

                editorGreen.apply();
                editorPlastic.apply();
                editorFurniture.apply();
                editorOther.apply();
                editorCheckBox.apply();

                //we refresh the map
                finalMapFragment.refreshMap();
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
