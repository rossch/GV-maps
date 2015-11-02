package com.example.rossch.gv_maps;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Toast;

import com.example.rossch.gv_maps.Components.BuildingSection;
import com.example.rossch.gv_maps.Components.DistanceCalculator;
import com.example.rossch.gv_maps.Components.Hallway;
import com.example.rossch.gv_maps.Components.HallwaySide;
import com.example.rossch.gv_maps.Components.RoomList;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Arrays;


public class MapsActivity extends FragmentActivity {

    private GoogleMap gvMap; // Might be null if Google Play services APK is not available.

    private LatLngBounds mackinawBounds;    // boundry for Mackinac building

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

        // create building sections
        createBuildingSections();

        // add Mackinaw building section markers
        //addMackinacBuildingSectionMarkers();

        // add stairs markers
        //addStairsMarkers();

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

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//  Create building sections
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * createBuildingSections
     */
    private void createBuildingSections() {
        createSectionB();
        createSectionA();
        createSectionC();
        createSectionD();
    }

    /**
     * createSectionB
     */
    private void createSectionA() {
        // building corner coordinates
        LatLng nw_A = new LatLng(42.966647, -85.886590);
        LatLng ne_A = new LatLng(42.966647, -85.886123);
        LatLng se_A = new LatLng(42.965977, -85.886123);
        LatLng sw_A = new LatLng(42.965977, -85.886590);
        BuildingSection section_A = new BuildingSection(nw_A, ne_A, se_A, sw_A, "Section A");
        section_A.drawBuildingSection(gvMap);
    }

    /**
     * createSectionB
     */
    private void createSectionB() {
        // building corner coordinates
        LatLng nw_B = new LatLng(42.967365, -85.886590);
        LatLng ne_B = new LatLng(42.967365, -85.886658);
        LatLng se_B = new LatLng(42.966647, -85.886658);
        LatLng sw_B = new LatLng(42.966647, -85.886590);
        BuildingSection section_B = new BuildingSection(nw_B, ne_B, se_B, sw_B, "Section B");
        section_B.drawBuildingSection(gvMap);
    }

    /**
     * createSectionC
     */
    private void createSectionC() {
        // building corner coordinates
        LatLng nw_C = new LatLng(42.966647, -85.887389);
        LatLng ne_C = new LatLng(42.966647, -85.886590);
        LatLng se_C = new LatLng(42.966477, -85.886590);
        LatLng sw_C = new LatLng(42.966477, -85.887389);
        BuildingSection section_C = new BuildingSection(nw_C, ne_C, se_C, sw_C, "Section C");
        section_C.drawBuildingSection(gvMap);
    }

    /**
     * createSectionD
     */
    private void createSectionD() {
        // building corner coordinates
        LatLng nw_D = new LatLng(42.967404, -85.887361);
        LatLng ne_D = DistanceCalculator.geoCordFromFeetDistance(nw_D, 102.00, "E");
        LatLng se_D = new LatLng(42.966647, -85.886982);
        LatLng sw_D = new LatLng(42.966647, -85.887361);
        BuildingSection section_D = new BuildingSection(nw_D, ne_D, se_D, sw_D, "Section D");

        /*
            hallway 1
        */

        // side 1
        LatLng hall1_side1_top = DistanceCalculator.geoCordFromFeetDistance(nw_D, 12.00, "E");
        hall1_side1_top = DistanceCalculator.geoCordFromFeetDistance(hall1_side1_top, 22.666667, "S");
        LatLng hall1_side1_bot = DistanceCalculator.geoCordFromFeetDistance(hall1_side1_top, 209.00, "S");
        HallwaySide hall1_side1 = new HallwaySide(hall1_side1_top, hall1_side1_bot);
        // side 1 rooms
        hall1_side1.createRooms(12.00, 12.00, RoomList.sectionD_h1_side1_rooms, 1, 0, "vertical");
        // side 2
        LatLng hall1_side2_top = DistanceCalculator.geoCordFromFeetDistance(hall1_side1_top, 12.00, "E");
        LatLng hall1_side2_bot = DistanceCalculator.geoCordFromFeetDistance(hall1_side1_bot, 12.00, "E");
        HallwaySide hall1_side2 = new HallwaySide(hall1_side2_top, hall1_side2_bot);
        // side 2 rooms
        hall1_side2.createRooms(26.50, 33.00, RoomList.sectionD_h1_side2_rooms, 2, 12.00, "vertical");
        // add hallway
        Hallway hallway1 = new Hallway(hall1_side1, hall1_side2, 1, "vertical");
        section_D.addHallway(hallway1);

        /*
            hallway 2
        */

        // side 1
        LatLng hall2_side1_top = hall1_side1_top;
        LatLng hall2_side1_bot = DistanceCalculator.geoCordFromFeetDistance(hall2_side1_top, 78.00, "E");
        HallwaySide hall2_side1 = new HallwaySide(hall2_side1_top, hall2_side1_bot);
        // side 2
        LatLng hall2_side2_top = DistanceCalculator.geoCordFromFeetDistance(hall2_side1_top, 12.00, "S");
        LatLng hall2_side2_bot = DistanceCalculator.geoCordFromFeetDistance(hall2_side2_top, 78.00, "E");
        HallwaySide hall2_side2 = new HallwaySide(hall2_side2_top, hall2_side2_bot);
        // add hallway
        Hallway hallway2 = new Hallway(hall2_side1, hall2_side2, 2, "horizontal");
        section_D.addHallway(hallway2);

        /*
            hallway 3
        */

        // side 1
        LatLng hall3_side1_top = DistanceCalculator.geoCordFromFeetDistance(hall1_side2_top, 54.00, "E");
        LatLng hall3_side1_bot = DistanceCalculator.geoCordFromFeetDistance(hall1_side2_bot, 54.00, "E");
        HallwaySide hall3_side1 = new HallwaySide(hall3_side1_top, hall3_side1_bot);
        // side 1 rooms
        hall3_side1.createRooms(26.50, 33.00, RoomList.sectionD_h3_side1_rooms, 1, 12.00, "vertical");
        // side 2
        LatLng hall3_side2_top = DistanceCalculator.geoCordFromFeetDistance(hall3_side1_top, 12, "E");
        LatLng hall3_side2_bot = DistanceCalculator.geoCordFromFeetDistance(hall3_side1_bot, 12, "E");
        HallwaySide hall3_side2 = new HallwaySide(hall3_side2_top, hall3_side2_bot);
        // side 2 rooms
        hall3_side2.createRooms(12.00, 12.00, RoomList.sectionD_h3_side2_rooms, 2, 45.00, "vertical");
        // add hallway
        Hallway hallway3 = new Hallway(hall3_side1, hall3_side2, 3, "vertical");
        section_D.addHallway(hallway3);

        /*
            hallway 4
        */

        LatLng hall4_side1_top = DistanceCalculator.geoCordFromFeetDistance(hall1_side1_bot, 12, "N");
        LatLng hall4_side1_bot = DistanceCalculator.geoCordFromFeetDistance(hall4_side1_top, 78, "E");
        HallwaySide hall4_side1 = new HallwaySide(hall4_side1_top, hall4_side1_bot);
        // side 2
        LatLng hall4_side2_top = hall1_side1_bot;
        LatLng hall4_side2_bot = DistanceCalculator.geoCordFromFeetDistance(hall4_side2_top, 78, "E");
        HallwaySide hall4_side2 = new HallwaySide(hall4_side2_top, hall4_side2_bot);
        // add hallway
        Hallway hallway4 = new Hallway(hall4_side1, hall4_side2, 4, "horizontal");
        section_D.addHallway(hallway4);

        // draw building section
        section_D.drawBuildingSection(gvMap);
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * addStairsMarkers
     *
     * Draws the stair markers
     */
    private void addStairsMarkers() {
        // map of coordinates
        HashMap<Double, Double> stairsCoordsMap = new HashMap<Double, Double>();
        stairsCoordsMap.put(42.966689, -85.886384);
        stairsCoordsMap.put(42.966728, -85.887340);

        // create markers
        for (double coord : stairsCoordsMap.keySet()) {
            gvMap.addMarker(new MarkerOptions()
                            .position(new LatLng(coord, stairsCoordsMap.get(coord)))
                            .title("Stairs")
                            .icon(BitmapDescriptorFactory.fromAsset("stairs.png"))
            );
        }
    }

    /**
     * addMackinacBuildingSectionMarkers
     *
     * Draws markers for the building sections in Mackinac
     */
    private void addMackinacBuildingSectionMarkers() {
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
    }

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
