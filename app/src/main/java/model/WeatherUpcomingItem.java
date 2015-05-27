package model;

/**
 * Created by kamil on 26.05.15.
 */
public class WeatherUpcomingItem {

    public String day;
    public String date;
    public int min;
    public int max;
    public String description;

    public WeatherUpcomingItem(String day, String date, int min, int max, String description) {
        this.day = day;
        this.date = date;
        this.min = min;
        this.max = max;
        this.description = description;
    }

}
