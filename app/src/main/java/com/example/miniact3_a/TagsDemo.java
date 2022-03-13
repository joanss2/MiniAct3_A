package com.example.miniact3_a;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;

import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class TagsDemo extends AppCompatActivity implements
        OnMarkerClickListener,
        OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener{

    private static final LatLng LLEIDA = new LatLng(41.619021, 0.619187);
    private static final LatLng BARCELONA = new LatLng(41.395917, 2.156674);
    private static final LatLng TARRAGONA = new LatLng(41.118538, 1.245129);
    private static final LatLng GIRONA = new LatLng(41.978669, 2.817908);
    private static final LatLng ANDORRA = new LatLng(42.504740, 1.521913);

    private static class CustomTag {
        private final String description;
        private int clickCount;

        public CustomTag(String description) {
            this.description = description;
            clickCount = 0;
        }

        public void incrementClickCount() {
            clickCount++;
        }

        @Override
        public String toString() {
            return "The " + description + " has been clicked " + clickCount + " times.";
        }
    }

    private GoogleMap mMap = null;

    private Marker mLleidaMarker;
    private Marker mBarcelonaMarker;
    private Marker mTarragonaMarker;
    private Marker mGironaMarker;
    private Marker mAndorraMarker;

    private TextView mTagText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tags_demo);

        mTagText = (TextView) findViewById(R.id.tag_text);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        new OnMapAndViewReadyListener(mapFragment, this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        UiSettings mUiSettings = mMap.getUiSettings();

        // Turn off the map toolbar.
        mUiSettings.setMapToolbarEnabled(false);

        // Enable interaction with the map
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setScrollGesturesEnabled(true);
        mUiSettings.setZoomGesturesEnabled(true);
        mUiSettings.setTiltGesturesEnabled(true);
        mUiSettings.setRotateGesturesEnabled(true);

        // Add markers to the map.
        addObjectsToMap();

        // Set listener for markers.
        mMap.setOnMarkerClickListener(this);

        // Create bounds that include all locations of the map.
        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(LLEIDA)
                .include(BARCELONA)
                .include(TARRAGONA)
                .include(GIRONA)
                .include(ANDORRA)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));
    }

    private void addObjectsToMap() {
        // A marker at LLEIDA.
        mLleidaMarker = mMap.addMarker(new MarkerOptions().position(LLEIDA));
        mLleidaMarker.setTag(new CustomTag("Lleida marker"));


        // A marker at BARCELONA.
        mBarcelonaMarker = mMap.addMarker(new MarkerOptions().position(BARCELONA));
        mBarcelonaMarker.setTag(new CustomTag("Barcelona marker"));

        // A marker at TARRAGONA.
        mTarragonaMarker = mMap.addMarker(new MarkerOptions().position(TARRAGONA));
        mTarragonaMarker.setTag(new CustomTag("Tarragona marker"));

        // A marker at GIRONA.
        mGironaMarker = mMap.addMarker(new MarkerOptions().position(GIRONA));
        mGironaMarker.setTag(new CustomTag("Girona marker"));

        // A marker at ANDORRA.
        mAndorraMarker = mMap.addMarker(new MarkerOptions().position(ANDORRA));
        mAndorraMarker.setTag(new CustomTag("Andorra marker"));
    }

    //
    // Click event listeners.
    //

    private void onClick(CustomTag tag) {
        tag.incrementClickCount();
        mTagText.setText(tag.toString());
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        onClick((CustomTag) marker.getTag());
        // We return true to indicate that we have consumed the event and that we do not wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return true;
    }

}