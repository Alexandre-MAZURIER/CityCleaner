package etu.ihm.citycleaner.ui.groups;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.TileOverlay;

import java.util.ArrayList;
import java.util.Objects;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.dialogs.AddGroupDialog;
import etu.ihm.citycleaner.ui.groups.dialogs.SearchGroupDialog;

public class GroupsFragment extends Fragment{

    public static ArrayList<Group> groups;

    private ArrayList<Group> myGroupsList;
    private  GroupManager databaseManager;
    private GroupListAdapter adapter;

    public GroupListAdapter getGroupListAdapter() { return this.adapter; }

    public GroupManager getDatabaseManager() { return this.databaseManager; }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        databaseManager = new GroupManager(getActivity());
        GroupsFragment.groups = new ArrayList<>();
        myGroupsList = new ArrayList<>();
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

        final ListView groupsList = root.findViewById(R.id.groups_list);
        updateGroupsList();

        //we set a custom adapter to the list
        adapter = new GroupListAdapter(getActivity(), R.layout.adapter_groups_list, this.myGroupsList, this);
        groupsList.setAdapter(adapter);

        updateMyGroupsList();

        //updating colors in list
        for (int i = 0; i <= groupsList.getLastVisiblePosition() - groupsList.getFirstVisiblePosition(); i++) {
            if (Objects.requireNonNull(adapter.getItem(i)).getId() != Group.actualGroupId) {
                groupsList.getChildAt(i).setBackgroundColor(0xFFF0F0F0);
            }
            else groupsList.setBackgroundColor(0xFFEAE6F4);
        }

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
        groups.clear();
        groups.addAll(databaseManager.getGroups());
        databaseManager.close();
    }

    public void updateMyGroupsList() {
        this.myGroupsList.clear();
        for(Group group : GroupsFragment.groups){
            if(group.isMyGroup() == 1) this.myGroupsList.add(group);
        }
        this.adapter.notifyDataSetChanged();
    }
}