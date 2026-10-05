package kth.lab1.Model.records;
import java.util.List;
import java.time.LocalDateTime;
//A row from the list the table that shows orders
public record CustomerOrder(
String order_id,
String customerUsername,
List<OrderProduct> items,
Double totalPrice,
LocalDateTime orderDate,
String orderStatus
) {}
