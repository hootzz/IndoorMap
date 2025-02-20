package com.example.navermapapi.coreModule.api.location.model;

import androidx.annotation.NonNull;
import com.example.navermapapi.coreModule.api.environment.model.EnvironmentType;

public class LocationData {

    private final double latitude;
    private final double longitude;
    private final float accuracy;
    private final float altitude;
    private final float bearing;
    private final float speed;
    private final EnvironmentType environment;
    private final long timestamp;
    private final String provider;
    private final float confidence;

    // 추가된 필드
    private final double offsetX;
    private final double offsetY;

    private LocationData(Builder builder) {
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.accuracy = builder.accuracy;
        this.altitude = builder.altitude;
        this.bearing = builder.bearing;
        this.speed = builder.speed;
        this.environment = builder.environment;
        this.provider = builder.provider;
        this.confidence = builder.confidence;
        this.timestamp = System.currentTimeMillis();
        // 추가된 필드 초기화
        this.offsetX = builder.offsetX;
        this.offsetY = builder.offsetY;
    }

    // Getter methods
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public float getAccuracy() { return accuracy; }
    public float getAltitude() { return altitude; }
    public float getBearing() { return bearing; }
    public float getSpeed() { return speed; }
    public EnvironmentType getEnvironment() { return environment; }
    public String getProvider() { return provider; }
    public float getConfidence() { return confidence; }
    public long getTimestamp() { return timestamp; }

    // 추가된 Getter methods
    public double getOffsetX() { return offsetX; }
    public double getOffsetY() { return offsetY; }

    public boolean isValid() {
        return accuracy <= 50.0f &&
                latitude >= -90 && latitude <= 90 &&
                longitude >= -180 && longitude <= 180 &&
                confidence >= 0.0f && confidence <= 1.0f;
    }

    public float distanceTo(@NonNull LocationData other) {
        float[] results = new float[1];
        android.location.Location.distanceBetween(
                this.latitude, this.longitude,
                other.latitude, other.longitude,
                results
        );
        return results[0];
    }

    public static class Builder {
        private double latitude;
        private double longitude;
        private float accuracy = 0.0f;
        private float altitude = 0.0f;
        private float bearing = 0.0f;
        private float speed = 0.0f;
        private EnvironmentType environment = EnvironmentType.OUTDOOR;
        private String provider = "GPS";
        private float confidence = 1.0f;

        // 추가된 필드 초기값 설정
        private double offsetX = 0.0;
        private double offsetY = 0.0;

        public Builder(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }

        // 새로운 생성자 추가: LocationData 객체를 받아서 필드 복사
        public Builder(@NonNull LocationData locationData) {
            this.latitude = locationData.getLatitude();
            this.longitude = locationData.getLongitude();
            this.accuracy = locationData.getAccuracy();
            this.altitude = locationData.getAltitude();
            this.bearing = locationData.getBearing();
            this.speed = locationData.getSpeed();
            this.environment = locationData.getEnvironment();
            this.provider = locationData.getProvider();
            this.confidence = locationData.getConfidence();
            // 추가된 필드 복사
            this.offsetX = locationData.getOffsetX();
            this.offsetY = locationData.getOffsetY();
        }

        public Builder accuracy(float accuracy) {
            this.accuracy = accuracy;
            return this;
        }

        public Builder altitude(float altitude) {
            this.altitude = altitude;
            return this;
        }

        public Builder bearing(float bearing) {
            this.bearing = bearing;
            return this;
        }

        public Builder speed(float speed) {
            this.speed = speed;
            return this;
        }

        public Builder environment(EnvironmentType environment) {
            this.environment = environment;
            return this;
        }

        public Builder provider(String provider) {
            this.provider = provider;
            return this;
        }

        public Builder confidence(float confidence) {
            this.confidence = confidence;
            return this;
        }

        // 추가된 메서드
        public Builder offsetX(double offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        public Builder offsetY(double offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        public LocationData build() {
            return new LocationData(this);
        }

    }

    @Override
    public String toString() {
        return String.format(
                "LocationData{lat=%.6f, lng=%.6f, acc=%.1fm, env=%s, provider=%s, conf=%.2f}",
                latitude, longitude, accuracy, environment, provider, confidence
        );
    }
}
