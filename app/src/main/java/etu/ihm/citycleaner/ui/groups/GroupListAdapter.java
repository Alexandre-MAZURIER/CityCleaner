package etu.ihm.citycleaner.ui.groups;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import java.util.Objects;

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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final int groupId = getItem(position).getId();
        final String groupName = getItem(position).getName();
        String trashNumber = Integer.toString(getItem(position).getThrashs().size());

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        TextView groupNameView = convertView.findViewById(R.id.group_name);
        TextView trashNumberView = convertView.findViewById(R.id.group_trash_number);
        Button deleteGroup = convertView.findViewById(R.id.delete_group_button);

        groupNameView.setText(groupName);
        final View finalConvertView = convertView;
        final View finalConvertView1 = convertView;
        groupNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Group group = getItem(position);
                Group.actualGroupId = group.getId();
                Toast.makeText(getContext(), group.getName() + " défini comme groupe actuel", Toast.LENGTH_SHORT).show();
                finalConvertView.setBackgroundColor(0xFFEAE6F4);

                GroupListAdapter adapter = groupsFragment.getGroupListAdapter();

                for(int i=0; i<adapter.getCount(); i++) {
                    if (Objects.requireNonNull(adapter.getItem(i)).getId() != Group.actualGroupId) {
                        parent.getChildAt(i).setBackgroundColor(0xFFF0F0F0);
                    }
                }
            }
        });

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

                        //we remove the actual group from the user's groups list
                        Group groupToUpdate = databaseManager.getGroup(groupId);
                        groupToUpdate.setIsMyGroup(0);
                        databaseManager.updateGroup(groupToUpdate);

                        databaseManager.close();

                        //we update the list
                        groupsFragment.updateGroupsList();
                        groupsFragment.updateMyGroupsList();

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
