package etu.ihm.citycleaner;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import etu.ihm.citycleaner.database.TrashManager;
import etu.ihm.citycleaner.ui.map.MapFragment;
import etu.ihm.citycleaner.ui.mytrashs.Trash;

public class CreateTrashActivity extends FragmentActivity {

    static final int REQUEST_TAKE_PHOTO = 1;
    private static final String CHANNEL_ID = "okok";
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    LocationManager lm;

    double latitude;
    double longitude;

    Bitmap photo;

    int garbageSize = -1;
    int garbageType = -1;

    private TrashManager databaseManager = new TrashManager(this);
    private String currentPhotoPath = null;
    private boolean pictureTaken = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_trash);

        Button contact_button = findViewById(R.id.contact_button);
        contact_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:support@citycleaner.com"));
                intent.setClassName("com.google.android.gm", "com.google.android.gm.ConversationListActivity");
                //putExtra(Intent.EXTRA_SUBJECT, subject)
                //putExtra(Intent.EXTRA_TEXT, text)
                startActivity(intent);

                 */


                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{"support@citycleaner.com"});
                email.putExtra(Intent.EXTRA_SUBJECT, "Problème rencontré en utilisant City Cleaner");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Send Mail Using :"));
            }
        });

        this.imageView = (ImageView) this.findViewById(R.id.imageView); // Where a miniature will be displayed

        Button photoButton = (Button) this.findViewById(R.id.imageButton);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                } else {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    // Ensure that there's a camera activity to handle the intent
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                        } catch (IOException ex) {
                            // Error occurred while creating the File

                        }
                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            Uri photoURI = FileProvider.getUriForFile(CreateTrashActivity.this,
                                    "etu.ihm.citycleaner.fileprovider",
                                    photoFile);
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                        }
                    }
                    setPic();
                }
            }
        });


        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        assert lm != null;
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, this.locationListener);


        Button closeButton = (Button) findViewById(R.id.cancel_button);
        closeButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


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


    public void onSendGarbage(View v) {
        Log.d("CreateActivity", "onSendGarbage: dzdzdzzdz");

        if (this.garbageSize != -1) {
            if (this.garbageType != -1) {
                String photoId = currentPhotoPath;
                LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
//                if(this.pictureTaken) {
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();

                    Trash trash = new Trash(0, this.garbageType, this.garbageSize, latitude, longitude, Util.getCurrentDate(), photoId, -1);

                    databaseManager.open();
                    databaseManager.addTrash(trash);
                    databaseManager.close();

                    openConfirmationDialog();

                    sendNotification(photoId);

                    //finish();
//                }else{
//                    Toast.makeText(getApplicationContext(), "Veuillez prendre une photo du déchet", Toast.LENGTH_SHORT).show();
//                }
            }else{
                Toast.makeText(getApplicationContext(), "Sélectionner un type de déchet", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "Sélectionner une taille de déchet", Toast.LENGTH_SHORT).show();
        }
    }

    public void sendNotification(String PhotoPath) {
        NotificationManager mNotificationManager;

        Bitmap bitmap = BitmapFactory.decodeFile(PhotoPath);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(CreateTrashActivity.this, "notify_001");
        Intent ii = new Intent(CreateTrashActivity.this.getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(CreateTrashActivity.this, 0, ii, 0);

//        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
//        bigText.bigText("Vous serez informé de sa prise en charge.");
//        bigText.setBigContentTitle("Votre déchet a bien été transmis !");
//        bigText.setSummaryText("Confirmation");

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Votre déchet a bien été transmis !");
        mBuilder.setContentText("Vous serez informé de sa prise en charge.");
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setLargeIcon(bitmap);
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null));

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


//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_LONG).show();
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
//            } else {
//                Toast.makeText(this, "Camera permission denied", Toast.LENGTH_LONG).show();
//            }
//        }
//    }


    private void setPic() {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        Log.e("d", "setPic: "+bitmap.toString());
        imageView.setImageBitmap(bitmap);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            this.pictureTaken = true;
            setPic();
        }
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
//        Log.e("FilePath", currentPhotoPath);
        return image;
    }

    private void openConfirmationDialog(){
        TrashCreatedDialog searchGroupDialog = new TrashCreatedDialog();
        searchGroupDialog.show(getSupportFragmentManager(), "confirm");
    }


    public void finish(View view) {
        finish();
    }
}
