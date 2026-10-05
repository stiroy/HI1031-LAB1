package kth.lab1.UI.DTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record OrderDTO(
    String orderId,
    String customerName,
    List<CartProductDTO> items,
    double totalAmount,
    LocalDateTime orderDate,
    String status // "PENDING", "PACKED", "CANCELLED"
) {
    public String getFormattedDate() {
        return orderDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // JavaBean getters for JSP EL (sometimes)
    public String getOrderId() { return orderId; }
    public String getCustomerName() { return customerName; }
    public List<CartProductDTO> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getOrderDate() { return getFormattedDate(); }
    public String getStatus() { return status; }
}