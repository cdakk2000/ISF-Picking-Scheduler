package org.example;

import java.io.InputStream;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args) throws Exception {
//        java -jar app.jar self-test-data/advanced-optimize-order-count/orders.json self-test-data/advanced-optimize-order-count/store.json

        // Create a new instance of FileUtils which will be used to load files
        FileUtils fileUtils = new FileUtils();

        // Set default file paths for the order and store files
        String orderFile;
        String storeFile;
        if(args.length == 0) {
            orderFile = "self-test-data/advanced-optimize-order-count/orders.json";
            storeFile = "self-test-data/advanced-optimize-order-count/store.json";
        } else {
            orderFile = args[0];
            storeFile = args[1];
        }

        // Load the order and store files as input streams
        InputStream orderIs = fileUtils.getFileFromResourceAsStream(orderFile);
        InputStream storeIs = fileUtils.getFileFromResourceAsStream(storeFile);

        // Create a new Store object and load the store from the input stream
        Store store = new Store();
        store.loadStore(storeIs);


        // Load the orders from the input stream and sort them based on different criteria
        ArrayList<Order> listOrder = ISFService.loadOrders(orderIs);
        Collections.sort(listOrder, comparing(Order::getCompleteBy)
                .thenComparing(Order::getPickingTime)
                .thenComparing(Order::getOrderValue, reverseOrder()));

        // Assign orders to pickers and sort the resulting list based on picking start time
        List<OrderToPicker> orderToPickerList = ISFService.assignOrderToPicker(listOrder, store.getPickers());
        Collections.sort(orderToPickerList, comparing(OrderToPicker::getPickingStartTime));

        // Build a string with the order-to-picker assignments for display purposes
        String valueToDisplay = "";
        for(OrderToPicker orderToPicker: orderToPickerList) {
            valueToDisplay += orderToPicker.toString();
        }
        // Display the resulting order-to-picker assignments
        System.out.println(valueToDisplay);
    }

}