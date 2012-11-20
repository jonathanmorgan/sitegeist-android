package com.sunlightfoundation.sitegeist.android;

import java.util.List;

import android.location.Location;
import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.sunlightfoundation.sitegeist.android.utils.Utils;

public class FindLocation extends MapActivity implements MyMapView.MapTapListener {

	private MyMapView map;
	private MapController controller;
	private TextView debugChosen, debugCurrent;
	private MyLocationOverlay location;
	
	private GestureDetector detector;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_location);
        
        setupControls();
        setupMap();
    }
	
	private void setupControls() {
		debugChosen = (TextView) findViewById(R.id.debug_chosen);
	    debugCurrent = (TextView) findViewById(R.id.debug_current);
	}
	
	private void setupMap() {
		map = (MyMapView) findViewById(R.id.map);
		map.setMapTapListener(this);
		
		controller = map.getController();
	    map.setBuiltInZoomControls(true);
	    
	    initialCenter();
	    location = new MyLocationOverlay(this, map);
	    List<Overlay> overlays = map.getOverlays();
	    overlays.add(location);
	}
	
	
	
	@Override
	protected void onResume() {
		super.onResume();
		if (location != null)
			location.enableMyLocation();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		if (location != null)
			location.disableMyLocation();
	}
	
	@Override
	public void onMapTap(GeoPoint point) {
		debugChosen.setText(point.toString());
	}
	
	private void initialCenter() {
		GeoPoint center = quickGetLocation();
	    if (center != null) {
	    	controller.setZoom(15);
			controller.setCenter(center);
			debugCurrent.setText(center.toString());
	    } else
	    	controller.setZoom(4);
	}
	
	private GeoPoint quickGetLocation() {
    	Location location = Utils.lastKnownLocation(this);
    	if (location != null) {
    		int lat = (int) (location.getLatitude() * 1000000);
    		int lng = (int) (location.getLongitude() * 1000000);
    		return new GeoPoint(lat, lng);
    	}
    	return null;
    }
	
	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

}