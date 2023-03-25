package org.example;

import org.json.*;

import java.io.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.lang.Object;
import java.io.InputStream;
import java.util.Scanner;

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

    public Order() {
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

