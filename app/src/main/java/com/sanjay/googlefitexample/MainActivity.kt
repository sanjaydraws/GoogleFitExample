package com.sanjay.googlefitexample

import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.Scope
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.data.DataPoint
import com.google.android.gms.fitness.request.OnDataPointListener
import com.sanjay.googlefitexample.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(),OnDataPointListener,GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    var binding:ActivityMainBinding? = null
    private val REQUEST_OAUTH = 1
    private val AUTH_PENDING = "auth_state_pending"
    private var authInProgress = false
    private var mApiClient: GoogleApiClient? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        binding?.apply {
            setContentView(this.root)
        }
        if(savedInstanceState!= null)
            authInProgress = savedInstanceState.getBoolean(AUTH_PENDING)

        mApiClient = GoogleApiClient.Builder(this)
            .addApi(Fitness.SENSORS_API)
            .addScope(Scope(Scopes.FITNESS_ACTIVITY_READ_WRITE))
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .build()


    }

    override fun onDataPoint(p0: DataPoint) {
    }

    override fun onConnected(p0: Bundle?) {
    }

    override fun onConnectionSuspended(p0: Int) {
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        if (!authInProgress) {
            try {
                authInProgress = true
                connectionResult.startResolutionForResult(this@MainActivity, REQUEST_OAUTH)
            } catch (e: SendIntentException) {
            }
        } else {
            Log.e("GoogleFit", "authInProgress")
        }
    }

    override fun onStart() {
        super.onStart()

        //after initializing google api client instance , u need to make it connect
        // to google backend
        mApiClient?.connect()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if( requestCode == REQUEST_OAUTH ) {
            authInProgress = false;
            if( resultCode == RESULT_OK ) {
                if( !mApiClient?.isConnecting!! && !mApiClient?.isConnected!!) {
                    mApiClient?.connect();
                }
            } else if( resultCode == RESULT_CANCELED ) {
                Log.e( "GoogleFit", "RESULT_CANCELED" );
            }
        } else {
            Log.e("GoogleFit", "requestCode NOT request_oauth");
        }
    }
}