package com.example.sebastian.myapplication;

import android.location.Location;
import android.os.Bundle;
import android.support.constraint.solver.ArrayLinkedVariables;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TestLocation testLocation = new TestLocation(MainActivity.this);

    ArrayList<String> listItems = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Button btngetLocation = (Button) findViewById(R.id.btnGetLocation);
        btngetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                testLocation = new TestLocation(MainActivity.this);
                Location loc = testLocation.getLocation();

                if (loc != null){
                    adapter.add( testLocation.getMethod() +  " Lat : " + loc.getLatitude() + " Long : " + loc.getLongitude() );
                }
                else{
                    adapter.add("no se pudo obtener la localizacion");
                }
                adapter.notifyDataSetChanged();

                //TextView txtLong = (TextView) findViewById(R.id.longLoc);
                //txtLong.setText(loc.getLongitude() + "");
            }
        });

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItems);

        ListView lstView = (ListView) findViewById(R.id.listView);
        lstView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
