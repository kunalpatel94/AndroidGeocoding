package kpsm461l.geocodeapp;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.*;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.*;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {

    private MapView mapView;
    private GoogleMap gMap;
    double latitude;
    double longitude;
    List<Address> geocodeMatches = null;
    List<Address> History = new ArrayList<Address>();
    int mapType = 0;
    boolean Traffic = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = new MapView(this.getApplicationContext());
        gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gMap.setMyLocationEnabled(true);
        gMap.setBuildingsEnabled(true);
        gMap.setTrafficEnabled(Traffic);

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

    public void getLocation(View view) {
        EditText edittext = (EditText) findViewById(R.id.et_place);
        String place = edittext.getText().toString();

        InputMethodManager imm = (InputMethodManager)getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edittext.getWindowToken(), 0);



        try {
            geocodeMatches =
                    new Geocoder(this).getFromLocationName(
                            place, 1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (!geocodeMatches.isEmpty()) {
            latitude = geocodeMatches.get(0).getLatitude();
            longitude = geocodeMatches.get(0).getLongitude();
            History.add(geocodeMatches.get(0));
            Toast.makeText(this, geocodeMatches.get(0).getAddressLine(0), Toast.LENGTH_SHORT).show();
        }

        LatLng LatLong = new LatLng(latitude, longitude);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLong, 15));

    }

    public void getHistory(View view) {

        for (int i = 0; i < History.size(); i += 1) {
            gMap.addMarker(new MarkerOptions()
                    .position(new LatLng(History.get(i).getLatitude(), History.get(i).getLongitude()))
                    .title(History.get(i).getAddressLine(0)));
        }

    }



    public void changeTerrain(View view) {
        if(mapType == 0){
            gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            Toast.makeText(this, "Satellite", Toast.LENGTH_SHORT).show();
        }else if(mapType == 1) {
            gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            Toast.makeText(this, "Hybrid", Toast.LENGTH_SHORT).show();
        } else if(mapType == 2) {
            gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
            Toast.makeText(this, "Terrain", Toast.LENGTH_SHORT).show();
        } else if(mapType == 3) {
            gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            Toast.makeText(this, "Normal", Toast.LENGTH_SHORT).show();
        }
        mapType =(mapType + 1)%4;
    }

    public void changeTraffic(View view) {
        if(Traffic){
            gMap.setTrafficEnabled(false);
            Toast.makeText(this, "Hide Traffic", Toast.LENGTH_SHORT).show();
        }else {
            gMap.setTrafficEnabled(true);
            Toast.makeText(this, "Show Traffic", Toast.LENGTH_SHORT).show();
        }
        Traffic = !Traffic;
    }



}
