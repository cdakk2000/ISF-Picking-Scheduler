package org.example;

import java.util.Comparator;

public class OrderSortingComparator implements Comparator<Order> {

    @Override
    public int compare(Order order1, Order order2) {


        int sComp = order1.getCompleteBy().compareTo(order2.getCompleteBy());;

        if (sComp != 0) {
            return sComp;
        }

        int sComp1 = order1.getPickingTime().compareTo(order2.getPickingTime());

        if (sComp1 != 0) {
            return sComp1;
        }

        return order1.getPickingTime().compareTo(order2.getPickingTime());

    }
}
