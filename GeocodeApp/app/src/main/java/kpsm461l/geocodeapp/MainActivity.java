package kpsm461l.geocodeapp;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.*;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.google.android.*;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;


public class MainActivity extends Activity {

    private MapView mapView;
    private GoogleMap gMap;
    double latitude;
    double longitude;
    List<Address> geocodeMatches = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = new MapView(this.getApplicationContext());
        gMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

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
        }

        LatLng LatLong = new LatLng(latitude, longitude);
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLong, 15));

    }


    public void changeNormal(View view) {
        gMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

    }

    public void changeSatellite(View view) {
        gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

    }

    public void changeHybrid(View view) {
        gMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

    }

    public void changeTerrain(View view) {
        gMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }
}
