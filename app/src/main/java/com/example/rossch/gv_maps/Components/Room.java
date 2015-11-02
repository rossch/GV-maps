package com.example.rossch.gv_maps.Components;


import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;

public class Room {

    /* PUBLIC */
    public String name;
    public PolygonOptions polygonOptions;
    public LatLng markerLocation;


    /* PRIVATE */
    private final int lineColor = Color.rgb(0, 31, 125);
    private final int fillColor = Color.rgb(167, 189, 255);
    private final float lineWidth = 3;
    private LatLng corner;
    private String cornerDirection;
    private double width;
    private double length;


    /**
     * CONSTRUCTOR
     *
     * cornerDirection should be either NW or SE
     * width is E/W
     * length is N/S
     */
    public Room(LatLng corner, String cornerDirection, String roomName, double width, double length) {
        this.corner = corner;
        this.cornerDirection = cornerDirection;
        this.name = roomName;
        this.width = width;
        this.length = length;
        createRoom();
    }

    /**
     * createRoom
     */
    private void createRoom() {
        polygonOptions = new PolygonOptions();
        LatLng point2 = null;
        LatLng point3 = null;
        LatLng point4 = null;

        // create room coordinates
        if (cornerDirection == "NW") {
            point2 = DistanceCalculator.geoCordFromFeetDistance(corner, width, "E");
            point3 = DistanceCalculator.geoCordFromFeetDistance(point2, length, "S");
            point4 = DistanceCalculator.geoCordFromFeetDistance(point3, width, "W");
        } else if (cornerDirection == "NE") {
            point2 = DistanceCalculator.geoCordFromFeetDistance(corner, width, "W");
            point3 = DistanceCalculator.geoCordFromFeetDistance(point2, length, "S");
            point4 = DistanceCalculator.geoCordFromFeetDistance(point3, width, "E");
        }
        polygonOptions.add(corner, point2, point3, point4);
        polygonOptions.strokeWidth(lineWidth);
        polygonOptions.strokeColor(lineColor);
        polygonOptions.fillColor(fillColor);
    }


}//END Room.java
