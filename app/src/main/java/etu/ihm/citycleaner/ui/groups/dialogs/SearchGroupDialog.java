package etu.ihm.citycleaner.ui.groups.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.groups.GroupsFragment;

public class SearchGroupDialog extends AppCompatDialogFragment {

    private GroupManager databaseManager;
    private EditText editText;
    private GroupsFragment parentFragment;

    public SearchGroupDialog(GroupsFragment parentFragment) {
        this.parentFragment = parentFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        this.databaseManager = new GroupManager(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.search_group_dialog, null);

        builder.setView(view)
                .setTitle("Rechercher un groupe")
                .setNegativeButton("Fermer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        this.editText = view.findViewById(R.id.search_group_editText);

        //we get the group list from the database, then we display it with an adapter
        databaseManager.open();
        ArrayList<Group> groupArrayList = databaseManager.getGroups();


        ListView groupsList = view.findViewById(R.id.search_groups_list);
        SearchGroupListAdapter adapter = new SearchGroupListAdapter(getActivity(), R.layout.adapter_search_groups_list, groupArrayList);
        groupsList.setAdapter(adapter);

        return builder.create();
    }
}
