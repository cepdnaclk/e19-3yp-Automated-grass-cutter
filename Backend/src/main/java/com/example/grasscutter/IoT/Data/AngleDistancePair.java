package com.example.grasscutter.IoT.Data;

import java.util.ArrayList;
import java.util.List;

public class AngleDistancePair {
    private double angle;
    private double distance;

    public AngleDistancePair(double angle, double distance) {
        this.angle = angle;
        this.distance = distance;
    }

    // Getters for angle and distance

    public double getAngle() {
        return angle;
    }

    public double getDistance() {
        return distance;
    }
}