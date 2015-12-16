package com.example.rossch.gv_maps;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.widget.*;
import java.lang.reflect.Array;
import java.util.*;

import com.example.rossch.gv_maps.Components.Hallway;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PathActivity extends AppCompatActivity {
    EditText startRoomText, endRoomText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        startRoomText = (EditText) findViewById(R.id.startRoom);
        endRoomText = (EditText) findViewById(R.id.endRoom);

        getRoomData();
    }

    List<ParseObject> myObjects;

    public void getRoomData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MackinacRoomsFinal");
        try {
            List<ParseObject> myObjects = query.find();
            this.myObjects = myObjects;

        }
        catch (com.parse.ParseException e) {
            Log.d("App", e.getMessage());
        }
    }

    ArrayList<String> usedList = new ArrayList<String>();
    List<String> thePath = new ArrayList<>();
    Boolean foundRoomPath = false;


    public void findPath(View view) {
        String startRoom = startRoomText.getText().toString();
        String startHallway = getHallwayOfRoom(startRoom);
        Log.d("start", startHallway);


        String endRoom = endRoomText.getText().toString();
        String endHallway = getHallwayOfRoom(endRoom);
        Log.d("end", endHallway);

        usedList.clear();
        thePath.clear();


        ArrayList<String> attached = getAttachedHallways(startHallway);
        ArrayList<String> path = new ArrayList<>();
        buildPath(path, startHallway, endHallway);

        thePath = thePath.subList(0,getIndexOfValue(endHallway,thePath) + 1);

        String str = "";
        for (String hall : thePath) {
            str += hall + ", ";
        }

        Log.d("LONG PATH", str);

        TextView text = (TextView)findViewById(R.id.directionsText);
        text.setText(str);
    }

    public void buildPath(ArrayList<String> currentPath, String currentHall, String endHall) {
        Log.d("ADDING PATH", currentHall);
        currentPath.add(currentHall);
        usedList.add(currentHall);

        if (!foundRoomPath) {
            if (currentHall.equalsIgnoreCase(endHall)) {
                thePath = currentPath;
                foundRoomPath = true;
            } else {

                ArrayList<String> potentialPaths = getAttachedHallways(currentHall);
                int count = 0;
                for (String hallway: potentialPaths) {
                    if (!usedList.contains(hallway)) {
                        buildPath(currentPath, hallway, endHall);
                    } else {
                        count++;
                    }
                }
                if (count == potentialPaths.size()) {
                    currentPath.remove(currentPath.size()-1);
                }
            }
        }
    }

    public boolean valueHasDuplicate(String val, ArrayList<String> list) {
        int count = 0;
        for (String str : list) {
            if (str.equalsIgnoreCase(val)) {
                count++;
                if (count > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<String> trimPath(ArrayList<String> list, String endHallway) {
        ArrayList<String> newList = list;
        getIndexOfValue(endHallway, newList);

        while(!newList.get(newList.size()-1).equalsIgnoreCase(endHallway)) {
            newList.remove(newList.size()-1);
        }

        return newList;
    }

    public int getIndexOfValue(String val, List<String> list) {
        int count = 0;

        for (String str : list) {
            if (str.equalsIgnoreCase(val)) {
                return count;
            }
            count++;
        }

        return 0;
    }


    public ArrayList<String> getAttachedHallways(String id) {
        for (ParseObject obj:myObjects) {
            String hallwayId = (String) obj.get("Hallway_Id");
            if (hallwayId.equalsIgnoreCase(id)) {
                ArrayList<String> list = (ArrayList) obj.get("Attached_Hallways");
                return list;
            }
        }

        ArrayList<String> empty = new ArrayList<String>();
        Log.d("Returned nothin", "nothin yo");
        return empty;
    }

    public String getHallwayOfRoom(String room) {
        for (ParseObject obj : myObjects) {
            try {
                ArrayList<String> rooms = (ArrayList) obj.get("Rooms");
                for (String r : rooms) {
                    if (r.equalsIgnoreCase(room)) {
                        String hallwayId = (String) obj.get("Hallway_Id");
                        return hallwayId;
                    }
                }
            } catch (Exception e) {
            }
        }
        return "";
    }
}
