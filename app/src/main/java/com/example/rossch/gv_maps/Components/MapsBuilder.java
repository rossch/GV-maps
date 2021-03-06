package com.example.rossch.gv_maps.Components;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MapsBuilder {

    // PRIVATE
    private GoogleMap map;
    private HashMap<String, List<ParseObject>> hallwayMap;
    private HashMap<String, List<ParseObject>> roomMap;

    // DEBUG TAG
    private static final String TAG = MapsBuilder.class.getSimpleName();

    // CONSTRUCTOR
    public MapsBuilder(GoogleMap map) {
        this.map = map;
    }

    /**
     * Builds Mackinac building sections (from Parse DB)
     */
    public void buildMackinac(String singleRoomName) {
        Log.d(TAG, "Room name: " + singleRoomName);
        // init hallways
        initHallwayMap();
        // init rooms
        initRoomMap(singleRoomName);
        // get building sections
        List<ParseObject> buildingSections = getObjectData("BuildingSections");
        if (buildingSections != null) {
            for (ParseObject o : buildingSections) {
                createBuildingSection(o);
            }
        }
    }

    /**
     * Gets the records for the specified object name
     */
    private List<ParseObject> getObjectData(String objectName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery(objectName);
        try {
            List<ParseObject> records = query.find();
            return records;
        } catch (com.parse.ParseException e) {
            Log.d(TAG, e.getMessage());
            return null;
        }
    }

    /**
     * Sets up the Building Section -> Hallway (list) map
     */
    private void initHallwayMap() {
        List<ParseObject> hallwayRecords = getObjectData("Hallways");
        if (hallwayRecords != null) {
            hallwayMap = new HashMap<String, List<ParseObject>>();
            for (ParseObject o : hallwayRecords) {
                String buildingSectionName = (String)o.get("Building_Section");
                if (hallwayMap.containsKey(buildingSectionName))
                    hallwayMap.get(buildingSectionName).add(o);
                else {
                    List<ParseObject> hallwayList = new ArrayList<ParseObject>();
                    hallwayList.add(o);
                    hallwayMap.put(buildingSectionName, hallwayList);
                }
            }
        }
    }

    /**
     * Sets up the Hallway -> Room (list) map
     */
    private void initRoomMap(String singleRoomName) {
        List<ParseObject> roomRecords = getObjectData("Rooms");
        if (roomRecords != null) {
            roomMap = new HashMap<String, List<ParseObject>>();
            for (ParseObject o : roomRecords) {
                String hallwayName = (String)o.get("Hallway");
                String roomName = (String)o.get("Name");
                if (singleRoomName.isEmpty()) {
                    if (roomMap.containsKey(hallwayName))
                        roomMap.get(hallwayName).add(o);
                    else {
                        List<ParseObject> roomList = new ArrayList<ParseObject>();
                        roomList.add(o);
                        roomMap.put(hallwayName, roomList);
                    }
                } else if (singleRoomName.equals(roomName)) {
                    List<ParseObject> singleRoomList = new ArrayList<ParseObject>();
                    singleRoomList.add(o);
                    roomMap.put(hallwayName, singleRoomList);
                }
            }
        }
    }

    private void createBuildingSection(ParseObject record) {
        String sectionName = (String)record.get("Name");
        Double latitude = (Double)record.get("Latitude");
        Double longitude = (Double)record.get("Longitude");
        LatLng nwCorner = new LatLng(latitude, longitude);
        Integer w = (Integer)record.get("Width");
        double width = w.doubleValue();
        Integer l = (Integer)record.get("Length");
        double length = l.doubleValue();
        BuildingSection section = new BuildingSection(nwCorner, width, length, sectionName);

        // create building section's hallways
        if (hallwayMap.containsKey(sectionName)) {
            int size = hallwayMap.get(sectionName).size();
            List<ParseObject> recordList = hallwayMap.get(sectionName);
            for (ParseObject o : recordList) {
                createHallway(o, section);
            }
        }
        section.drawBuildingSection(map);
    }

    private void createHallway(ParseObject record, BuildingSection section) {
        String direction = (String)record.get("Direction");
        Double width, length;
        try {
            width = (Double) record.get("Width");
        } catch (ClassCastException e1) {
            Integer w = (Integer) record.get("Width");
            width = w.doubleValue();
        }
        try {
            length = (Double) record.get("Length");
        } catch (ClassCastException e2) {
            Integer l = (Integer) record.get("Length");
            length = l.doubleValue();
        }
        String hallwayName = (String)record.get("Name");
        Double feetSouth = (Double)record.get("Feet_South");
        Double feetEast = (Double)record.get("Feet_East");
        LatLng point = new LatLng(feetEast, feetSouth);
        section.addHallwayToMap(point, width, length, direction, hallwayName);
        // add hallway rooms
        if (roomMap.containsKey(hallwayName))
            createRooms(hallwayName, section);
    }

    private void createRooms(String hallwayName, BuildingSection section) {
        List<ParseObject> roomsList = roomMap.get(hallwayName);
        for (ParseObject o : roomsList) {
            String name = (String)o.get("Name");
            Double lat = (Double)o.get("Latitude");
            Double lng = (Double)o.get("Longitude");
            String type = (String)o.get("Type");
            LatLng point = new LatLng(lat, lng);
            Room room = new Room(name, point, hallwayName, type);
            section.hallwayMap.get(hallwayName).addRoom(1, room);
        }
    }

    /**
     * Creates Mackinac building sections (from scratch)
     */
    public void createMackinac() {
        buildMackinacSectionD();
        buildMackinacSectionC();
        buildMackinacSectionB();
        buildMackinacSectionA();
    }

    /**
     * Builds the Section D of the Mackinac building
     */
    private void buildMackinacSectionD() {
        LatLng nwCorner_D = new LatLng(42.967400, -85.887416);
        double width_D = 106.00;
        double length_D = 250.00;
        BuildingSection section_D = new BuildingSection(nwCorner_D, width_D, length_D, "Mackinac-D-1");
        // vertical hallway 1
        section_D.addHallway("D-1-C101", 12.50, 25.00, 12.50, 210.00, "vertical");
        section_D.addHallwayRooms("D-1-C101", 1, "D-1", 20, 202, 2, 11.00, -2.00, "Teacher Office");
        section_D.addHallwayRooms("D-1-C101", 2, "D-1", 5, 209, 6, 33.00, 12.50, "Classroom");
        // vertical hallway 2
        section_D.addHallway("D-1-C103", 75.00, 25.00, 12.50, 210.00, "vertical");
        section_D.addHallwayRooms("D-1-C103", 1, "D-1", 5, 141, -6, 33.00, 12.50, "Classroom");
        section_D.addHallwayRooms("D-1-C103", 2, "D-1", 15, 140, -2, 11.00, 44.00, "Teacher Office");
        // horizontal hallways
        section_D.addHallway("D-1-C104", 12.50, 25.00, 12.50, 75.00, "horizontal");
        section_D.addHallway("D-1-C102", 12.50, 222.50, 17.50, 75.00, "horizontal");
        section_D.addHallwayRooms("D-1-C102", 2, "D-1", 3, 102, 2, 11.00, 37.50, "Teacher Office");
        // stairs 1
        LatLng stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_D, 58.00, "S");
        stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation1, 93.75, "E");
        section_D.addHallwaySpecialMarker("S-1", stairsLocation1, "Stairs", "D-1-C103");
        // stairs 2
        LatLng stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_D, 247.50, "S");
        stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation2, 10.00, "E");
        section_D.addHallwaySpecialMarker("S-2", stairsLocation2, "Stairs", "D-1-C102");
        // restroom 1
        LatLng restRoomLocation1 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_D, 215.00, "S");
        restRoomLocation1 = DistanceCalculator.geoCordFromFeetDistance(restRoomLocation1, 50.00, "E");
        section_D.addHallwaySpecialMarker("Restroom-1", restRoomLocation1, "Restroom", "D-1-C102");
        section_D.drawBuildingSection(map);
    }

    /**
     * Builds the Section C of the Mackinac building
     */
    private void buildMackinacSectionC() {
        LatLng nwCorner_C = new LatLng(42.966650, -85.887451);
        double width_C = 231.00;
        double length_C = 60.00;
        BuildingSection section_C = new BuildingSection(nwCorner_C, width_C, length_C, "Mackinac-C-1");
        // vertical hallway 1
        section_C.addHallway("C-1-C102", 41.00, 0.00, 17.50, 55.00, "vertical");
        // horizontal hallway 1
        section_C.addHallway("C-1-C101", 41.00, 41.00, 15.00, 190.00, "horizontal" );
        section_C.drawBuildingSection(map);
    }

    /**
     * Builds the Section B of the Mackinac building
     */
    private void buildMackinacSectionB() {
        LatLng nwCorner_B = new LatLng(42.967354, -85.886768);
        double width_B = 90.00;
        double length_B = 258.00;
        BuildingSection section_B = new BuildingSection(nwCorner_B, width_B, length_B, "Mackinac-B-1");
        // vertical hallway 1
        section_B.addHallway("B-1-C102", 62.50, 18.00, 12.50, 240.00, "vertical");
        section_B.addHallwayRooms("B-1-C102", 1, "B-1", 1, 124, 0, 29.00, 50.00, "Classroom");
        section_B.addHallwayRooms("B-1-C102", 1, "B-1", 2, 118, -2, 29.00, 98.00, "Classroom");
        section_B.addHallwayRooms("B-1-C102", 1, "B-1", 1, 110, 0, 45.00, 165.00, "Classroom");
        // horizontal hallway 1
        section_B.addHallway("B-1-C101", 0.00, 209.50, 12.50, 75.00, "horizontal");
        // horizontal hallway 2
        section_B.addHallway("B-1-C103", 37.50, 163.00, 12.50, 25.00, "horizontal");
        section_B.addHallwayRooms("B-1-C103", 1, "B-1", 1, 114, 0, 0.00, 0.00, "Classroom");
        section_B.addHallwayRooms("B-1-C103", 2, "B-1", 1, 112, 0, 0.00, 0.00, "Classroom");
        // horizontal hallway 3
        section_B.addHallway("B-1-C105", 37.50, 102.50, 12.50, 25.00, "horizontal");
        section_B.addHallwayRooms("B-1-C105", 1, "B-1", 1, 122, 0, 0.00, 0.00, "Classroom");
        section_B.addHallwayRooms("B-1-C105", 2, "B-1", 1, 120, 0, 0.00, 0.00, "Classroom");
        // horizontal hallway 4
        section_B.addHallway("B-1-C107", 7.50, 32.00, 12.50, 62.50, "horizontal");
        section_B.addHallwayRooms("B-1-C107", 1, "B-1", 2, 132, 4, 27.50, 7.50, "Classroom");
        section_B.addHallwayRooms("B-1-C107", 2, "B-1", 2, 128, -2, 23.75, 15.00, "Classroom");
        // stairs 1
        LatLng stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_B, 60.00, "S");
        stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation1, 7.50, "E");
        section_B.addHallwaySpecialMarker("S-8", stairsLocation1, "Stairs", "B-1-C107");
        // stairs 2
        LatLng stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_B, 240.00, "S");
        stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation2, 7.50, "E");
        section_B.addHallwaySpecialMarker("S-7", stairsLocation2, "Stairs", "B-1-C101");
        // stairs 3
        LatLng stairsLocation3 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_B, 240.00, "S");
        stairsLocation3 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation3, 82.50, "E");
        section_B.addHallwaySpecialMarker("S-9", stairsLocation3, "Stairs", "B-1-C102");
        section_B.drawBuildingSection(map);
    }

    /**
     * Builds the Section A of the Mackinac building
     */
    private void buildMackinacSectionA() {
        LatLng nwCorner_A = new LatLng(42.966652, -85.886598);
        double width_A = 110.00;
        double length_A = 250.00;
        BuildingSection section_A = new BuildingSection(nwCorner_A, width_A, length_A, "Mackinac-A-1");
        double hallwayWidth = 10.00;
        // vertical hallway 1
        section_A.addHallway("A-1-C104", 12.50, 60.00, hallwayWidth, 173.00, "vertical");
        section_A.addHallwayRooms("A-1-C104", 1, "A-1", 9, 106, 2, 11.00, 22.00, "Teacher Office");
        section_A.addHallwayRooms("A-1-C104", 2, "A-1", 1, 101, 0, 40.00, 0.00, "Lab");
        section_A.addHallwayRooms("A-1-C104", 2, "A-1", 4, 105, 6, 25.00, 40.00, "Classroom");
        // vertical hallway 2
        section_A.addHallway("A-1-C106", 89.00, 26.00, hallwayWidth, 206.00, "vertical");
        section_A.addHallwayRooms("A-1-C106", 2, "A-1", 12, 170, -2, 11.00, 23.00, "Teacher Office");
        section_A.addHallwayRooms("A-1-C106", 1, "A-1", 2, 171, -4, 20.00, 34.00, "Lab");
        section_A.addHallwayRooms("A-1-C106", 1, "A-1", 4, 165, -4, 25.00, 74.00, "Classroom");
        // horizontal hallway 1
        section_A.addHallway("A-1-C103", 12.50, 26.00, hallwayWidth, 86.50, "horizontal");
        // horizontal hallway 2
        section_A.addHallway("A-1-C105", 12.50, 223.00, hallwayWidth, 86.50, "horizontal");
        section_A.addHallwayRooms("A-1-C105", 2, "A-1", 6, 130, 2, 11.00, 10.00, "Teacher Office");
        // stairs 1
        LatLng stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_A, 200.00, "S");
        stairsLocation1 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation1, 5.00, "E");
        section_A.addHallwaySpecialMarker("S-6", stairsLocation1, "Stairs", "A-1-C104");
        // stairs 2
        LatLng stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(nwCorner_A, 200.00, "S");
        stairsLocation2 = DistanceCalculator.geoCordFromFeetDistance(stairsLocation2, 105.00, "E");
        section_A.addHallwaySpecialMarker("S-5", stairsLocation2, "Stairs", "A-1-C106");
        section_A.drawBuildingSection(map);
    }

}//END MapsBuilder.java
