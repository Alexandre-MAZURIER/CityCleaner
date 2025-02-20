package etu.ihm.citycleaner.ui.map;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.ArrayList;
import java.util.Date;

import etu.ihm.citycleaner.MainActivity;
import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.TrashManager;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private TrashManager trashManager;

    public void refreshMap() {
        assert getFragmentManager() != null;
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        MainActivity.mapFragment = this;

        View root = inflater.inflate(R.layout.fragment_map, container, false);


        mMapView = root.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        trashManager = new TrashManager(this.getContext());
        trashManager.open();
        /*trashManager.addTrash(new Trash(0, 0, 1, 43.581665, 7.121582, "Le 05/05/2020 à 13:50", "",-1, 15));
        trashManager.addTrash(new Trash(1, 1, 0, 43.579987, 7.122226, "Le 03/05/2020 à 11:58", "",-1, 2));
        trashManager.addTrash(new Trash(2, 2, 2, 43.578215, 7.122656, "Le 08/05/2020 à 19:26", "",-1, 54));
        trashManager.addTrash(new Trash(3, 3, 1, 43.581728, 7.126349, "Le 11/05/2020 à 23:47", "",-1, 4));
        trashManager.addTrash(new Trash(3, 2, 2, 43.580764, 7.127723, "Le 09/05/2020 à 14:12", "",-1, 18));

        trashManager.addTrash(new Trash(4, 3, 0, 43.576287, 7.123171, "Le 12/05/2020 à 08:02", "",-1, 8));
        trashManager.addTrash(new Trash(5, 2, 2, 43.579334, 7.118447, "Le 06/05/2020 à 18:16", "",-1, 9));
        trashManager.addTrash(new Trash(5, 1, 2, 43.574422, 7.124627, "Le 08/05/2020 à 21:29", "",-1, 12));
        */

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
            googleMap = mMap;
            googleMap.setMyLocationEnabled(true);
            loadTrashesFromDb();
            }
        });

        return root;
    }

    @Override
    public void onResume(){
        super.onResume();
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                loadTrashesFromDb();
            }
        });
    }

    public void loadTrashesFromDb() {
        ArrayList<Trash> res = new ArrayList<>();
        trashManager.open();
        LatLng lastTrashPos = null;
        SharedPreferences sharedPref = getActivity().getSharedPreferences("checkBox", Context.MODE_PRIVATE);

        if(sharedPref.getBoolean("keyGreen", true) && sharedPref.getBoolean("keyCheckBox", true))
            res.addAll(trashManager.getTrashesByGroupType(0));
        else if(sharedPref.getBoolean("keyGreen", true))
            res.addAll(trashManager.getTrashesByType(0));

        if(sharedPref.getBoolean("keyPlastic", true) && sharedPref.getBoolean("keyCheckBox", true))
            res.addAll(trashManager.getTrashesByGroupType(1));
        else if(sharedPref.getBoolean("keyPlastic", true))
            res.addAll(trashManager.getTrashesByType(1));

        if(sharedPref.getBoolean("keyFurniture", true) && sharedPref.getBoolean("keyCheckBox", true))
            res.addAll(trashManager.getTrashesByGroupType(2));
        else if(sharedPref.getBoolean("keyFurniture", true))
            res.addAll(trashManager.getTrashesByType(2));

        if(sharedPref.getBoolean("keyOther", true) && sharedPref.getBoolean("keyCheckBox", true))
            res.addAll(trashManager.getTrashesByGroupType(3));
        else if(sharedPref.getBoolean("keyOther", true))
            res.addAll(trashManager.getTrashesByType(3));


            for (Trash t : res) {
                CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this.getContext());
                googleMap.setInfoWindowAdapter(customInfoWindow);
                LatLng latLng = new LatLng(t.getLatitude(), t.getLongitude());
                Marker m = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(t.getType()))
                        ));
                m.setTag(t);
                m.showInfoWindow();
                lastTrashPos = latLng;
            }
            if (lastTrashPos != null) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(lastTrashPos).zoom(15).build();
                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        }


    private Bitmap getMarkerBitmapFromView(int n) {
        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerIcon = customMarkerView.findViewById(R.id.trashIcon);
        if(n == 0) {
            markerIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_greenmarker));
        } else if (n == 1) {
            markerIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_plasticmarker));
        } else if (n == 2) {
            markerIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_bulkymarker));
        } else {
            markerIcon.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_othermarker));
        }
        customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        customMarkerView.layout(0, 0, customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight());
        customMarkerView.buildDrawingCache();
        Bitmap returnedBitmap = Bitmap.createBitmap(customMarkerView.getMeasuredWidth(), customMarkerView.getMeasuredHeight(),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN);
        Drawable drawable = customMarkerView.getBackground();
        if (drawable != null)
            drawable.draw(canvas);
        customMarkerView.draw(canvas);
        return returnedBitmap;
    }


}