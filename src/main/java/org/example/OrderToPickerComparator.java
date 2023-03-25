package org.example;

import java.util.Comparator;

public class OrderToPickerComparator implements Comparator<OrderToPicker> {
    @Override
    public int compare(OrderToPicker orderToPicker1, OrderToPicker orderToPicker2) {

        return orderToPicker1.getPickingStartTime().compareTo(orderToPicker2.getPickingStartTime());

    }
}
