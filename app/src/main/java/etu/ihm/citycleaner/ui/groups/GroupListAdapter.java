package etu.ihm.citycleaner.ui.groups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;

public class GroupListAdapter extends ArrayAdapter<Group> {

    private static final String TAG = "GroupListAdapter";
    private Context mContext;
    private int mResource;
    private GroupsFragment groupsFragment;

    private GroupManager databaseManager;

    public GroupListAdapter(@NonNull Context context, int resource, ArrayList<Group> groups, GroupsFragment groupsFragment) {
        super(context, resource, groups);
        this.mContext = context;
        this.mResource = resource;
        this.databaseManager = new GroupManager(getContext());
        this.groupsFragment = groupsFragment;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final int groupId = getItem(position).getId();
        final String groupName = getItem(position).getName();
        String trashNumber = Integer.toString(getItem(position).getThrashs().size());

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        TextView groupNameView = convertView.findViewById(R.id.group_name);
        TextView trashNumberView = convertView.findViewById(R.id.group_trash_number);
        Button deleteGroup = convertView.findViewById(R.id.delete_group_button);

        groupNameView.setText(groupName);
        trashNumberView.setText(trashNumber);
        deleteGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popup = new AlertDialog.Builder(getContext());
                popup.setMessage("Voulez-vous vraiment supprimer ce groupe ?");

                popup.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseManager.open();
                        databaseManager.removeGroup(groupId);
                        databaseManager.close();

                        //we update the list
                        groupsFragment.updateGroupsList();

                        Toast.makeText(getContext(), groupName + " supprimé", Toast.LENGTH_SHORT).show();

                    }
                });

                popup.setPositiveButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                popup.show();

            }
        });


        return convertView;
    }
}
