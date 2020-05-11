package etu.ihm.citycleaner;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

import etu.ihm.citycleaner.database.TrashManager;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class CreateTrashActivity extends FragmentActivity {

    private static final int CAMERA_REQUEST = 1888;
    private static final String CHANNEL_ID = "okok";
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    LocationManager lm;

    double latitude;
    double longitude;

    int garbageSize = -1;
    int garbageType = -1;

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private TrashManager databaseManager = new TrashManager(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trash);


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        this.imageView = (ImageView) this.findViewById(R.id.imageView); // Where a miniature will be displayed

        Button photoButton = (Button) this.findViewById(R.id.imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        });


        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this.locationListener);


        Button closeButton = (Button) findViewById(R.id.cancel_button);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void onSendGarbage(View v) {
        Log.d("CreateActivity", "onSendGarbage: dzdzdzzdz");

        if (this.garbageSize != -1) {
            if (this.garbageType != -1) {
                Trash trash = new Trash(0, this.garbageType, this.garbageSize, latitude, longitude, Calendar.getInstance().getTime().toString(), "https://upload.wikimedia.org/wikipedia/commons/3/33/Pedro_playing_for_Chelsea.jpg", -1);

                databaseManager.open();
                databaseManager.addTrash(trash);
                databaseManager.close();
                sendNotification();
                finish();
            }else{
                Toast.makeText(getApplicationContext(), "Sélectionner un type de déchet", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Sélectionner une taille de déchet", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendNotification() {
        NotificationManager mNotificationManager;

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(CreateTrashActivity.this, "notify_001");
        Intent ii = new Intent(CreateTrashActivity.this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(CreateTrashActivity.this, 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Vous serez informé de sa prise en charge.");
        bigText.setBigContentTitle("Votre déchet a bien été transmis !");
        bigText.setSummaryText("Confirmation");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle("Votre déchet a bien été transmis !");
        mBuilder.setContentText("Vous serez informé de sa prise en charge.");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setStyle(bigText);

        mNotificationManager =
                (NotificationManager) CreateTrashActivity.this.getSystemService(Context.NOTIFICATION_SERVICE);

// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = "123";
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }


    public void onSizeChange(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.little_bin:
                if (checked)
                    this.garbageSize = 0;
                break;
            case R.id.medium_bin:
                if (checked)
                    this.garbageSize = 1;
                break;
            case R.id.large_bin:
                if (checked)
                    this.garbageSize = 2;
                break;
        }
    }


    public void onTypeChange(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.green_garbage:
                if (checked)
                    this.garbageType = 0;
                break;
            case R.id.plastic_garbage:
                if (checked)
                    this.garbageType = 1;
                break;
            case R.id.furniture_garbage:
                if (checked)
                    this.garbageType = 2;
                break;
            case R.id.other_garbage:
                if (checked)
                    this.garbageType = 3;
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }

}
