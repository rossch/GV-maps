package com.example.rossch.gv_maps.Components;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;


public class HallwaySide {

    /* PUBLIC */
    public LatLng point1;
    public LatLng point2;
    public PolylineOptions line;
    public double length;
    public ArrayList<Room> rooms;

    /* PRIVATE */
    private final int lineColor = Color.GRAY;
    private final float lineWidth = 3;

    /**
     * CONSTRUCTOR
     */
    public HallwaySide(LatLng point1, LatLng point2) {
        this.point1 = point1;
        this.point2 = point2;
        createLine();
        length = DistanceCalculator.feetBetweenGeoCoordinates(this.point1, this.point2);
        rooms = new ArrayList<Room>();
    }

    /**
     * createLine
     */
    private void createLine() {
        line = new PolylineOptions();
        line.add(point1);
        line.add(point2);
        line.color(lineColor);
        line.width(lineWidth);
    }

    /**
     * createRooms
     */
    public void createRooms(double width, double length, String[] roomNames, int sideNumber, double distanceFromHallwayStart, String direction) {
        LatLng corner = null;
        String way = "";
        if (direction == "horizontal")
            way = "E";
        else if (direction == "vertical")
            way = "S";
        corner = DistanceCalculator.geoCordFromFeetDistance(point1, distanceFromHallwayStart, way);

        // side 1 (W/N)
        if (sideNumber == 1) {
            for (int i=0; i<roomNames.length; i++) {
                rooms.add(new Room(
                    corner, "NE", roomNames[i], width, length
                ));
                corner = DistanceCalculator.geoCordFromFeetDistance(corner, length, way);
            }
        }
        // side 2
        else if (sideNumber == 2) {
            for (int i=0; i<roomNames.length; i++) {
                rooms.add(new Room(
                        corner, "NW", roomNames[i], width, length
                ));
                corner = DistanceCalculator.geoCordFromFeetDistance(corner, length, way);
            }
        }
    }

}//END HallwaySide.java
