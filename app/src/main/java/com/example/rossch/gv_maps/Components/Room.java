package com.example.rossch.gv_maps.Components;
import android.graphics.Color;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;



public class Room {

    // PRIVATE
    private String name;
    private LatLng doorLocation;
    private String type;

    // CONSTRUCTOR
    public Room(String name, LatLng doorLocation) {
        this.doorLocation = doorLocation;
        this.name = name;
    }

    /**
     * Draws the room on the given map
     */
    public void drawRoom(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(doorLocation)
                .title(name)
                .icon(BitmapDescriptorFactory.fromAsset("marker_blue.png"))
        );
    }

    // GETTERS

    /**
     * Returns the name of the room
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the middle location of the room
     */
    public LatLng getLocation() {
        return this.doorLocation;
    }

}//END Room.java
