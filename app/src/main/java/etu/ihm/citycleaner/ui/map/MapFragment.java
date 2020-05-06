package etu.ihm.citycleaner.ui.map;

import android.content.Context;
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
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import etu.ihm.citycleaner.R;
import etu.ihm.citycleaner.database.TrashManager;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class MapFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private TrashManager trashManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

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
        trashManager.addTrash(new Trash(0, 2, 1, 43.615479, 7.072214, "22/04/2020", ""));
        trashManager.addTrash(new Trash(1, 1, 1, 43.61641, 7.06866, "22/04/2020", ""));

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
            googleMap = mMap;

            // For showing a move to my location button
            googleMap.setMyLocationEnabled(true);

            for(Trash t : trashManager.getTrashs()) {
                Log.e("TrashType", t.getType() + "");
                LatLng latLng = new LatLng(t.getLatitude(), t.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Polytech Nice Sophia")
                    .snippet("La petite Jaja")
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(t.getType()))
                ));


                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

            // For zooming automatically to the location of the marker

            }
        });

        return root;
    }

    private Bitmap getMarkerBitmapFromView(int n) {

        //HERE YOU CAN ADD YOUR CUSTOM VIEW
        View customMarkerView = ((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker, null);
        ImageView markerIcon = customMarkerView.findViewById(R.id.trashIcon);
        if(n == 0) {
            markerIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_greenmarker));
        } else if (n == 1) {
            markerIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_plasticmarker));
        } else if (n == 2) {
            markerIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_bulkymarker));
        } else {
            markerIcon.setImageDrawable(getResources().getDrawable(R.drawable.ic_othermarker));
        }


        //IN THIS EXAMPLE WE ARE TAKING TEXTVIEW BUT YOU CAN ALSO TAKE ANY KIND OF VIEW LIKE IMAGEVIEW, BUTTON ETC.
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