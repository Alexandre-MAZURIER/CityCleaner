package etu.ihm.citycleaner.ui.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.Util;
import etu.ihm.citycleaner.database.TrashManager;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    static final String[] types = {"Déchets verts", "Déchets plastiques", "Ecombrants", "Autres"};
    TrashManager trashManager;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.trash_item, null);

        TextView date = view.findViewById(R.id.date);
        ImageView trashIcon = view.findViewById(R.id.icon);
        TextView trashType = view.findViewById(R.id.type);
        TextView adress = view.findViewById(R.id.adress);
        final TextView likes = view.findViewById(R.id.nbLike);
        Button likeIcon = view.findViewById(R.id.iconLike);

        final Trash trash = (Trash) marker.getTag();

        likeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likes.setText(2 + "");
            }
        });

        Date trashDate = new Date();
        try {
            trashDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(trash.getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy à HH:mm");


        date.setText("Le " + simpleDateFormat.format(trashDate));

        int trashTypeNb = trash.getType();
        if(trash.getType() < 4) trashType.setText(types[trash.getType()]);

        if(trashTypeNb == 0) {
            trashIcon.setImageResource(R.drawable.ic_green);
        } else if (trashTypeNb == 1) {
            trashIcon.setImageResource(R.drawable.ic_plastic);
        } else if (trashTypeNb == 2) {
            trashIcon.setImageResource(R.drawable.ic_furniture);
        } else {
            trashIcon.setImageResource(R.drawable.ic_other);
        }



        adress.setText(Util.getCompleteAddressString(context, trash.getLatitude(), trash.getLongitude()));
        likes.setText(trash.getNbLike() + "");
        return view;
    }



}