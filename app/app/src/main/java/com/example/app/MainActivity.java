package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.widget.Toast;

import com.example.app.Retrofit.PreferenceHandler;


public class MainActivity extends AppCompatActivity {

    PreferenceHandler prefHandler;
    DatabaseHandler dbHandler;
    private ConnectivityManager.NetworkCallback networkCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefHandler = new PreferenceHandler(this);

//        prefHandler.setEmail("none");

        //creating a connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //check if network is available
        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

        if (isConnected) {
            if (prefHandler.getEmail().equals("none")) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                startActivity(intent);
            }
        } else {
            Intent intent = new Intent(MainActivity.this, No_Internet_Activity.class);
            startActivity(intent);
        }

        //creating network callbacks that let us keep track of the state of the network, since ConnecitivtyManager.CONNECTIVITY_ACTION is depricated
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // Network is now available
                if (prefHandler.getEmail().equals("none")) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                    startActivity(intent);
                }

            }


            @Override
            public void onLost(Network network) {
                // Network is no longer available
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
            }
        };

        //the registration of the connectivity manager (instead of usually registering the broadcast receiver
        //I added the permissions for this type of Broad cast in the manifest file
        connectivityManager.registerDefaultNetworkCallback(networkCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

}