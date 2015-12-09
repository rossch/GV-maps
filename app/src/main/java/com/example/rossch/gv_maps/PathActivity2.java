package com.example.rossch.gv_maps;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class PathActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getRoomData("");
        TextView text = (TextView) findViewById(R.id.textView3);
        text.setText(listStr);
    }

    public void getRoomData(String buildingName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("MackinacRoomsFinal");
        query.whereExists("Rooms");
        try {
            List<ParseObject> myObjects = query.find();
            storeRoomData(myObjects);

        }
        catch (com.parse.ParseException e) {
            Log.d("App", e.getMessage());
        }

    }

    List<ParseObject> myObjects;
    String listStr = "";

    public void storeRoomData(List<ParseObject> newObjects) {
        String start = "A-1-101";
        String startHallway = "";
        String end = "B-1-104";
        String endHallway = "";

        myObjects = newObjects;

        Boolean foundStartRoom = false;
        ParseObject startingObj;



        while (!foundStartRoom) {
            String hallway = "";
            for (int i = 0; i < myObjects.size(); i++) {
                ParseObject temp = myObjects.get(i);
                hallway = (String) temp.get("Hallway_Id");
                final ArrayList tempRoomsArray = (ArrayList) temp.get("Rooms");
                for (int j = 0; j < tempRoomsArray.size(); j++) {
                    String str = (String) tempRoomsArray.get(j);

                    if (str.equalsIgnoreCase(start)) {
                        startHallway = hallway;
                        listStr += "Starting from Room " + start + " in Hallway " + startHallway +"\n";
                        foundStartRoom = true;
                        startingObj = temp;
                    }
                }
            }
        }

        Boolean foundEndRoom = false;
        ParseObject endingObj;

        while (!foundEndRoom) {
            String hallway = "";
            for (int i = 0; i < myObjects.size(); i++) {
                ParseObject temp = myObjects.get(i);
                hallway = (String) temp.get("Hallway_Id");
                final ArrayList tempRoomsArray = (ArrayList) temp.get("Rooms");
                for (int j = 0; j < tempRoomsArray.size(); j++) {
                    String str = (String) tempRoomsArray.get(j);

                    if (str.equalsIgnoreCase(end)) {
                        endHallway = hallway;

                        listStr += "Ending at Room " + end + " in Hallway " + endHallway +"\n";
                        foundEndRoom = true;
                        endingObj = temp;
                    }
                }
            }
        }

        Boolean foundEndHallway = false;

        while (!foundEndHallway) {


        }



    }

    ArrayList<ArrayList<String>> pathsFound = new ArrayList<ArrayList<String>>();
    ArrayList<String> path = new ArrayList<String>();

    public void findPath(String start, String end) {
        ParseObject currentHallway = getParseObject(start);

    }

    public ParseObject getParseObject(String hallway) {
        for (int i = 0; i < myObjects.size(); i++) {
            ParseObject temp = myObjects.get(i);
            if (temp.get("Hallway_Id").toString().equalsIgnoreCase(hallway)) {
                return temp;
            }
        }
        return null;
    }
}
