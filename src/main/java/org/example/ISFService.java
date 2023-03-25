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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ISFService {
    public static List<Order> sortOrdersByCompleteBy(List<Order> orders) {
        Collections.sort(orders, new OrderSortingComparator());

        return orders;
    }

    public static List<OrderToPicker> sortOrdersToPickerByPickingStartTime(List<OrderToPicker> orderToPickerList) {
        Collections.sort(orderToPickerList, new OrderToPickerComparator());
        return orderToPickerList;
    }
    public static ArrayList<Order> loadOrders(InputStream is) throws Exception {
        ArrayList<Order> orders = new ArrayList<>();
//        Scanner s = new Scanner(is).useDelimiter("\\A");
//        String result = s.hasNext() ? s.next() : "";
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
//            is.close();
//            bR.close();
        } catch (IOException e){
            e.printStackTrace();
        }



//        JSONTokener tokener = new JSONTokener(is.toString());
//        JSONTokener tokener = new JSONTokener(result);
//        JSONArray jsonArray = new JSONArray(tokener);


//        System.out.println("Courses: ");
//        JSONArray courses = object.getJSONArray("courses");


//        for (int i = 0; i < jsonArray.length(); i++) {
//            JSONObject jsonObj = jsonArray.getJSONObject(i);
//            String orderId = jsonObj.getString("orderId");
//            double orderValue = jsonObj.getDouble("orderValue");
//            Duration pickingTime = Duration.parse(jsonObj.getString("pickingTime"));
//            LocalTime completeBy = LocalTime.parse(jsonObj.getString("completeBy"));
//            orders.add(new Order(orderId, orderValue, pickingTime, completeBy));
//        }
        return orders;
    }

    public static List<OrderToPicker> assignOrderToPicker(List<Order> orderList, List<Picker> pickerList){
        List<OrderToPicker> orderToPickerList = new ArrayList<>();

        for (Picker picker : pickerList) {
            List<Order> ordersToDelete = new ArrayList<>();
            LocalTime orderPickingStartTime = picker.getPickingStartTime();
            for (Order order : orderList){
                int val = picker.getLeftCapacity().compareTo(order.getPickingTime());
//                System.out.println("LEFT CAPACITY: " + picker.getLeftCapacity());
//                System.out.println("PICKING TIME: " + order.getPickingTime());
//                System.out.println("LEFT AFTER MINUS: " + picker.getLeftCapacity().minus(order.getPickingTime()).toMinutes());
                if (val >= 0) { //picker.getLeftCapacity is greater than order.getPickingTime
//                    System.out.println("WCHODZE!");
//                    System.out.println("Order ID: "+order.getOrderId());
                    orderToPickerList.add(new OrderToPicker(picker.getPickerId(), order.getOrderId(), orderPickingStartTime));
                    orderPickingStartTime = orderPickingStartTime.plus(order.getPickingTime());
//                    System.out.println(orderPickingStartTime.plusMinutes(order.getPickingTime().toMinutes()));
                    picker.setLeftCapacity(picker.getLeftCapacity().minus(order.getPickingTime()));
//                    System.out.println("LEFT CAPACITY: " + picker.getLeftCapacity());
                    ordersToDelete.add(order);
                }
            }
            orderList.removeAll(ordersToDelete);
//            System.out.println("OrderList: " + orderList.toString());
//            System.out.println("OrderToDELETE: " + ordersToDelete.toString());

        }
        return orderToPickerList;
    }
}
