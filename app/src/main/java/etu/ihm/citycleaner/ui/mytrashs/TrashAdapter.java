package etu.ihm.citycleaner.ui.mytrashs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.Util;
import etu.ihm.citycleaner.database.TrashManager;

public class TrashAdapter extends ArrayAdapter<Trash> {
    private static final String TAG = "TrashsAdapter";
    private Context mContext;
    private int mResource;
    private TrashManager trashManager;

    public TrashAdapter(Context context, int resource, ArrayList<Trash> trashs) {
        super(context, resource, trashs);
        this.mContext = context;
        this.mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Trash trash = getItem(position);
        View.OnClickListener handler = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder popup = new AlertDialog.Builder(getContext());
                popup.setMessage("Voulez-vous vraiment supprimer ce déchet ?");

                popup.setNegativeButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TrashManager trashManager = new TrashManager(getContext());
                        trashManager.open();
                        trashManager.removeTrash(trash.getId());
                        clear();
                        addAll(trashManager.getTrashs());
                        notifyDataSetChanged();
                        Toast.makeText(getContext(),"Déchet supprimé", Toast.LENGTH_SHORT).show();
                    }
                });

                popup.setPositiveButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                popup.show();


            }
        };
        // Get the data item for this position

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(mResource, null);
        }


        TextView textViewDate = convertView.findViewById(R.id.date);
        textViewDate.setText(trash.getDate());

        TextView textViewAdress = convertView.findViewById(R.id.adress);
        textViewAdress.setText(Util.getCompleteAddressString(this.getContext(), trash.getLatitude(), trash.getLongitude()));

        TextView textViewLike = convertView.findViewById(R.id.nbLike);
        textViewLike.setText(String.valueOf(trash.getNbLike()));

        ImageView iconTrash = convertView.findViewById(R.id.icon);


        if(mResource != R.layout.fragment_simplified_trash) {

            ImageView imageView = convertView.findViewById(R.id.image);

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;

            // Determine how much to scale down the image
            int scaleFactor = 1;

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            Bitmap bitmap = BitmapFactory.decodeFile(trash.getImage(), bmOptions);
            imageView.setImageBitmap(bitmap);

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
            Button button = convertView.findViewById(R.id.button_remove);
            button.setOnClickListener(handler);
        }
        else {
            switch (trash.getType()) {
                case 0:
                    iconTrash.setImageResource(R.drawable.ic_green);
                    break;
                case 1:
                    iconTrash.setImageResource(R.drawable.ic_plastic);
                    break;
                case 2:
                    iconTrash.setImageResource(R.drawable.ic_furniture);
                    break;
                case 3:
                    iconTrash.setImageResource(R.drawable.ic_other);
                    break;
            }
            ImageButton imageButton = convertView.findViewById(R.id.button_remove);
            imageButton.setOnClickListener(handler);
        }




        return convertView;
    }
}