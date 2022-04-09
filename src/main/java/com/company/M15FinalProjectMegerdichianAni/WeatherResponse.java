package com.company.M15FinalProjectMegerdichianAni;

public class WeatherResponse {

    public String name;
    public Sys sys;
    public WeatherInfo main;

    public class Sys {
        public String country;
    }
    public class WeatherInfo {
        public String temp;
        public String feels_like;
        public String temp_min;
        public String temp_max;
        public String humidity;
    }
}
