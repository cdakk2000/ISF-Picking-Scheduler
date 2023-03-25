package org.example;

import java.io.InputStream;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args) throws Exception {
//        java -jar app.jar self-test-data/advanced-optimize-order-count/orders.json self-test-data/advanced-optimize-order-count/store.json
        FileUtils fileUtils = new FileUtils();
        String orderFile;
        String storeFile;
        if(args.length == 0) {
            orderFile = "self-test-data/advanced-optimize-order-count/orders.json";
            storeFile = "self-test-data/advanced-optimize-order-count/store.json";
        } else {
            orderFile = args[0];
            storeFile = args[1];
        }

        InputStream orderIs = fileUtils.getFileFromResourceAsStream(orderFile);
        InputStream storeIs = fileUtils.getFileFromResourceAsStream(storeFile);

        Store store = new Store();
        store.loadStore(storeIs);


        ArrayList<Order> listOrder = ISFService.loadOrders(orderIs);

        Collections.sort(listOrder, comparing(Order::getCompleteBy)
                .thenComparing(Order::getPickingTime)
                .thenComparing(Order::getOrderValue, reverseOrder()));

        List<OrderToPicker> orderToPickerList = ISFService.assignOrderToPicker(listOrder, store.getPickers());

        Collections.sort(orderToPickerList, comparing(OrderToPicker::getPickingStartTime));

        String valueToDisplay = "";
        for(OrderToPicker orderToPicker: orderToPickerList) {
            valueToDisplay += orderToPicker.toString();
        }
        System.out.println(valueToDisplay);
    }

}