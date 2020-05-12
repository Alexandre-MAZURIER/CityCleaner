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
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;


public class DialogFilter extends AppCompatDialogFragment {

    private ListView list;
    private ArrayList<Group> tabMyGroups;

    @SuppressLint("ResourceType")
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        GroupManager groupManager = new GroupManager(getActivity());
        groupManager.open();
        tabMyGroups = groupManager.getMyGroups();
        View view = inflater.inflate(R.layout.activity_filter_trash, null);
        list = view.findViewById(R.id.listGroups);
        if(tabMyGroups != null){
            FilterGroupAdapter filterGroupAdapter = new FilterGroupAdapter(getActivity(),R.layout.fragment_trash, tabMyGroups);
            list.setAdapter(filterGroupAdapter);
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



        builder.setView(view);

        if(share.getBoolean("keyGreen", true))
            green.setChecked(true);
        if(share.getBoolean("keyPlastic", true))
            plastic.setChecked(true);
        if(share.getBoolean("keyFurniture", true))
            furniture.setChecked(true);
        if(share.getBoolean("keyOther", true))
            other.setChecked(true);

        builder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                editorGreen.putBoolean("keyGreen", green.isChecked());
                editorPlastic.putBoolean("keyPlastic", plastic.isChecked());
                editorFurniture.putBoolean("keyFurniture", furniture.isChecked());
                editorOther.putBoolean("keyOther", other.isChecked());

                editorGreen.apply();
                editorPlastic.apply();
                editorFurniture.apply();
                editorOther.apply();

            }
        });

        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

            }
        });
    return builder.create();
    }

    public void MyHandler(View v) {
        CheckBox cb = (CheckBox) v;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());

        //On change la couleur
        if (cb.isChecked()) {
            //ajouter les déchets du groupe ci dessous a faire
            tabMyGroups.get(position);
        } else {
            //retirer déchet du groupe ci dessous a faire
            tabMyGroups.get(position);
        }
    }
}
