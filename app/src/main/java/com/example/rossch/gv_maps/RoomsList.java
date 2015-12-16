package com.example.rossch.gv_maps;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import java.util.ArrayList;
import java.util.List;

public class RoomsList extends AppCompatActivity {

    TableLayout tableMain;
    List<ParseObject> myObjects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String building;

        tableMain = (TableLayout) findViewById(R.id.roomsTable);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_list);
        Bundle bundle = getIntent().getExtras();
        building = bundle.getString("Building");

        getRoomData(building);
    }

    //Actually makes the request to the parse database and grabs all of the hallways with rooms
    public void getRoomData(String buildingName) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Rooms");
        query.whereExists("Name");
        query.orderByAscending("Name");
        try {
            List<ParseObject> myObjects = query.find();
            storeRoomData(myObjects);
        }
        catch (com.parse.ParseException e) {
            Log.d("App", e.getMessage());
        }

    }

    public void storeRoomData(List<ParseObject> newObjects){
        myObjects = newObjects;
        tableMain = (TableLayout) findViewById(R.id.roomsTable);
        for (int i = 0; i < myObjects.size(); i++) {
            final ParseObject temp = myObjects.get(i);
            TableRow t1 = new TableRow(this);

            t1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
            TextView myText = new TextView(this);
            String name = (String) temp.get("Name");
            String type = (String) temp.get("Type");
            myText.setText(name + " - " + type );
            myText.setTextSize(TypedValue.COMPLEX_UNIT_PX, 80);
            myText.setTextColor(Color.parseColor("#FFFFFF"));

            //Set on click listener for every new table row
            t1.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), MapsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("room", (String) temp.get("Name"));
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });


            t1.addView(myText);
            tableMain.addView(t1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rooms_list, menu);
        return true;
    }

//    @Override
//    protected void onResume(){
//        super.onResume();
//
//        linearMain = (LinearLayout) findViewById(R.id.roomLinear);
//        linearMain.removeAllViews();
//        linearMain.invalidate();
//
//        addLayout();
//    }
//
//    public void addLayout() {
//        TableLayout myTable = new TableLayout(getApplicationContext());
//        TableLayout.LayoutParams tab_lp = new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.FILL_PARENT);
//        myTable.setLayoutParams(tab_lp);
//
//        TableRow row = new TableRow(this);
//
//        final TextView tvProduct = new TextView(getApplicationContext());
//
//        LinearLayout.LayoutParams lp_l3 = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, (TableLayout.LayoutParams.WRAP_CONTENT));
//        tvProduct.setLayoutParams(lp_l3);
//        tvProduct.setText("new text");
//
//        row.addView(tvProduct,  new TableRow.LayoutParams(1));
//        myTable.addView(row,new TableLayout.LayoutParams());
//
//        linearMain.addView(myTable);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
