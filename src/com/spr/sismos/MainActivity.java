package com.spr.sismos;


import java.util.List;


 


import android.app.AlertDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import android.content.Intent;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
 


public class MainActivity extends ActionBarActivity implements
		ActionBar.TabListener {

	static List<Earthquake> xmlQuakes = null;
	static List<Earthquake> jsonQuakes = null;
	Earthquake e = null;
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
	ViewPager mViewPager;
    ActionBar actionBar;
	

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		actionBar = getSupportActionBar();
		actionBar.setHomeButtonEnabled(false);

		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		 
		
			  	
	 	
	 	
	 	 try {
	 		 ReadXMLTask rXML = new ReadXMLTask(this);
	 	 rXML.execute();
	 	} catch (Exception e) {
	 		 noInternet();
	 	 }
	 	 
		 
	 	 try {
		ReadJSONTask ReadJSON = new ReadJSONTask( );
		ReadJSON.execute();
	 	 } catch (Exception e) {
	 		 noInternet();
	 	 }
 
 
		
	}
	
 	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
	    //No call for super(). Bug on API Level > 11.
	}
	
	private void setXMLData(List<Earthquake> xmlData) {
	
		xmlQuakes = xmlData;
		 

		if (xmlQuakes == null) {
			noInternet();
			return;
		}
 

	Tab tab1 = actionBar
			.newTab()
			.setText("uChile")
			.setTabListener(
					new TabListener(this, "uChile", XMLFragment.class));
	actionBar.addTab(tab1);

		
	}
	
	private void setJSONData(List<Earthquake> jsonData) {
		
		jsonQuakes = jsonData ;
		
		if (jsonQuakes  == null) {
			noInternet();
			return;
		}
		Tab tab2 = actionBar
				.newTab()
				.setText("USGS")
				.setTabListener(
						new TabListener(this, "USGS", JSONFragment.class));
		actionBar.addTab(tab2); 
	} 
	
	
	class ReadXMLTask extends AsyncTask<String, Integer, List<Earthquake>> {
		private ProgressDialog dialog;
		private Context context;
		
		List<Earthquake> el = null;

		private ActionBarActivity activity;

		public ReadXMLTask(ActionBarActivity activity) {
			this.activity = activity;
			context = activity;
			dialog = new ProgressDialog(context);
		}

		@Override
		protected void onPreExecute() {
			 dialog.setMessage("Descargando datos...");
			 dialog.show();

		}

		protected void onPostExecute(List<Earthquake> result) {

			 dialog.dismiss();
			 setXMLData(el);

		}

		protected List<Earthquake> doInBackground(String... s) {
			ReadGeoXML xmlQ = new ReadGeoXML();
			
                try {
				el = xmlQ.getJsonEarthquakes();
                } catch(Exception e) {
                	Log.d("background",e.getMessage());
                }

			return el;

		}

	}
	
	
	class ReadJSONTask extends AsyncTask<String, Integer, List<Earthquake>> {
		List<Earthquake> el = null;
		
		protected List<Earthquake> doInBackground(String... s) {
			readGeoJSON jsonQ = new readGeoJSON();
			

			 try {
				el = jsonQ.getJsonEarthquakes();
				
			  } catch(Exception e) {
              	
              }
		 
			return el;

		}

		protected void onPostExecute(List<Earthquake> result) {

			 
		 	 setJSONData(el);

		}
		
	}


	

	 
	 
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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

			case R.id.action_refresh:

				Intent i2 = getBaseContext().getPackageManager()
						.getLaunchIntentForPackage(
								getBaseContext().getPackageName());
				i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i2);

			default:
				return super.onOptionsItemSelected(item);
			}
		} catch (Exception e) {

		}
		return false;

	}

	class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int pos) {

			switch (pos) {

			case 0:
				XMLFragment xmlFragment = new XMLFragment();
				return xmlFragment;

			case 1:
				JSONFragment jsonFragment = new JSONFragment();
				return jsonFragment;
			}

			return null;
		}

		@Override
		public int getCount() {

			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return "Page Title";
		}

	}

	public static class XMLFragment extends ListFragment {
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			EarthQuakeAdaptor adapter = new EarthQuakeAdaptor(getActivity(),
					xmlQuakes);
			setListAdapter(adapter);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					QuakeDetail.class);

			class EarthQuakeTemp {

				Earthquake e;

				EarthQuakeTemp(int position) {
					e = xmlQuakes.get(position);
				}

				private Earthquake getQuake() {
					return e;
				}
			}

			EarthQuakeTemp te = new EarthQuakeTemp(position);

			Earthquake e = te.getQuake();

			intent.putExtra("longitude", e.getLongitude());
			intent.putExtra("latitude", e.getLatitude());
			intent.putExtra("FirstLine", e.getFirstLine());
			intent.putExtra("SecondLine", e.getSecondLine());
			intent.putExtra("ClosetCity", e.getClosetCity());
			intent.putExtra("SecondClosetCity", e.getSecondClosetCity());
			intent.putExtra("ThirdClosetCity", e.getThirdClosetCity());
			intent.putExtra("FourthClosetCity", e.getFourthClosetCity());
			intent.putExtra("SubjectLine", e.getSubjectLine());
			intent.putExtra("link", e.getLink());

			startActivity(intent);
		}

	}

	public static class JSONFragment extends ListFragment {
		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			EarthQuakeAdaptor adapter = new EarthQuakeAdaptor(getActivity(),
					jsonQuakes);
			setListAdapter(adapter);

		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			Intent intent = new Intent(getActivity().getApplicationContext(),
					QuakeDetail.class);

			class EarthQuakeTemp {

				Earthquake e;

				EarthQuakeTemp(int position) {
					e = jsonQuakes.get(position);
				}

				private Earthquake getQuake() {
					return e;
				}
			}

			EarthQuakeTemp te = new EarthQuakeTemp(position);

			Earthquake e = te.getQuake();

			intent.putExtra("longitude", e.getLongitude());
			intent.putExtra("latitude", e.getLatitude());
			intent.putExtra("FirstLine", e.getFirstLine());
			intent.putExtra("SecondLine", e.getSecondLine());
			intent.putExtra("ClosetCity", e.getClosetCity());
			intent.putExtra("SecondClosetCity", e.getSecondClosetCity());
			intent.putExtra("ThirdClosetCity", e.getThirdClosetCity());
			intent.putExtra("FourthClosetCity", e.getFourthClosetCity());
			intent.putExtra("SubjectLine", e.getSubjectLine());
			intent.putExtra("link", e.getLink());

			startActivity(intent);
		}
	}

	@Override
	public void onTabReselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTabSelected(Tab tab,
			android.support.v4.app.FragmentTransaction arg1) {
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab arg0,
			android.support.v4.app.FragmentTransaction arg1) {
		// TODO Auto-generated method stub

	}
	
	private void restartSelf() {
		finish();
	}

	public void onClick(View v) {
		restartSelf();
	}

	void noInternet() {

		 
		AlertDialog.Builder dialog = new AlertDialog.Builder(this)
         .setTitle("Falla")
         .setCancelable(false)
         .setMessage("No puedo descargar datos...pincha Si para probar de nuevo.")  
         .setPositiveButton("Si poh", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
            	 Intent i2 = getBaseContext().getPackageManager()
 						.getLaunchIntentForPackage(
 								getBaseContext().getPackageName());
 				i2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
 				startActivity(i2);
             }
         })
         .setNegativeButton("No poh", new DialogInterface.OnClickListener() {
             public void onClick(DialogInterface dialog, int id) {
                 // User cancelled the dialog
             }
         });
         dialog.show();
          

	}
		
	 
	 
}
	
