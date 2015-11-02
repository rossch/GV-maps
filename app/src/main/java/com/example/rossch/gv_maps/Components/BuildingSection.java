package com.example.rossch.gv_maps.Components;

import android.graphics.Color;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;



public class BuildingSection {

    /* PUBLIC */
    public ArrayList<Hallway> hallways;
    public String name;

    /* PRIVATE */
    private LatLng NW_corner, NE_corner, SE_corner, SW_corner;
    private final int outlineColor = Color.BLACK;

    /**
     * CONSTRUCTOR
     */
    public BuildingSection(LatLng NW_corner, LatLng NE_corner, LatLng SE_corner, LatLng SW_corner, String name) {
        this.name = name;
        this.NW_corner = NW_corner;
        this.NE_corner = NE_corner;
        this.SW_corner = SW_corner;
        this.SE_corner = SE_corner;
        hallways = new ArrayList<Hallway>();
    }

    public BuildingSection(LatLng nwCorner, double width, double length, String name) {
        this.name = name;
        hallways = new ArrayList<Hallway>();
        this.NW_corner = nwCorner;
        this.NE_corner = DistanceCalculator.geoCordFromFeetDistance(NW_corner, width, "E");
        this.SE_corner = DistanceCalculator.geoCordFromFeetDistance(NE_corner, length, "S");
        this.SW_corner = DistanceCalculator.geoCordFromFeetDistance(SE_corner, width, "W");
    }

    /**
     * drawBuildingSection
     */
    public void drawBuildingSection(GoogleMap map) {
        // draw section outline
        map.addPolygon(new PolygonOptions()
            .add(NW_corner, NE_corner, SE_corner, SW_corner)
            .strokeColor(outlineColor)
            .fillColor(Color.TRANSPARENT)
        );
        // draw hallways
        for (Hallway h : hallways) {
            h.drawHallway(map);
        }
    }

    /**
     * addHallway
     */
    public void addHallway(Hallway hallway) {
        hallways.add(hallway);
    }

}//END BuildingSection.java
