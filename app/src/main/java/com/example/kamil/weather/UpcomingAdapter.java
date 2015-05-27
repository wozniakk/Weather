package com.example.kamil.weather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import model.WeatherUpcomingItem;

/**
 * Created by kamil on 27.05.15.
 */
public class UpcomingAdapter extends ArrayAdapter<WeatherUpcomingItem> {

    private Context context;

    public UpcomingAdapter(Context context) {
        super(context, 0, MainActivity.currentUpcomingItems);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        WeatherUpcomingItem item = MainActivity.currentUpcomingItems[position];
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.upcoming_item, parent, false);
        }
        // Lookup view for data population
        TextView firstLine = (TextView) convertView.findViewById(R.id.firstLine);
        TextView secondLine = (TextView) convertView.findViewById(R.id.secondLine);
        TextView thirdLine = (TextView) convertView.findViewById(R.id.thirdLine);
        // Populate the data into the template view using the data object
        firstLine.setText( item.day + ", "  + item.date );
        secondLine.setText( context.getString(R.string.upcoming_from) + " " + item.min + "° " + MainActivity.UNITS.toUpperCase() + " " + context.getString(R.string.upcoming_to) + " " + item.max + "° " + MainActivity.UNITS.toUpperCase() );
        thirdLine.setText( item.description );
        // Return the completed view to render on screen
        return convertView;
    }

}
