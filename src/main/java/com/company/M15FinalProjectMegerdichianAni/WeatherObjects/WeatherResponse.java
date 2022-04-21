package com.company.M15FinalProjectMegerdichianAni.WeatherObjects;
import java.util.ArrayList;
public class WeatherResponse {

    private String name;
    private Sys sys;
    private Main main;
    private Clouds clouds;
    private Coord coord;
    private Wind wind;
    private ArrayList<Weather> weathers;
    private String base;
    private double visibility;
    private double timezone;
    private double id;
    private double cod;
    private double dt;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public ArrayList<Weather> getWeathers() {
        return weathers;
    }

    public void setWeathers(ArrayList<Weather> weathers) {
        this.weathers = weathers;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public double getVisibility() {
        return visibility;
    }

    public void setVisibility(double visibility) {
        this.visibility = visibility;
    }

    public double getTimezone() {
        return timezone;
    }

    public void setTimezone(double timezone) {
        this.timezone = timezone;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public double getCod() {
        return cod;
    }

    public void setCod(double cod) {
        this.cod = cod;
    }

    public double getDt() {
        return dt;
    }

    public void setDt(double dt) {
        this.dt = dt;
    }
}
