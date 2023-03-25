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

    public static ArrayList<Order> loadOrders(InputStream is) throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
        JSONArray result;
        try {
            BufferedReader bR = new BufferedReader(new InputStreamReader(is));
            String line = "";

            StringBuilder responseStrBuilder = new StringBuilder();
            while ((line = bR.readLine()) != null) {
                responseStrBuilder.append(line);
            }
            result = new JSONArray(responseStrBuilder.toString());
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

        return orders;
    }

    public static List<OrderToPicker> assignOrderToPicker(List<Order> orderList, List<Picker> pickerList){
        List<OrderToPicker> orderToPickerList = new ArrayList<>();

        for (Picker picker : pickerList) {
            List<Order> ordersToDelete = new ArrayList<>();
            LocalTime orderPickingStartTime = picker.getPickingStartTime();
            for (Order order : orderList){
                int isCapacity = picker.getLeftCapacity().compareTo(order.getPickingTime());
                if (isCapacity >= 0) { //picker.getLeftCapacity is greater than order.getPickingTime
                    orderToPickerList.add(new OrderToPicker(picker.getPickerId(), order.getOrderId(), orderPickingStartTime));
                    orderPickingStartTime = orderPickingStartTime.plus(order.getPickingTime());
                    picker.setLeftCapacity(picker.getLeftCapacity().minus(order.getPickingTime()));
                    ordersToDelete.add(order);
                }
            }
            orderList.removeAll(ordersToDelete);
        }
        return orderToPickerList;
    }
}
