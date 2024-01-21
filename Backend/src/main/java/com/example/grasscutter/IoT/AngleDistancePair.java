package com.example.grasscutter.IoT;

public class AngleDistancePair {
    private double angle;
    private double distance;

    public AngleDistancePair(double angle, double distance) {
        this.angle = angle;
        this.distance = distance;
    }
    public double getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }
}