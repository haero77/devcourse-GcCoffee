package devcourse.coffee.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Order {

    private final UUID orderId;
    private final Email email;
    private final List<OrderItem> orderItems;
    private final LocalDateTime createAt;
    private String address;
    private String postcode;
    private OrderStatus orderStatus;
    private LocalDateTime updatedAt;

    public Order(UUID orderId, Email email, List<OrderItem> orderItems, LocalDateTime createAt,
                 String address, String postcode, OrderStatus orderStatus, LocalDateTime updatedAt
    ) {
        this.orderId = orderId;
        this.email = email;
        this.orderItems = orderItems;
        this.createAt = createAt;
        this.address = address;
        this.postcode = postcode;
        this.orderStatus = orderStatus;
        this.updatedAt = updatedAt;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public Email getEmail() {
        return email;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public String getAddress() {
        return address;
    }

    public String getPostcode() {
        return postcode;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
        this.updatedAt = LocalDateTime.now();
    }

    public void setAddress(String address) {
        this.address = address;
        this.updatedAt = LocalDateTime.now();
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
        this.updatedAt = LocalDateTime.now();
    }
}
