package com.example.kamil.weather;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import model.WeatherAdditional;
import model.WeatherBasic;
import model.WeatherUpcomingItem;
import utilities.InternetConnection;
import utilities.WeatherParser;
import utilities.WeatherRequest;
import utilities.WoeidParser;
import utilities.WoeidRequest;


public class MainActivity extends ActionBarActivity
        implements BasicInformationFragment.OnFragmentInteractionListener,
        AdditionalInformationFragment.OnFragmentInteractionListener,
        UpcomingDaysFragment.OnFragmentInteractionListener {

    private static final String LOCATION_KEY = "Location";
    private static final String WOEID_KEY = "WOEID";
    public static String WOEID = "505120";
    private static final String UNITS_KEY = "Units";
    public static String UNITS = "c";
    private SharedPreferences settings;
    private static SharedPreferences.Editor settingsEditor;
    public static WeatherBasic currentBasic;
    public static WeatherAdditional currentAdditional;
    public static WeatherUpcomingItem[] currentUpcomingItems;
    public static boolean refreshed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        settings = getPreferences(MODE_PRIVATE);
        settingsEditor = settings.edit();
        if (settings.contains(UNITS_KEY)) {
            UNITS = settings.getString(UNITS_KEY, "c");
        }
        WOEID = settings.getString(WOEID_KEY, "505120");

        if (!InternetConnection.isAvailable(this) && isFirstRun()) {
            showInternetDialog();
        } else if (!InternetConnection.isAvailable(this) && !isFirstRun()) {
            showInternetDialog();
            String result = loadRequestFromFile(this);
            MainActivity.currentBasic = WeatherParser.getWeatherBasic(result);
            MainActivity.currentAdditional = WeatherParser.getWeatherAdditional(result);
            MainActivity.currentUpcomingItems = WeatherParser.getWeatherUpcomingItems(result);
            MainActivity.refreshed = true;
        } else if (InternetConnection.isAvailable(this) && isFirstRun()) {
            setCurrentLocation();
        } else {
            boolean outdated = true;
            if (outdated) {
                new WeatherRequest(this).execute( WOEID );
            } else {
                String result = loadRequestFromFile(this);
                MainActivity.currentBasic = WeatherParser.getWeatherBasic(result);
                MainActivity.currentAdditional = WeatherParser.getWeatherAdditional(result);
                MainActivity.currentUpcomingItems = WeatherParser.getWeatherUpcomingItems(result);
                MainActivity.refreshed = true;
            }
        }
    }

    private void runWoeid(String location) {
        new WoeidRequest(this).execute(location.replace(" ", "%20"));
    }

    private void setCurrentLocation() {
        final EditText locationText = new EditText(this);
        locationText.setLines(1);
        new AlertDialog.Builder(this)
                .setTitle(R.string.location_title)
                .setView(locationText)
                .setPositiveButton(R.string.internet_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        runWoeid(locationText.getText().toString());
                    }
                })
                .setNegativeButton(R.string.location_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private boolean isFirstRun() {
        return !settings.contains(LOCATION_KEY);
    }

    public static void saveSettings() {
        settingsEditor.putString(LOCATION_KEY, currentBasic.location);
        settingsEditor.putString(WOEID_KEY, WOEID);
        settingsEditor.commit();
    }

    public static void saveRequestToFile(Context context, String result) {

        String FILENAME = "file";
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(result.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String loadRequestFromFile(Context context) {

        String FILENAME = "file";
        FileInputStream fis = null;
        StringBuilder sb = null;
        try {
            fis = context.openFileInput(FILENAME);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();

    }

    private void showInternetDialog() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.internet_title)
                .setMessage(R.string.internet_message)
                .setPositiveButton(R.string.internet_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
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
        if (id == R.id.action_refresh) {
            new WeatherRequest(this).execute( WOEID );
            return true;
        }

        if (id == R.id.action_location) {
            setCurrentLocation();
            return true;
        }

        if (item.getGroupId() == R.id.group_units) {
            item.setChecked( !item.isChecked() );
        }

        if (id == R.id.action_celsius) {
            UNITS = "c";
            new WeatherRequest(this).execute( WOEID );
            return true;
        }

        if (id == R.id.action_fahrenheit) {
            UNITS = "f";
            new WeatherRequest(this).execute( WOEID );
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onFragmentInteraction(String id) {

    }
}
