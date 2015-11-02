package com.example.rossch.gv_maps.Components;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;


public class Hallway {

    /* PUBLIC */
    public HallwaySide side1;
    public HallwaySide side2;
    public int sideNumber;
    public String direction;
    public double width;
    public double length;

    /**
     * CONSTRUCTOR
     *
     * side1 is either W/N side
     * side2 is either E/S side
     *
     * hallway sides determined by 1 at NW corner going counter clockwise
     */
    public Hallway(HallwaySide side1, HallwaySide side2, int sideNumber, String direction) {
        this.side1 = side1;
        this.side2 = side2;
        this.sideNumber = sideNumber;
        this.direction = direction;
        //calculateDimensions();
    }

    /**
     * drawHallway
     */
    public void drawHallway(GoogleMap map) {
        // hall ways sides
        map.addPolyline(side1.line);
        map.addPolyline(side2.line);
        // rooms
        for (Room r : side1.rooms) {
            map.addPolygon(r.polygonOptions);
        }
        for (Room r2 : side2.rooms) {
            map.addPolygon(r2.polygonOptions);
        }
    }

    private void calculateDimensions() {
        width = DistanceCalculator.feetBetweenGeoCoordinates(side1.point1, side2.point1);
        length = side1.length;
    }

}//END Hallway.java
