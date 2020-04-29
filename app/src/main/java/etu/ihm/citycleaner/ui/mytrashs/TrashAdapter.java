package etu.ihm.citycleaner.ui.mytrashs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;

public class TrashAdapter extends ArrayAdapter<Trash> {

    private static final String TAG = "TrashsAdapter";
    private Context mContext;
    private int mResource;

    public TrashAdapter(Context context, int resource, ArrayList<Trash> trashs) {
        super(context, resource, trashs);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trash trash = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_trash, null);
        }

        TextView textViewType = convertView.findViewById(R.id.trash_type);
        switch (trash.getType()){
            case 0:
                textViewType.setText("Déchet type 1");
                break;
            case 1:
                textViewType.setText("Déchet type 2");
                break;
            case 2:
                textViewType.setText("Déchet type 3");
                break;
            case 3:
                textViewType.setText("Déchet type 4");
                break;
        }
        
        TextView textViewDate = convertView.findViewById(R.id.trash_date);
        textViewDate.setText(trash.getDate());


        // Return the completed view to render on screen
        return convertView;
    }
}