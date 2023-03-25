import org.example.Order;
import org.junit.Assert;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalTime;

public class OrderTest {

    @Test
    public void creationTest() {
        Order order = new Order("order-1", 2.0, Duration.ofMinutes(20), LocalTime.of(10,2,3));
        Assert.assertEquals( "order-1", order.getOrderId());
        Assert.assertEquals( 2.0, order.getOrderValue(), 0.001);
        Assert.assertEquals(Duration.ofMinutes(20),order.getPickingTime());
        Assert.assertEquals(LocalTime.of(10,2,3), order.getCompleteBy());
    }
}
