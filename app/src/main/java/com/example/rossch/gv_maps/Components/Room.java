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
    private String hallwayName;

    // CONSTRUCTOR
    public Room(String name, LatLng doorLocation, String hallwayName, String type) {
        this.type = type;
        this.hallwayName = hallwayName;
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

    /**
     * Returns the type of the room
     */
    public String getType() {
        return this.type;
    }

    /**
     * Returns the hallway name that the room belongs to
     */
    public String getHallwayName() {
        return this.hallwayName;
    }

}//END Room.java
