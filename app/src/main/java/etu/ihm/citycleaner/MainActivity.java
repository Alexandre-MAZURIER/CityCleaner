package etu.ihm.citycleaner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Map;

import etu.ihm.citycleaner.ui.filter.DialogFilter;
import etu.ihm.citycleaner.ui.groups.dialogs.AddGroupDialog;
import etu.ihm.citycleaner.ui.map.MapFragment;


public class MainActivity extends AppCompatActivity implements AddGroupDialog.DialogListener {

    public static MapFragment mapFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED) {
        } else {
            ActivityCompat.requestPermissions(this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    100);
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder().build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        getSupportActionBar().hide();
    }

    public void addTrash(View view) {
        Intent intent = new Intent(MainActivity.this, CreateTrashActivity.class);
        startActivity(intent);
    }
    
    public void filterTrash(View view) {
//        Intent intent = new Intent(MainActivity.this, DialogFilter.class);
//        startActivity(intent);
        openDialog();

    }

    private void openDialog() {
        DialogFilter dialogFilter = new DialogFilter();
        dialogFilter.show(getSupportFragmentManager(),"DialogFilter");
    }

    //-------------------------- GROUP FRAGMENT ----------------------------------------------------

    @Override
    public void applyText(String groupName) {
        if(groupName.equals("")) return;
        //TODO add group in database
    }

    //-------------------------- END GROUP FRAGMENT -----------------------------------------------
}
