package utilities;

import org.json.JSONException;
import org.json.JSONObject;

import model.WeatherAdditional;
import model.WeatherBasic;
import model.WeatherUpcomingItem;

/**
 * Created by kamil on 27.05.15.
 */
public class WeatherParser {

    public static WeatherBasic getWeatherBasic(String result) {

        String date = null;
        String description = null;
        int temperature = 0;
        double pressure = 0;
        String location = null;
        double latitude = 0;
        double longtitude = 0;

        try {
            JSONObject jObject = new JSONObject(result);

            date = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getString("lastBuildDate");
            description = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("text");
            temperature = Integer.parseInt(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONObject("condition").getString("temp"));
            pressure = Double.parseDouble(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getString("pressure"));
            location = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("location").getString("city");
            latitude = Double.parseDouble(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getString("lat"));
            longtitude = Double.parseDouble(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getString("long"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new WeatherBasic(date, description, temperature, pressure, location, latitude, longtitude);

    }

    public static WeatherAdditional getWeatherAdditional(String result) {

        double speed = 0;
        int direction = 0;
        int humidity = 0;
        int visibility = 0;
        String sunrise = null;
        String sunset = null;

        try {
            JSONObject jObject = new JSONObject(result);

            speed = Double.parseDouble(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getString("speed"));
            direction = Integer.parseInt(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("wind").getString("direction"));
            humidity = Integer.parseInt(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").getString("humidity"));
            visibility = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("atmosphere").optInt("visibility", 0);
            sunrise = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunrise");
            sunset = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("astronomy").getString("sunset");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new WeatherAdditional(speed, direction, humidity, visibility, sunrise, sunset);

    }

    public static WeatherUpcomingItem[] getWeatherUpcomingItems(String result) {

        //result channel item forecast[as array]
        WeatherUpcomingItem[] weatherUpcomingItems = new WeatherUpcomingItem[5];
        String day = null;
        String date = null;
        int min = 0;
        int max = 0;
        String description = null;

        for (int i = 0; i<5; ++i) {
            try {
                JSONObject jObject = new JSONObject(result);

                day = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast").getJSONObject(i).getString("day");
                date = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast").getJSONObject(i).getString("date");
                min = Integer.parseInt(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast").getJSONObject(i).getString("low"));
                max = Integer.parseInt(jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast").getJSONObject(i).getString("high"));
                description = jObject.getJSONObject("query").getJSONObject("results").getJSONObject("channel").getJSONObject("item").getJSONArray("forecast").getJSONObject(i).getString("text");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            weatherUpcomingItems[i] = new WeatherUpcomingItem(day, date, min, max, description);
        }

        return weatherUpcomingItems;

    }

}
