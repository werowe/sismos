package com.spr.sismos;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.app.FragmentManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.app.Dialog;
import android.content.Intent;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class QuakeDetail extends FragmentActivity {

	Earthquake quake;

	String shareSubject, shareTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quake_detail);
		
		 Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		  toolbar.setLogo(R.drawable.ic_launcher);
		  toolbar.setTitle("Sismos Chile");
		  
		   toolbar.inflateMenu(R.menu.quake_detail);
		Bundle extras = getIntent().getExtras();

		
		 
	            
		// Getting status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) {
			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();
		}

		double longitude = extras.getDouble("longitude");
		double latitude = extras.getDouble("latitude");
		String FirstLine = extras.getString("FirstLine");
		String SecondLine = extras.getString("SecondLine");
		String SubjectLine = extras.getString("SubjectLine");
		String SecondClosetCity = extras.getString("SecondClosetCity");
		String ThirdClosetCity = extras.getString("ThirdClosetCity");
		String FourthClosetCity = extras.getString("FourthClosetCity");
		// String link = extras.getString("link");
		
		String link = " http://bit.ly/1wG9FxG";

		shareTxt = "#sismo " + SubjectLine + " #Chile " + link;
		shareSubject = SubjectLine;

		// Get a handle to the Map Fragment
		GoogleMap map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();

		LatLng quakeLocation = new LatLng(latitude, longitude);
		Marker quakeMarker = map.addMarker(new MarkerOptions().position(
				quakeLocation).title(FirstLine));

		map.moveCamera(CameraUpdateFactory.newLatLngZoom(quakeLocation, 7));
		 

		TextView tv1 = (TextView) findViewById(R.id.line1);
		tv1.setText(Html.fromHtml(FirstLine));
		TextView tv2 = (TextView) findViewById(R.id.line2);
		tv2.setText(SecondLine);

		TextView tv4 = (TextView) findViewById(R.id.line4);
		tv4.setText(SecondClosetCity);
		TextView tv5 = (TextView) findViewById(R.id.line5);
		tv5.setText(ThirdClosetCity);
		TextView tv6 = (TextView) findViewById(R.id.line6);
		tv6.setText(FourthClosetCity);
		
		 toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
	            @Override
	            public boolean onMenuItemClick(MenuItem menuItem) {
	 
	                switch (menuItem.getItemId()){
	                case R.id.action_share:

	    				Intent sharingIntent = new Intent(
	    						android.content.Intent.ACTION_SEND);
	    				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
	    						shareSubject);
	    				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
	    						shareTxt);
	    				sharingIntent.setType("text/plain");
	    				startActivity(Intent.createChooser(sharingIntent,
	    						"compartir via"));
	    				return true;
	                }
	 
	                return false;
	            }
		 });
		 
		 

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.quake_detail, menu);

		return super.onCreateOptionsMenu(menu);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		try {
			switch (item.getItemId()) {
			case R.id.action_settings:
				String url = "http://southernpacificreview.com/sismos-chile/";
				Intent i = new Intent(Intent.ACTION_VIEW);
				i.setData(Uri.parse(url));
				startActivity(i);
				return true;
			case R.id.action_share:

				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						shareSubject);
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						shareTxt);
				sharingIntent.setType("text/plain");
				startActivity(Intent.createChooser(sharingIntent,
						"compartir via"));

			default:
				return super.onOptionsItemSelected(item);
			}
		} catch (Exception e) {

		}
		return false;

	}
}
