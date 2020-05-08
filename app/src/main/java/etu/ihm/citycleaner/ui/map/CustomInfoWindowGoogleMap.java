package etu.ihm.citycleaner.ui.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;
    static final String[] types = {"Déchets verts", "Déchets plastiques", "Ecombrants", "Autres"};

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

        Trash trash = (Trash) marker.getTag();

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



        adress.setText(getCompleteAddressString(trash.getLatitude(), trash.getLongitude()));

        return view;
    }

    private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd.replace(", ","\n");
    }
}