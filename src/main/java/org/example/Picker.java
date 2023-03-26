package org.example;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Objects;

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

    private  Picker() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Picker picker = (Picker) o;
        return Objects.equals(pickerId, picker.pickerId) && Objects.equals(leftCapacity, picker.leftCapacity) && Objects.equals(pickingStartTime, picker.pickingStartTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pickerId, leftCapacity, pickingStartTime);
    }
}
