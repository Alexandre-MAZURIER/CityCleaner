package etu.ihm.citycleaner.ui.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.Arrays;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class GroupsFragment extends Fragment {

    private GroupsViewModel myTrashsViewModel;
    public static ArrayList<Group> groupsMock;
    public static GroupListAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myTrashsViewModel =
                ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_groups, container, false);

        //setting group mocks
        Trash defaultTrash = new Trash(0, 0, 0, 0 , 0, null, null);

        Group group1 = new Group(0, "Polytech Nice Sophia", new ArrayList<Trash>());
        Group group2 = new Group(1, "Les calades", new ArrayList<>(Arrays.asList(defaultTrash, defaultTrash)));
        Group group3 = new Group(1, "Antoine Facq", new ArrayList<>(Arrays.asList(defaultTrash, defaultTrash, defaultTrash, defaultTrash, defaultTrash, defaultTrash, defaultTrash, defaultTrash)));


        groupsMock = new ArrayList<>(Arrays.asList(group1, group2, group3, group1, group2, group3, group1, group2, group3, group1, group2, group3));
        //-------------------

        final TextView textView = root.findViewById(R.id.text_groups);
        textView.setText("Groupes");

        ListView groupsList = root.findViewById(R.id.groups_list);

        adapter = new GroupListAdapter(getActivity(), R.layout.adapter_groups_list, groupsMock);
        groupsList.setAdapter(adapter);

        return root;
    }
}