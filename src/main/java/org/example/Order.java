package org.example;
import java.time.Duration;
import java.time.LocalTime;

public class Order {
    private String orderId;
    private double orderValue;
    private Duration pickingTime;
    private LocalTime completeBy;


    public Order(String orderId, double orderValue, Duration pickingTime, LocalTime completeBy) {
        this.orderId = orderId;
        this.orderValue = orderValue;
        this.pickingTime = pickingTime;
        this.completeBy = completeBy;
    }

    private Order() {
    }

    public String getOrderId() {
        return orderId;
    }

    public double getOrderValue() {
        return orderValue;
    }

    public Duration getPickingTime() {
        return pickingTime;
    }

    public LocalTime getCompleteBy() {
        return completeBy;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderValue=" + orderValue +
                ", pickingTime=" + pickingTime +
                ", completeBy=" + completeBy +
                '}';
    }





}

