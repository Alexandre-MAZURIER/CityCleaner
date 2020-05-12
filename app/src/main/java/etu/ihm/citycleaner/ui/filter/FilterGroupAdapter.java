package etu.ihm.citycleaner.ui.filter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.Util;
import etu.ihm.citycleaner.ui.groups.Group;

public class FilterGroupAdapter extends ArrayAdapter<Group> {
    private static final String TAG = "TrashsAdapter";
    private Context mContext;
    private int mResource;

    public FilterGroupAdapter(Context context, int resource, ArrayList<Group> trashs) {
        super(context, resource, trashs);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Group group = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_detail, null);
        }
        CheckBox checkBox = convertView.findViewById(R.id.checkBoxGroup);
        checkBox.setText(group.getName());
        return convertView;
    }

}