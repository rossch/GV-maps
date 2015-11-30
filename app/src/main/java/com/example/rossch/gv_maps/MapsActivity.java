package com.example.rossch.gv_maps;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.*;
import android.widget.Toast;

import com.example.rossch.gv_maps.Components.MapsBuilder;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.*;

import java.util.HashMap;


public class MapsActivity extends FragmentActivity {

    private GoogleMap gvMap; // Might be null if Google Play services APK is not available.

    private LatLngBounds mackinawBounds;    // boundry for Mackinac building

    // logs
    private static final String TAG = MapsActivity.class.getSimpleName();

    // icon map
    private static class IconMap extends HashMap {
        {
            put("Stairs", "stairs_marker.png");
        }
    }
    public static final HashMap specialMarkerIconMap = new IconMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();

        String room;
        try {
            Bundle bundle = getIntent().getExtras();
            room = bundle.getString("room");
            Context context = getApplicationContext();
            CharSequence text = "Here is the room: " + room;
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
        catch (NullPointerException e) {
            //Means wasn't redirected from room
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #gvMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (gvMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            gvMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (gvMap != null) {
                setUpMap();
            }
        }


    }

    /**
     * setUpMap
     *
     * Draws the custom map interface
     */
    private void setUpMap() {
        // add GV campus marker
        LatLng gv_coords = new LatLng(42.963036, -85.891823);
        gvMap.addMarker(new MarkerOptions().position(gv_coords).title("GV"));

        // set Mackinaw building bounds
        LatLng mack_ne = new LatLng(42.967543, -85.886037);
        LatLng mack_sw = new LatLng(42.965859, -85.887925);
        mackinawBounds = new LatLngBounds(mack_sw, mack_ne);

        // build map
        MapsBuilder builder = new MapsBuilder(gvMap);
        builder.buildMackinac();
        addStairsMarkers();

        // update map bounds on map creation
        gvMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition position) {
                // Move camera.
                gvMap.moveCamera(CameraUpdateFactory.newLatLngBounds(mackinawBounds, 10));
                // Remove listener to prevent position reset on camera move.
                gvMap.setOnCameraChangeListener(null);
            }
        });
    }

    private void addStairsMarkers() {
        // map of coordinates
        HashMap<Double, Double> stairsCoordsMap = new HashMap<Double, Double>();
        stairsCoordsMap.put(42.966689, -85.886384);
        stairsCoordsMap.put(42.966728, -85.887340);
    }

    /*private void addMackinacBuildingSectionMarkers() {
        // map of section names, coordinates
        HashMap<String, LatLng> coordsMap = new HashMap<String, LatLng>();
        coordsMap.put("A Wing", new LatLng(42.966309, -85.886331));
        coordsMap.put("B Wing", new LatLng(42.967057, -85.886537));
        coordsMap.put("C Wing", new LatLng(42.966552, -85.886881));
        coordsMap.put("D Wing", new LatLng(42.967057, -85.887185));

        // create markers
        for (String sectionName : coordsMap.keySet()) {
            gvMap.addMarker(new MarkerOptions()
                            .position(coordsMap.get(sectionName))
                            .alpha(0.5f)
                            .title(sectionName)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
            );
        }
    }*/

    public void goToCurrentLocation(View view) {
        LatLng coordinate = new LatLng(42.963036, -85.891823);
        CameraUpdate yourLocation = CameraUpdateFactory.newLatLngZoom(coordinate, 10);
        gvMap.animateCamera(yourLocation);
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
