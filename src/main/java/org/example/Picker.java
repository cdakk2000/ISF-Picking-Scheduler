package org.example;

import java.time.Duration;
import java.time.LocalTime;

public class Picker {
    private String pickerId;
    private Duration leftCapacity;
    private LocalTime pickingStartTime;

    public LocalTime getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(LocalTime pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public Picker(String pickerId) {
        this.pickerId = pickerId;
    }

    public Picker(String pickerId, Duration leftCapacity, LocalTime pickingStartTime) {
        this.pickerId = pickerId;
        this.leftCapacity = leftCapacity;
        this.pickingStartTime = pickingStartTime;
    }

    public String getPickerId() {
        return pickerId;
    }

    public void setPickerId(String pickerId) {
        this.pickerId = pickerId;
    }

    public Duration getLeftCapacity() {
        return leftCapacity;
    }

    public void setLeftCapacity(Duration leftCapacity) {
        this.leftCapacity = leftCapacity;
    }

    @Override
    public String toString() {
        return "Picker{" +
                "pickerId='" + pickerId + '\'' +
                ", leftCapacity=" + leftCapacity +
                ", pickingStartTime=" + pickingStartTime +
                '}';
    }
}
