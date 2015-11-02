package com.example.rossch.gv_maps.Components;

import com.google.android.gms.maps.model.LatLng;



public class DistanceCalculator {

        /**
         * feetBetweenGeoCoordinates
         */
        public static double feetBetweenGeoCoordinates(LatLng a, LatLng b) {
            double theta = a.longitude - b.longitude;
            double dist = Math.sin(deg2rad(a.latitude)) * Math.sin(deg2rad(b.latitude)) + Math.cos(deg2rad(a.latitude)) * Math.cos(deg2rad(b.latitude)) * Math.cos(deg2rad(theta));
            dist = Math.acos(dist);
            dist = rad2deg(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 5280; // 5280 ft = 1 mile
            return dist;
        }

        /**
         * geoCordFromFeetDistance
         */
        public static LatLng geoCordFromFeetDistance(LatLng coordinate, double feet, String direction) {
            double lat = 0.00;
            double lng = 0.00;
            double meters = feet / 3.2808;

            // south
            if (direction == "S") {
                lat = coordinate.latitude + (180/Math.PI) * ((meters * -1) / 6378137);
                lat = roundToSixDecimals(lat);
                lng = coordinate.longitude;
            }
            // north
            else if (direction == "N") {
                lat = coordinate.latitude + (180/Math.PI) * (meters / 6378137);
                lat = roundToSixDecimals(lat);
                lng = coordinate.longitude;
            }
            // west
            else if (direction == "E") {
                lat = coordinate.latitude;
                lng = coordinate.longitude + (180/Math.PI) * (meters/6378137) / Math.cos(Math.PI/180*coordinate.latitude);
                lng = roundToSixDecimals(lng);
            }
            // east
            else if (direction == "W") {
                lat = coordinate.latitude;
                lng = coordinate.longitude + (180/Math.PI) * ((meters * -1)/6378137) / Math.cos(Math.PI/180*coordinate.latitude);
                lng = roundToSixDecimals(lng);
            }
            return new LatLng(lat, lng);
        }

        /**
        * deg2rad
        */
        private static double deg2rad(double deg) {
            return (deg * Math.PI / 180.0);
        }

        /**
         * rad2deg
         */
        private static double rad2deg(double rad) {
            return (rad * 180 / Math.PI);
        }

        /**
         * roundToSixDecimals
         */
        private static double roundToSixDecimals(double number) {
            return Math.round(number*1000000.0) / 1000000.0;
        }

}// END DistanceCalculator.java

