package dtsquared.nwschoolsafewalk;

import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.kml.KmlLayer;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

import dtsquared.nwschoolsafewalk.database.DatabaseHelper;

public class Maps extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        LocationListener, ResultCallback<Status> {

    public static final int LOCATION_ACCESS_CODE = 1;
    private static final int REQUEST_RESOLVE_ERROR = 1001;
    private static final String DIALOG_ERROR = "dialog_error";
    private static final LatLng RICHARDMcBRIDE = new LatLng(49.226546, -122.899537);
    private static final LatLng JIBC = new LatLng(49.222264, -122.910133);
    private static final LatLng FWHOWAY = new LatLng(49.226056, -122.912388);
    private static final LatLng QUEEN_ELIZABETH = new LatLng(49.257411, -123.197517);
    private static final LatLng QUEENSBOROUGH = new LatLng(49.186294, -122.940922);
    private static final LatLng CONNAUGHT_HEIGHTS = new LatLng(49.202614, -122.954815);
    private static final LatLng LORD_TWEEDSMUIR = new LatLng(49.205670, -122.942554);
    private static final LatLng FRASER_RIVER = new LatLng(49.204704, -122.916450);
    private static final LatLng DOUGLAS = new LatLng(49.203568, -122.912689);
    private static final LatLng LORD_KELVIN = new LatLng(49.210974, -122.930177);
    private static final LatLng GLENBROOK = new LatLng(42.043290, -72.550925);
    private static final LatLng HUME_PARK = new LatLng(49.232917, -122.890297);
    private static final LatLng NW = new LatLng(49.215781, -122.928791);
    private static final LatLng HERBERT_SPENCER = new LatLng(49.217382, -122.913695);
    private static final LatLng QAYQAYT = new LatLng(49.208025, -122.905131);
    private static final LatLng TEMP = new LatLng(49.248464, -123.001213);

    private static final long GEO_DURATION = 60 * 60 * 1000;
    private static final String GEOFENCE_REQ_ID = "NW School Routes";
    private static final float GEOFENCE_RADIUS = 300.0f; // in meters
    private DatabaseHelper helper;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrentLocation;
    LocationRequest mLocationRequest;
    private boolean resolvingError;
    private Marker geoFenceMarker;
    private PendingIntent geoFencePendingIntent;
    private final int GEOFENCE_REQ_CODE = 0;
    private Circle geoFenceLimits;

    static Intent makeNotificationIntent(Context geofenceService, String msg) {
        Log.d("",msg);
        return new Intent(geofenceService, Maps.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        checkLocationPermission();

        helper = DatabaseHelper.getInstance(this);
        helper.openDatabaseForReading(this);

        // Map component to place the map.
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        buildGoogleApiClient();
    }

    public void checkLocationPermission(){
        // Check permissions
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check SDK version
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Request permission
                requestPermissions(new String[] {
                                android.Manifest.permission.ACCESS_FINE_LOCATION },
                        LOCATION_ACCESS_CODE);
            }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10 * 1000);        // 10 sec
        mLocationRequest.setFastestInterval(1000);      // 1 sec
        // Obtain location (high accuracy), requires more power and time
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // Check permissions before calling APIs
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, this);
        }
        Log.d("!!!!!!!!!!", "works onconnect");

        dtsquared.nwschoolsafewalk.database.schema.Marker marker = helper.getMarkerByGeofence(true);

        float lat = Float.parseFloat(marker.getLatitude());
        float lng = Float.parseFloat(marker.getLongitude());
        LatLng markerLatLng = new LatLng(lat, lng);

        geofenceMarker(markerLatLng);
        /*geofenceMarker(RICHARDMcBRIDE);
        geofenceMarker(JIBC);
        geofenceMarker(FWHOWAY);
        geofenceMarker(QUEEN_ELIZABETH);
        geofenceMarker(QUEENSBOROUGH);
        geofenceMarker(CONNAUGHT_HEIGHTS);
        geofenceMarker(LORD_TWEEDSMUIR);
        geofenceMarker(FRASER_RIVER);
        geofenceMarker(DOUGLAS);
        geofenceMarker(LORD_KELVIN);
        geofenceMarker(GLENBROOK);
        geofenceMarker(HUME_PARK);
        geofenceMarker(NW);
        geofenceMarker(HERBERT_SPENCER);
        geofenceMarker(QAYQAYT); */
        //geofenceMarker(TEMP);
        startGeofence();
    }

    // Callback for the result from requesting permissions
    @Override
    public void onRequestPermissionsResult(int    requestCode,
                                           String permissions[],
                                           int[]  grantResults) {
        switch (requestCode) {
            case LOCATION_ACCESS_CODE: {
                // If request is cancelled, the result arrays are empty
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (mGoogleApiClient == null) {
                        buildGoogleApiClient();
                    }
                } else {
                    Toast.makeText(this, "Allow location access to use the app",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        UiSettings settings = mMap.getUiSettings();
        /*Marker tmp = googleMap.addMarker(new MarkerOptions()
                .position(TEMP)
                .title("Place"));
        tmp.setTag(0);*/


        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
                settings.setZoomControlsEnabled(true);
            }
        }

        retrieveKMLFiles();
    }

    // Create a Location Marker
    /*private void markerLocation(LatLng latLng) {
        Log.i("", "markerLocation("+latLng+")");
        String title = latLng.latitude + ", " + latLng.longitude;
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .title(title);
        if ( mMap!=null ) {
            // Remove the anterior marker
            if ( locationMarker != null )
                locationMarker.remove();
            locationMarker = mMap.addMarker(markerOptions);
            float zoom = 14f;
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoom);
            mMap.animateCamera(cameraUpdate);
        }
    }*/

    // Create a marker for the geofence creation
    private void geofenceMarker(LatLng latLng) {
        Log.i("", "markerForGeofence(" + latLng + ")");
        String title = latLng.latitude + ", " + latLng.longitude;

        // Define marker options
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                .title(title);

        if (mMap!=null) {
            // Remove last geoFenceMarker
            if (geoFenceMarker != null)
                geoFenceMarker.remove();

            geoFenceMarker = mMap.addMarker(markerOptions);
        }
    }

    // Create a Geofence
    private Geofence createGeofence(LatLng latLng, float radius) {
        Log.d("", "createGeofence");
        return new Geofence.Builder()
                .setRequestId(GEOFENCE_REQ_ID)
                .setCircularRegion(latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration(GEO_DURATION)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
                .build();
    }

    // Create a Geofence Request
    private GeofencingRequest createGeofenceRequest(Geofence geofence) {
        Log.d("Request", "createGeofenceRequest");
        return new GeofencingRequest.Builder()
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
                .addGeofence(geofence)
                .build();
    }

    private PendingIntent createGeofencePendingIntent() {
        Log.d("", "createGeofencePendingIntent");
        if (geoFencePendingIntent != null)
            return geoFencePendingIntent;

        Intent intent = new Intent(this, GeofenceTransitionsIntentService.class);
        return PendingIntent.getService(this, GEOFENCE_REQ_CODE, intent,
                PendingIntent.FLAG_UPDATE_CURRENT );
    }

    // Add the created GeofenceRequest to the device's monitoring list
    private void addGeofence(GeofencingRequest request) {
        Log.d("", "addGeofence");
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("!!!!!!", "in");
            LocationServices.GeofencingApi.addGeofences(
                    mGoogleApiClient,
                    request,
                    createGeofencePendingIntent()
            ).setResultCallback(this);
        }
    }

    // Draw Geofence circle on GoogleMap
    private void drawGeofence() {
        Log.d("Draw Geofence", "drawGeofence()");

        if (geoFenceLimits != null)
            geoFenceLimits.remove();

        CircleOptions circleOptions = new CircleOptions()
                .center(geoFenceMarker.getPosition())
                .strokeColor(Color.argb(50, 70,70,70))
                .fillColor( Color.argb(100, 150,150,150) )
                .radius(GEOFENCE_RADIUS);
        geoFenceLimits = mMap.addCircle(circleOptions);
    }

    // Start Geofence creation process
    private void startGeofence() {
        Log.i("Start geofence", "startGeofence()");
        if(geoFenceMarker != null) {
            Geofence geofence = createGeofence(geoFenceMarker.getPosition(), GEOFENCE_RADIUS);
            GeofencingRequest geofenceRequest = createGeofenceRequest(geofence);
            addGeofence(geofenceRequest);
        } else {
            Log.e("Geofence marker null", "Geofence marker is null");
        }
    }





    private void retrieveKMLFiles() {
        // School sites
        try {
            KmlLayer schoolLayer = new KmlLayer(mMap, R.raw.schools, getApplicationContext());
            schoolLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


/*
        // School walking routes
        try {
            KmlLayer routesLayer = new KmlLayer(mMap, R.raw.walkingroutes, getApplicationContext());
            routesLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        // Fire and rescue services buildings
        try {
            KmlLayer fireRescueLayer = new KmlLayer(mMap, R.raw.firerescue, getApplicationContext());
            fireRescueLayer.addLayerToMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        if (mCurrentLocation != null) {
            mCurrentLocation.remove();
        }

        // Obtain current coordinates
        LatLng coordinates = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(coordinates);
        options.title("Hmm");
        // Marker colour
        options.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        mCurrentLocation = mMap.addMarker(options);

        // Move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        // Zoom level
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
        if(mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if(resolvingError) {
            return;
        }

        if(result.hasResolution()) {
            try {
                resolvingError = true;
                result.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
            } catch(final IntentSender.SendIntentException e) {
                mGoogleApiClient.connect();
            }
        } else {
            showErrorDialog(result.getErrorCode());
            resolvingError = true;
        }
    }

    private void showErrorDialog(int errorCode) {
        final ErrorDialogFragment dialogFragment;
        final Bundle              args;

        dialogFragment = new ErrorDialogFragment();
        args           = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    public void onDialogDismissed()
    {
        resolvingError = false;
    }

    @Override
    protected void onActivityResult(final int requestCode,
                                    final int resultCode,
                                    final Intent data) {
        if (requestCode == REQUEST_RESOLVE_ERROR) {
            resolvingError = false;

            if(resultCode == RESULT_OK) {
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            }
        }
    }

    @Override
    public void onResult(@NonNull Status status) {
        Log.d("!!!!!!", "onResult");
        if (status.isSuccess() ) {
            drawGeofence();
        } else {
            // inform about fail
        }
    }

    public static class ErrorDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final int errorCode;

            errorCode = this.getArguments().getInt(DIALOG_ERROR);

            return GoogleApiAvailability.getInstance().getErrorDialog(
                    this.getActivity(),
                    errorCode,
                    REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            final Maps mainActivity;

            mainActivity = (Maps) getActivity();
            mainActivity.onDialogDismissed();
        }
    }

    public void back(View view) {
        finish();
    }
}
