package model;

/**
 * Created by kamil on 26.05.15.
 */
public class WeatherAdditional {

    public double speed;
    public int direction;
    public int humidity;
    public int visibility;
    public String sunrise;
    public String sunset;

    public WeatherAdditional(double speed, int direction, int humidity, int visibility, String sunrise, String sunset) {
        this.speed = speed;
        this.direction = direction;
        this.humidity = humidity;
        this.visibility = visibility;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

}
