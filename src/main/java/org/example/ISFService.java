package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ISFService {

    // This method loads the list of orders from the input stream and returns it as an ArrayList<Order>
    public static ArrayList<Order> loadOrders(InputStream is) throws Exception {
        // Create an empty ArrayList to hold the orders
        ArrayList<Order> orders = new ArrayList<>();
        // Parse the JSON data from the input stream
        JSONArray result;
        try {
            BufferedReader bR = new BufferedReader(new InputStreamReader(is));
            String line = "";

            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
            result = new JSONArray(responseStrBuilder.toString());

            // Convert the JSON objects into Order objects and add them to the orders ArrayList
            for (int i = 0; i < result.length(); i++) {
                JSONObject jsonObj = result.getJSONObject(i);
                String orderId = jsonObj.getString("orderId");
                double orderValue = jsonObj.getDouble("orderValue");
                Duration pickingTime = Duration.parse(jsonObj.getString("pickingTime"));
                LocalTime completeBy = LocalTime.parse(jsonObj.getString("completeBy"));
                orders.add(new Order(orderId, orderValue, pickingTime, completeBy));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        // Return the ArrayList of orders
        return orders;
    }

    // This method assigns orders to pickers based on their available capacity and returns a list of OrderToPicker objects
    public static List<OrderToPicker> assignOrderToPicker(List<Order> orderList, List<Picker> pickerList){
        List<OrderToPicker> orderToPickerList = new ArrayList<>();

        // Iterate through the list of pickers
        for (Picker picker : pickerList) {
            List<Order> ordersToDelete = new ArrayList<>();
            LocalTime orderPickingStartTime = picker.getPickingStartTime();

            // Iterate through the list of orders
            for (Order order : orderList){
                int isCapacity = picker.getLeftCapacity().compareTo(order.getPickingTime());
                if (isCapacity >= 0) { // If the picker has enough capacity to pick this order
                    // Create a new OrderToPicker object and add it to the orderToPickerList
                    orderToPickerList.add(new OrderToPicker(picker.getPickerId(), order.getOrderId(), orderPickingStartTime));
                    orderPickingStartTime = orderPickingStartTime.plus(order.getPickingTime());
                    picker.setLeftCapacity(picker.getLeftCapacity().minus(order.getPickingTime()));
                    ordersToDelete.add(order);
                }
            }
            // Remove the assigned orders from the orderList
            orderList.removeAll(ordersToDelete);
        }
        // Return the list of assigned orders
        return orderToPickerList;
    }
}
