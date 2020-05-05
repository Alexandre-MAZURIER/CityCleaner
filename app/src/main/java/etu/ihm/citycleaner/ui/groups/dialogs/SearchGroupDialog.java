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

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.ui.groups.GroupsFragment;

public class SearchGroupDialog extends AppCompatDialogFragment {

    private EditText editText;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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

        ListView groupsList = view.findViewById(R.id.search_groups_list);

        SearchGroupListAdapter adapter = new SearchGroupListAdapter(getActivity(), R.layout.adapter_search_groups_list, GroupsFragment.groupsMock);
        groupsList.setAdapter(adapter);

        return builder.create();
    }
}
