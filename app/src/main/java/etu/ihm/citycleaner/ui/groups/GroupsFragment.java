package etu.ihm.citycleaner.ui.groups;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import etu.ihm.citycleaner.R;

public class GroupsFragment extends Fragment {

    private GroupsViewModel myTrashsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        myTrashsViewModel =
                ViewModelProviders.of(this).get(GroupsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_groups, container, false);
        final TextView textView = root.findViewById(R.id.text_groups);
        textView.setText("Groupes");
        return root;
    }
}