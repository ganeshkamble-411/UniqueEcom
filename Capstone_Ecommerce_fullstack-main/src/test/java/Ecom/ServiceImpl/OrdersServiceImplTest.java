//package Ecom.ServiceImpl;
//
//import Ecom.Enum.OrderStatus;
//import Ecom.Exception.OrdersException;
//import Ecom.Exception.UserException;
//import Ecom.Model.Cart;
//import Ecom.Model.CartItem;
//import Ecom.Model.Orders;
//import Ecom.Model.OrderItem;
//import Ecom.Model.User;
//import Ecom.ModelDTO.OrdersDTO;
//import Ecom.Repository.CartItemRepository;
//import Ecom.Repository.CartRepository;
//import Ecom.Repository.OrderItemRepository;
//import Ecom.Repository.OrderRepository;
//import Ecom.Repository.ProductRepository;
//import Ecom.Repository.UserRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class OrdersServiceImplTest {
//
//    @Mock
//    private OrderRepository orderRepository;
//
//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private OrderItemRepository orderItemRepository;
//
//    @Mock
//    private ProductRepository productRepository;
//
//
//    @InjectMocks
//    private OrdersServiceImpl ordersService;
//
//    private User user;
//    private Cart cart;
//    private Orders order;
//    private CartItem cartItem;
//
//    @BeforeEach
//    void setUp() {
//        user = new User();
//        user.setUserId(1);
//
//        cart = new Cart();
//        cart.setCartId(101);
//        cart.setTotalAmount(500.0);
//        user.setCart(cart);
//
//        cartItem = new CartItem();
//        cartItem.setQuantity(2);
//        cartItem.setCart(cart);
//        cart.getCartItems().add(cartItem);
//
//        order = new Orders();
//        order.setOrderId(1001);
//        order.setOrderDate(LocalDateTime.now());
//        order.setStatus(OrderStatus.PENDING);
//        order.setTotalAmount(500.0);
//    }
//
//    @Test
//    void placeOrder_ShouldCreateOrderAndReturnDTO() throws OrdersException {
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(orderRepository.save(any(Orders.class))).thenReturn(order);
//        when(userRepository.save(user)).thenReturn(user);
//
//        OrdersDTO result = ordersService.placeOrder(1);
//
//        assertNotNull(result);
//        assertEquals(1001, result.getOrderId());
//        assertEquals("Pending", result.getStatus());
//        verify(orderRepository, times(2)).save(any(Orders.class));
//
//    }
//
//    @Test
//    void placeOrder_EmptyCart_ShouldThrowException() {
//        cart.setTotalAmount(0.0);
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//
//        OrdersException ex = assertThrows(OrdersException.class,
//                () -> ordersService.placeOrder(1));
//
//        assertEquals("Add item To the cart first.......", ex.getMessage());
//    }
//
//    @Test
//    void placeOrder_UserNotFound_ShouldThrowException() {
//        when(userRepository.findById(1)).thenReturn(Optional.empty());
//
//        UserException ex = assertThrows(UserException.class,
//                () -> ordersService.placeOrder(1));
//
//        assertEquals("User Not Found In Database", ex.getMessage());
//    }
//
//    @Test
//    void getOrdersDetails_ShouldReturnOrder() throws OrdersException {
//        when(orderRepository.findById(1001)).thenReturn(Optional.of(order));
//
//        Orders result = ordersService.getOrdersDetails(1001);
//
//        assertNotNull(result);
//        assertEquals(1001, result.getOrderId());
//    }
//
//    @Test
//    void getOrdersDetails_NotFound_ShouldThrowException() {
//        when(orderRepository.findById(999)).thenReturn(Optional.empty());
//
//        OrdersException ex = assertThrows(OrdersException.class,
//                () -> ordersService.getOrdersDetails(999));
//
//        assertEquals("Order not found in the database.", ex.getMessage());
//    }
//
//    @Test
//    void getAllUserOrder_ShouldReturnList() throws OrdersException {
//        List<Orders> ordersList = new ArrayList<>();
//        ordersList.add(order);
//
//        when(orderRepository.getAllOrderByUserId(1)).thenReturn(ordersList);
//
//        List<Orders> result = ordersService.getAllUserOrder(1);
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void getAllUserOrder_Empty_ShouldThrowException() {
//        when(orderRepository.getAllOrderByUserId(1)).thenReturn(new ArrayList<>());
//
//        OrdersException ex = assertThrows(OrdersException.class,
//                () -> ordersService.getAllUserOrder(1));
//
//        assertEquals("No orders found for the user in the database.", ex.getMessage());
//    }
//
//    @Test
//    void viewAllOrders_ShouldReturnList() throws OrdersException {
//        List<Orders> ordersList = new ArrayList<>();
//        ordersList.add(order);
//
//        when(orderRepository.findAll()).thenReturn(ordersList);
//
//        List<Orders> result = ordersService.viewAllOrders();
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void viewAllOrders_Empty_ShouldThrowException() {
//        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
//
//        OrdersException ex = assertThrows(OrdersException.class,
//                () -> ordersService.viewAllOrders());
//
//        assertEquals("No orders found in the database.", ex.getMessage());
//    }
//
//    @Test
//    void viewAllOrderByDate_ShouldReturnList() throws OrdersException {
//        List<Orders> ordersList = new ArrayList<>();
//        ordersList.add(order);
//
//        when(orderRepository.findByOrderDateGreaterThanEqual(any(Date.class))).thenReturn(ordersList);
//
//        List<Orders> result = ordersService.viewAllOrderByDate(new Date());
//
//        assertEquals(1, result.size());
//    }
//
//    @Test
//    void viewAllOrderByDate_Empty_ShouldThrowException() {
//        when(orderRepository.findByOrderDateGreaterThanEqual(any(Date.class))).thenReturn(new ArrayList<>());
//
//        OrdersException ex = assertThrows(OrdersException.class,
//                () -> ordersService.viewAllOrderByDate(new Date()));
//
//        assertEquals("No orders found for the given date.", ex.getMessage());
//    }
//
//    @Test
//    void deleteOrders_ShouldDeleteOrder() throws OrdersException {
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(orderRepository.findById(1001)).thenReturn(Optional.of(order));
//
//        ordersService.deleteOrders(1, 1001);
//
//        verify(orderRepository, times(1)).delete(order);
//    }
//
//    @Test
//    void deleteOrders_UserNotFound_ShouldThrowException() {
//        when(userRepository.findById(1)).thenReturn(Optional.empty());
//
//        UserException ex = assertThrows(UserException.class,
//                () -> ordersService.deleteOrders(1, 1001));
//
//        assertEquals("User Not Found In Database", ex.getMessage());
//    }
//
//    @Test
//    void deleteOrders_OrderNotFound_ShouldThrowException() {
//        when(userRepository.findById(1)).thenReturn(Optional.of(user));
//        when(orderRepository.findById(1001)).thenReturn(Optional.empty());
//
//        UserException ex = assertThrows(UserException.class,
//                () -> ordersService.deleteOrders(1, 1001));
//
//        assertEquals("order Not Found In Database", ex.getMessage());
//    }
//}