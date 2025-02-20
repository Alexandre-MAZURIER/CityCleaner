package etu.ihm.citycleaner.ui.groups.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;
import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.groups.GroupsFragment;

public class SearchGroupDialog extends AppCompatDialogFragment {

    private GroupManager databaseManager;
    private EditText editText;
    private GroupsFragment parentFragment;

    private ArrayList<Group> groupArrayList;
    private ArrayList<Group> groupsToSearch;
    private SearchGroupListAdapter adapter;

    public SearchGroupDialog(GroupsFragment parentFragment) {
        this.parentFragment = parentFragment;
        this.groupsToSearch = new ArrayList<>();
        this.groupArrayList = new ArrayList<>();
        this.adapter = null;
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

        //we add a listener to change the list when typing
        this.editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String typed = s.toString();
                updateGroupsToSearch(typed);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ListView groupsList = view.findViewById(R.id.search_groups_list);
        adapter = new SearchGroupListAdapter(getActivity(),
                R.layout.adapter_search_groups_list,
                this.groupsToSearch,
                parentFragment,
                this);
        groupsList.setAdapter(adapter);

        this.updateGroupsToSearch();

        return builder.create();
    }

    public void updateGroupsToSearch() {
        //we get the group list from the database, then we display it with an adapter
        databaseManager.open();
        this.groupArrayList = databaseManager.getGroups();
        databaseManager.close();

        this.groupsToSearch.clear();

        for(Group group : this.groupArrayList) {
            if(group.isMyGroup() == 0) this.groupsToSearch.add(group);
        }

        this.adapter.notifyDataSetChanged();
    }

    public void updateGroupsToSearch(String typed) {
        //we get the group list from the database, then we display it with an adapter
        databaseManager.open();
        this.groupArrayList = databaseManager.getGroups();
        databaseManager.close();

        this.groupsToSearch.clear();

        for(Group group : this.groupArrayList) {
            if(group.isMyGroup() == 0 && group.getName().contains(typed)) {
                this.groupsToSearch.add(group);
            }
        }

        this.adapter.notifyDataSetChanged();
    }
}
