package etu.ihm.citycleaner.ui.mytrashs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.Util;

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
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null);
        }
        ImageView iconTrash = convertView.findViewById(R.id.icon);
        TextView textViewDate = convertView.findViewById(R.id.date);
        textViewDate.setText(trash.getDate());
        TextView textViewAdress = convertView.findViewById(R.id.adress);
        textViewAdress.setText(Util.getCompleteAddressString(this.getContext(), trash.getLatitude(), trash.getLongitude()));
        TextView textViewLike = convertView.findViewById(R.id.nbLike);
        textViewLike.setText(String.valueOf(trash.getNbLike()));
        if(mResource != R.layout.fragment_simplified_trash) {
            TextView textViewType = convertView.findViewById(R.id.type);

            switch (trash.getType()) {
                case 0:
                    textViewType.setText("Déchets verts");
                    iconTrash.setImageResource(R.drawable.ic_green);
                    break;
                case 1:
                    textViewType.setText("Déchets plastiques");
                    iconTrash.setImageResource(R.drawable.ic_plastic);
                    break;
                case 2:
                    textViewType.setText("Ecombrants");
                    iconTrash.setImageResource(R.drawable.ic_furniture);
                    break;
                case 3:
                    textViewType.setText("Autres");
                    iconTrash.setImageResource(R.drawable.ic_other);
                    break;
            }

            //changer pour mettre image de trash
            ImageView imageTrash = convertView.findViewById(R.id.image);
            imageTrash.setImageResource(R.drawable.ic_large_bin);
        }

        return convertView;
    }
}