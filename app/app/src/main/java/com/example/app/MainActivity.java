package com.example.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private checkConnection broadcastReceiver ;
    private ConnectivityManager.NetworkCallback networkCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sending you to LoginActivity
        Intent intent = new Intent(MainActivity.this, MovieList.class);
        startActivity(intent);



        //creating the boradcast reciever

        broadcastReceiver = new checkConnection();
        //creating a connectivity manager
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        //creating network callbacks that let us keep track of the state of the network, since ConnecitivtyManager.CONNECTIVITY_ACTION is depricated
        networkCallback = new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                // Network is now available
                Toast.makeText(MainActivity.this, "Internet is found", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLost(Network network) {
                // Network is no longer available
                Toast.makeText(MainActivity.this, "No Internet", Toast.LENGTH_SHORT).show();
            }
        };

        //the registration of the connectivity manager (instead of usually registering the broadcast receiver
        //I added the permissions for this type of Broad cast in the manifest file
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);
        } else {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                    .build();
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        connectivityManager.unregisterNetworkCallback(networkCallback);
    }

}