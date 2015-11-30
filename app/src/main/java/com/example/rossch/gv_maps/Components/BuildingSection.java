package com.example.rossch.gv_maps.Components;
import android.graphics.Color;
import android.os.Build;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import java.util.ArrayList;
import java.util.HashMap;
import android.util.Log;



public class BuildingSection {

    // PRIVATE
    private LatLng nwCorner, neCorner, seCorner, swCorner;
    private final int outlineColor = Color.TRANSPARENT;

    // PUBLIC
    public HashMap<String, Hallway> hallwayMap;
    public String name;

    // DEBUG TAG
    private static final String TAG = BuildingSection.class.getSimpleName();

    // CONSTRUCTOR
    public BuildingSection(LatLng nwCorner, double width, double length, String name) {
        this.name = name;
        this.hallwayMap = new HashMap<String, Hallway>();
        this.nwCorner = nwCorner;
        this.neCorner = DistanceCalculator.geoCordFromFeetDistance(nwCorner, width, "E");
        this.seCorner = DistanceCalculator.geoCordFromFeetDistance(neCorner, length, "S");
        this.swCorner = DistanceCalculator.geoCordFromFeetDistance(seCorner, width, "W");
    }

    /**
     * Draws the building section on the map
     */
    public void drawBuildingSection(GoogleMap map) {
        // building section
        map.addPolygon(new PolygonOptions()
                .add(nwCorner, neCorner, seCorner, swCorner)
                .strokeColor(outlineColor)
                .fillColor(Color.TRANSPARENT));

        // hallways
        for (Hallway h : hallwayMap.values())
            h.drawHallway(map);
    }

    /**
     * Adds a hallway to the building section
     */
    public void addHallway(String hallwayName, double feetEast, double feetSouth, double width, double length, String direction) {
        LatLng startingCoord = DistanceCalculator.geoCordFromFeetDistance(nwCorner, feetEast, "E");
        startingCoord = DistanceCalculator.geoCordFromFeetDistance(startingCoord, feetSouth, "S");
        Hallway hallway = new Hallway(startingCoord, width, length, direction, hallwayName);
        hallwayMap.put(hallwayName, hallway);
    }

    /**
     * Adds a special marker to a hallway
     */
    public void addHallwaySpecialMarker(String name, LatLng location, String type, String hallwayName) {
        if (hallwayMap.containsKey(hallwayName))
            hallwayMap.get(hallwayName).addSpecialMarker(name, location, type, hallwayName);
    }

    /**
     * Adds a list of rooms to a specified hallway
     */
    public void addHallwayRooms(String hallwayName, int hallwaySide, String namePrefix, int totalRooms, int startingRoomNumber, int roomNumberIncrement,
                                double roomLength, double startingLocationOffset, String type) {
        // check if hallway exists
        if (hallwayMap.containsKey(hallwayName)) {
            int roomNumber = startingRoomNumber;
            String roomName;

            // hallway direction
            String direction = hallwayMap.get(hallwayName).getDirection();
            direction = direction == "vertical" ? "S" : "E";

            // set starting location
            LatLng location = null;
            if (hallwaySide == 1)
                location = hallwayMap.get(hallwayName).getSide1_start();
            else if (hallwaySide == 2)
                location = hallwayMap.get(hallwayName).getSide2_start();
            location = DistanceCalculator.geoCordFromFeetDistance(location, ((double)roomLength / 2.00), direction);
            // account for offset
            if (startingLocationOffset != 0) {
                location = DistanceCalculator.geoCordFromFeetDistance(location, startingLocationOffset, direction);
            }

            // add rooms
            for (int i = 0; i < totalRooms; i++) {
                roomName = namePrefix + "-" + String.valueOf(roomNumber);
                Room r = new Room(roomName, location, hallwayName, type);
                hallwayMap.get(hallwayName).addRoom(hallwaySide, r);
                roomNumber += roomNumberIncrement;
                location = DistanceCalculator.geoCordFromFeetDistance(location, roomLength, direction);
            }
        }
    }

}//END BuildingSection.java
