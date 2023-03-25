import org.example.ISFService;
import org.example.Order;
import org.example.OrderToPicker;
import org.example.Picker;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalTime;

import static org.example.ISFService.assignOrderToPicker;
import static org.example.ISFService.loadOrders;

public class ISFServiceTest {


    @Test
    public void loadOrdersSuccessTest() throws Exception {

        ISFService isfService = new ISFService();
        InputStream anyInputStream = new ByteArrayInputStream(("[{\n" +
                        "    \"orderId\": \"order-1\",\n" +
                        "    \"orderValue\": \"0.00\",\n" +
                        "    \"pickingTime\": \"PT20M\",\n" +
                        "    \"completeBy\": \"09:30\"\n" +
                        "  }]").getBytes());
        List<Order> result = loadOrders(anyInputStream);
        Assert.assertEquals(1, result.size());
    }

    @Test
    public void assignOrderToPickerSuccessTest() {

        List<Order> orderList = new ArrayList<>();
        List<Picker> pickerList = new ArrayList<>();
        Order order1 = new Order("order-1", 0.00, Duration.ofMinutes(20), LocalTime.of(9,30,0));
        Order order2 = new Order("order-2", 0.00, Duration.ofMinutes(20), LocalTime.of(9,30,0));
        orderList.add(order1);
        orderList.add(order2);

        Picker picker1 = new Picker("P1",Duration.ofMinutes(60), LocalTime.of(9,0,0));
        pickerList.add(picker1);

        List<OrderToPicker> result = assignOrderToPicker(orderList, pickerList);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals("order-1",result.get(0).getOrderId());
        Assert.assertEquals("order-2",result.get(1).getOrderId());
        Assert.assertEquals("P1",result.get(0).getPickerId());
        Assert.assertEquals("P1",result.get(1).getPickerId());

    }


}
