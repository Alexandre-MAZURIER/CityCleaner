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
import androidx.core.content.ContextCompat;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Date;

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
        //trashManager.addTrash(new Trash(0, 2, 1, 43.615479, 7.072214, new Date().toString(), ""));
        //trashManager.addTrash(new Trash(1, 1, 1, 43.61641, 7.06866, new Date().toString(), ""));

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
            googleMap = mMap;

            // For showing a move to my location button
            googleMap.setMyLocationEnabled(true);

            loadTrashesFromDb();

            // For zooming automatically to the location of the marker

            }
        });

        return root;
    }

    public void loadTrashesFromDb(){
        for(Trash t : trashManager.getTrashs()) {
            CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this.getContext());
            googleMap.setInfoWindowAdapter(customInfoWindow);
            Log.e("TrashType", t.getType() + "");
            LatLng latLng = new LatLng(t.getLatitude(), t.getLongitude());
            Marker m = googleMap.addMarker(new MarkerOptions()
                    .position(latLng)
                    .title("Polytech Nice Sophia")
                    .snippet("La petite Jaja")
                    .icon(BitmapDescriptorFactory.fromBitmap(getMarkerBitmapFromView(t.getType()))
                    ));
            m.setTag(t);
            m.showInfoWindow();


            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
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