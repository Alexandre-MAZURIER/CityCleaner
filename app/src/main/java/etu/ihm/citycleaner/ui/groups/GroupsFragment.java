package etu.ihm.citycleaner.ui.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.dialogs.AddGroupDialog;
import etu.ihm.citycleaner.ui.groups.dialogs.SearchGroupDialog;

public class GroupsFragment extends Fragment{

    private GroupManager databaseManager;
    private ListView groupsList;
    public static GroupListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        this.databaseManager = new GroupManager(getActivity());
        //setting group mocks

        //------------------- MENU -----------------------------------------------------------------
        final TextView textView = root.findViewById(R.id.text_groups);
        textView.setText("Groupes");

        final Button searchGroupButton = root.findViewById(R.id.search_group_button);
        searchGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchGroupDialog();
            }
        });

        final Button addGroupButton = root.findViewById(R.id.add_group_button);
        addGroupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddGroupDialog();
            }
        });

        //------------------ FIN MENU --------------------------------------------------------------

        groupsList = root.findViewById(R.id.groups_list);
        updateGroupsList();

        return root;
    }

    private void openAddGroupDialog() {
        AddGroupDialog addGroupDialog = new AddGroupDialog(this);
        addGroupDialog.show(getChildFragmentManager(), "add group");
    }

    private void openSearchGroupDialog() {
        SearchGroupDialog searchGroupDialog = new SearchGroupDialog(this);
        searchGroupDialog.show(getChildFragmentManager(), "search group");
    }

    public void updateGroupsList() {
        //we get the group list from the database, then we display it with an adapter
        databaseManager.open();
        ArrayList<Group> groupArrayList = databaseManager.getGroups();
        databaseManager.close();

        //we set a custom adapter to the list
        adapter = new GroupListAdapter(getActivity(), R.layout.adapter_groups_list, groupArrayList, this);
        groupsList.setAdapter(adapter);
    }
}