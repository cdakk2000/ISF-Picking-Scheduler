package org.example;

import java.io.File;
import java.io.InputStream;
import java.time.Duration;
import java.util.*;

import static java.util.Collections.reverseOrder;
import static java.util.Comparator.comparing;

public class Main {
    public static void main(String[] args) throws Exception {

        FileUtils fileUtils = new FileUtils();

        //String fileName = "database.properties";
        String orderFile = "self-test-data/logic-bomb/orders.json";
        String storeFile = "self-test-data/logic-bomb/store.json";

      //  System.out.println("getResourceAsStream : " + fileName);
        InputStream orderIs = fileUtils.getFileFromResourceAsStream(orderFile);
        InputStream storeIs = fileUtils.getFileFromResourceAsStream(storeFile);
        //fileUtils.printInputStream(is);

        Store store = new Store();
        Order order = new Order();

        store.loadStore(storeIs);
       // Duration workingHours = store.getWorkingHours();


        ArrayList<Order> listOrder = ISFService.loadOrders(orderIs);

        //System.out.println(store.toString());

//        for(int i=0;i<listOrder.size();i++){
//            System.out.println(listOrder.get(i).toString());
//        }
//

        //System.out.println(workingHours.toString());
//
//        for(int i=0;i<listOrder.size();i++){
//            System.out.println("Posortowana: " +listOrder.get(i).toString());
//        }


//        List<Order> sortedOrders = ISFService.sortOrdersByCompleteBy(listOrder);

        Collections.sort(listOrder, comparing(Order::getCompleteBy)
                .thenComparing(Order::getPickingTime)
                .thenComparing(Order::getOrderValue, reverseOrder()));



//        for(int i=0;i<listOrder.size();i++){
//            System.out.println(listOrder.get(i).toString());
//        }

        List<OrderToPicker> orderToPickerList = ISFService.assignOrderToPicker(listOrder, store.getPickers());
        //List<OrderToPicker> orderToPickerListSortedByPickingStartTime = ISFService.sortOrdersToPickerByPickingStartTime(orderToPickerList);

        Collections.sort(orderToPickerList, comparing(OrderToPicker::getPickingStartTime));

        String valueToDisplay = "";
        for(OrderToPicker orderToPicker: orderToPickerList) {
            valueToDisplay += orderToPicker.toString();
        }
        System.out.println(valueToDisplay);
    }

}