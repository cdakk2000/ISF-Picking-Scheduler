package org.example;

import java.time.LocalTime;

public class OrderToPicker {
    private String pickerId;
    private String orderId;

    private LocalTime pickingStartTime;

    public LocalTime getPickingStartTime() {
        return pickingStartTime;
    }

    public void setPickingStartTime(LocalTime pickingStartTime) {
        this.pickingStartTime = pickingStartTime;
    }

    public String getPickerId() {
        return pickerId;
    }

    public void setPickerId(String pickerId) {
        this.pickerId = pickerId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderToPicker(String pickerId, String orderId, LocalTime pickingStartTime) {
        this.pickerId = pickerId;
        this.orderId = orderId;
        this.pickingStartTime = pickingStartTime;
    }

    private OrderToPicker() {
    }

    @Override
    public String toString() {
        return  pickerId + " " + orderId + " " + pickingStartTime + '\n';

    }
}
