package com.example.rossch.gv_maps.Components;
import android.graphics.Color;

import com.example.rossch.gv_maps.MapsActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;


public class Hallway {

    // PRIVATE
    private String direction;
    private double width;
    private double length;
    private String name;

    private LatLng side1_start;
    private LatLng side1_end;
    private LatLng side2_start;
    private LatLng side2_end;
    private ArrayList<Room> side1_rooms;
    private ArrayList<Room> side2_rooms;
    private HashMap<String, Room> specialMarkerMap;
    private String buildingSectionName;

    private final int fillColor = Color.GRAY;
    private final int outlineColor = Color.GRAY;

    // PUBLIC
    public PolygonOptions hallwayPolygonOptions;

    // DEBUG TAG
    private static final String TAG = Hallway.class.getSimpleName();

    // CONSTRUCTOR
    public Hallway(LatLng nwCorner, double width, double length, String direction, String name, String buildingSectionName) {
        this.name = name;
        this.buildingSectionName = buildingSectionName;
        this.width = width;
        this.length = length;
        this.direction = direction;
        this.side1_start = nwCorner;
        createHallway();
    }

    /**
     * Creates the hallway's PolygonOptions object
     */
    private void createHallway() {
        hallwayPolygonOptions = new PolygonOptions();
        specialMarkerMap = new HashMap<String, Room>();
        hallwayPolygonOptions.fillColor(fillColor);
        hallwayPolygonOptions.strokeColor(outlineColor);
        calculateDimensions();
        this.side1_rooms = new ArrayList<Room>();
        this.side2_rooms = new ArrayList<Room>();
    }

    /**
     * Determines the dimensions of the hallway
     */
    private void calculateDimensions() {
        Log.d(TAG, "Direction: " + direction);
        if (direction.equals("horizontal")) {
            Log.d(TAG, "is horizontal");
            side1_end = DistanceCalculator.geoCordFromFeetDistance(side1_start, length, "E");
            side2_start = DistanceCalculator.geoCordFromFeetDistance(side1_start, width, "S");
            side2_end = DistanceCalculator.geoCordFromFeetDistance(side2_start, length, "E");
            hallwayPolygonOptions.add(side1_start, side1_end, side2_end, side2_start);
            Log.d(TAG, "added points");
        } else {
            Log.d(TAG, "is vertical");
            side1_end = DistanceCalculator.geoCordFromFeetDistance(side1_start, length, "S");
            side2_start = DistanceCalculator.geoCordFromFeetDistance(side1_start, width, "E");
            side2_end = DistanceCalculator.geoCordFromFeetDistance(side2_start, length, "S");
            hallwayPolygonOptions.add(side1_start, side2_start, side2_end, side1_end);
        }
    }

    /**
     * Draws the hallway and its rooms on the given map
     */
    public void drawHallway(GoogleMap map) {
        Log.d(TAG, "reached drawHallway");
        // hallway
        try {
            Log.d(TAG, String.valueOf(hallwayPolygonOptions.getPoints().size()));
            map.addPolygon(hallwayPolygonOptions);
        } catch (Exception e) {
            Log.d(TAG, "ERROR: " + e.getMessage());
        }
        Log.d(TAG, "past addPolygon");

        // side 1 rooms
        for (Room r1 : side1_rooms) {
            r1.drawRoom(map);
        }
        // side 2 rooms
        for (Room r2 : side2_rooms) {
            r2.drawRoom(map);
        }
        // special markers
        for (String s : specialMarkerMap.keySet()) {
            Room room = specialMarkerMap.get(s);
            map.addMarker(new MarkerOptions()
                    .position(room.getLocation())
                    .title(room.getName())
                    .icon(BitmapDescriptorFactory.fromAsset((String) MapsActivity.specialMarkerIconMap.get(room.getType())))
            );
        }
        Log.d(TAG, "reached end of drawHallway");
    }

    /**
     * Adds a room entrance points to a hallway side
     */
    public void addRoom(int hallwaySide, Room room) {
        // side 1
        if (hallwaySide == 1) {
            side1_rooms.add(room);
        }
        // side 2
        else if (hallwaySide == 2) {
            side2_rooms.add(room);
        }
    }

    /**
     * Adds a special marker to the hallway
     */
    public void addSpecialMarker(String name, LatLng location, String type, String hallwayName) {
        if (!specialMarkerMap.containsKey(name)) {
            Room room = new Room(name, location, hallwayName, type);
            specialMarkerMap.put(name, room);
        }
    }

    // GETTERS

    /**
     * Returns the name of the hallway
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the starting point for side 1 of hallway
     */
    public LatLng getSide1_start() {
        return this.side1_start;
    }

    /**
     * Returns the starting point for side 2 of hallway
     */
    public LatLng getSide2_start() {
        return this.side2_start;
    }

    /**
     * Returns the direction of the hallway
     */
    public String getDirection() {
        return this.direction;
    }

    public void printRoomTable() {
        String line;
        for (Room r1 : side1_rooms) {
            line = r1.getName() + " ";
            line += name + " ";
            line += r1.getType() + " ";
            line += String.valueOf(r1.getLocation().latitude) + " ";
            line += String.valueOf(r1.getLocation().longitude);
            Log.d(TAG, line);
        }
        for (Room r2 : side2_rooms) {
            line = r2.getName() + " ";
            line += name + " ";
            line += r2.getType() + " ";
            line += String.valueOf(r2.getLocation().latitude) + " ";
            line += String.valueOf(r2.getLocation().longitude);
            Log.d(TAG, line);
        }
    }

}//END Hallway.java
