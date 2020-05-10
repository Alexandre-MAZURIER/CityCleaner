package etu.ihm.citycleaner.ui.groups.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;


import java.util.ArrayList;
import java.util.Objects;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.GroupManager;
import etu.ihm.citycleaner.ui.groups.Group;
import etu.ihm.citycleaner.ui.groups.GroupsFragment;

public class SearchGroupListAdapter extends ArrayAdapter<Group> {

    private GroupsFragment parentFragment;

    private static final String TAG = "GroupListAdapter";
    private Context mContext;
    private int mResource;

    public SearchGroupListAdapter(@NonNull Context context, int resource, ArrayList<Group> groups, GroupsFragment parentFragment) {
        super(context, resource, groups);
        this.mContext = context;
        this.mResource = resource;
        this.parentFragment = parentFragment;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Group currentGroup = Objects.requireNonNull(getItem(position));
        int id = currentGroup.getId();
        final String groupName = currentGroup.getName();

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        TextView groupNameView = convertView.findViewById(R.id.searched_group_name);
        Button addGroup = convertView.findViewById(R.id.add_searched_group);

        groupNameView.setText(groupName);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupManager databaseManager = parentFragment.getDatabaseManager();
                databaseManager.open();

                //we set the group in the users' groups list
                Group groupToUpdate = databaseManager.getGroup(currentGroup.getId());
                groupToUpdate.setIsMyGroup(1);

                databaseManager.updateGroup(groupToUpdate);
                databaseManager.close();

                //we update the list
                parentFragment.updateGroupsList();
                parentFragment.updateMyGroupsList();

                //we send feedback to the user
                Toast feedback = Toast.makeText(getContext(), groupName + " ajouté à vos groupes", Toast.LENGTH_LONG);
                feedback.show();
            }
        });


        return convertView;
    }
}
