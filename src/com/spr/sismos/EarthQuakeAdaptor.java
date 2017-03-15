package com.spr.sismos;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

class EarthQuakeAdaptor extends BaseAdapter {

	private Context context;
	private List<Earthquake> eQuakes;

	public EarthQuakeAdaptor(Context context, List<Earthquake> eQuakes) {
		this.context = context;
		this.eQuakes = eQuakes;
	}

	@Override
	public int getCount() {
	 	if ( eQuakes != null) {
	 	return eQuakes.size();
	 	} else {
	       return 0;
	 	}
	}

	@Override
	public Object getItem(int position) {
		return eQuakes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater;

		View view;

		String type = eQuakes.get(position).getSource();
		TextView tv1, tv2;

		if (type == "XML") {
			if (convertView == null) {

				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.xml_layout, parent, false);

			} else {
				view = convertView;
			}

			Subroutines s = new Subroutines(eQuakes.get(position));
			
			tv1 = (TextView) view.findViewById(R.id.xmltext1);
			tv1.setText(Html.fromHtml(s.getFirstLine()));
		 

			tv2 = (TextView) view.findViewById(R.id.xmltext2);
			tv2.setText(s.getSecondLine());
		 
			return view;
		}

		if (type == "JSON") {
			if (convertView == null) {

				inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = inflater.inflate(R.layout.json_layout, parent, false);
			} else {
				view = convertView;
			}
			Subroutines s = new Subroutines(eQuakes.get(position));

			tv1 = (TextView) view.findViewById(R.id.jsontext1);
			tv1.setText(Html.fromHtml(s.getFirstLine()));
 
			
			tv2 = (TextView) view.findViewById(R.id.jsontext2);
			tv2.setText(s.getSecondLine());
			

			return view;
		}

		return null;

	}

}