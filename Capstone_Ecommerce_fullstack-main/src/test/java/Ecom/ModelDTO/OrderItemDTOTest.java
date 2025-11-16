package Ecom.ModelDTO;

import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

class OrderItemDTOTest {

    @Test
    void testSettersAndGetters() {
        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProductId(101);
        orderItem.setQuantity(2);
        orderItem.setPrice(BigDecimal.valueOf(499.99));

        assertEquals(101, orderItem.getProductId());
        assertEquals(2, orderItem.getQuantity());
        assertEquals(BigDecimal.valueOf(499.99), orderItem.getPrice());
    }

    @Test
    void testEqualsAndHashCode() {
        OrderItemDTO item1 = new OrderItemDTO();
        item1.setProductId(101);
        item1.setQuantity(2);
        item1.setPrice(BigDecimal.valueOf(499.99));

        OrderItemDTO item2 = new OrderItemDTO();
        item2.setProductId(101);
        item2.setQuantity(2);
        item2.setPrice(BigDecimal.valueOf(499.99));

        assertEquals(item1, item2);
        assertEquals(item1.hashCode(), item2.hashCode());
    }

    @Test
    void testToStringContainsFields() {
        OrderItemDTO orderItem = new OrderItemDTO();
        orderItem.setProductId(101);
        orderItem.setQuantity(2);
        orderItem.setPrice(BigDecimal.valueOf(499.99));

        String toStringResult = orderItem.toString();
        assertTrue(toStringResult.contains("101"));
        assertTrue(toStringResult.contains("2"));
        assertTrue(toStringResult.contains("499.99"));
    }

    @Test
    void testDefaultValues() {
        OrderItemDTO orderItem = new OrderItemDTO();
        assertEquals(0, orderItem.getProductId());
        assertEquals(0, orderItem.getQuantity());
        assertNull(orderItem.getPrice());
    }
}