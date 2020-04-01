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
    public TrashAdapter(Context context, ArrayList<Trash> trashs) {
        super(context, 0, trashs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Trash trash = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_trash, null);
        }

        TextView textViewDate = convertView.findViewById(R.id.trash_date);
        textViewDate.setText(trash.getDate().toString());

        TextView textViewAddress = convertView.findViewById(R.id.trash_address);
        textViewAddress.setText(trash.getAddress());

        TextView textViewCity = convertView.findViewById(R.id.trash_city);
        textViewCity.setText(trash.getCity());

        TextView textViewZIP = convertView.findViewById(R.id.trash_zip);
        textViewZIP.setText(trash.getZip() +"");

        TextView textViewCountry = convertView.findViewById(R.id.trash_country);
        textViewCountry.setText(trash.getCountry());
        // Return the completed view to render on screen
        return convertView;
    }
}