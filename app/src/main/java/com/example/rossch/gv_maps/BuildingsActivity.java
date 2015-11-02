package com.example.rossch.gv_maps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.*;
import android.content.*;
import android.widget.TableLayout;
import android.widget.TextView;
import com.parse.Parse;
public class BuildingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buildings);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_buildings, menu);
        return true;
    }

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

    public void goToMain(View view){
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }

    public void macRoomsList(View view){
        Intent intent = new Intent (this, RoomsList.class);
        Bundle bundle = new Bundle();
        bundle.putString("Building", "Mackinac");
        intent.putExtras(bundle);
        startActivity(intent);

    }
}
