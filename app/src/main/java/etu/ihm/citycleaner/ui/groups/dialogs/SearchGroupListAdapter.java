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
import etu.ihm.citycleaner.ui.groups.Group;

public class SearchGroupListAdapter extends ArrayAdapter<Group> {

    private static final String TAG = "GroupListAdapter";
    private Context mContext;
    private int mResource;

    public SearchGroupListAdapter(@NonNull Context context, int resource, ArrayList<Group> groups) {
        super(context, resource, groups);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int id = Objects.requireNonNull(getItem(position)).getId();
        final String groupName = Objects.requireNonNull(getItem(position)).getName();

        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        convertView = inflater.inflate(this.mResource, parent, false);

        TextView groupNameView = (TextView) convertView.findViewById(R.id.searched_group_name);
        Button addGroup = (Button) convertView.findViewById(R.id.add_searched_group);

        groupNameView.setText(groupName);
        addGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast feedback = Toast.makeText(getContext(), groupName + " ajouté à vos groupes", Toast.LENGTH_LONG);
                feedback.show();
            }
        });


        return convertView;
    }
}
