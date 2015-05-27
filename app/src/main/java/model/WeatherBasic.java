package model;

/**
 * Created by kamil on 26.05.15.
 */
public class WeatherBasic {

    public String date;
    public String description;
    public int temperature;
    public double pressure;
    public String location;
    public double latitude;
    public double longtitude;

    public WeatherBasic(String date, String description, int temperature, double pressure, String location, double latitude, double longtitude) {
        this.date = date;
        this.description = description;
        this.temperature = temperature;
        this.pressure = pressure;
        this.location = location;
        this.latitude = latitude;
        this.longtitude = longtitude;
    }

}
