package com.company.M15FinalProjectMegerdichianAni;

public class SpaceResponse {

    private Position iss_position;

    public Position getIss_position() {
        return iss_position;
    }

    public void setIss_position(Position iss_position) {
        this.iss_position = iss_position;
    }

    public class Position{
        private String latitude;
        private String longitude;

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
