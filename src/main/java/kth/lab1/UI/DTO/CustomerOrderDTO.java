package kth.lab1.UI.DTO;
//A row from the list the table that shows customers orders, for employees
public record CustomerOrderDTO(
int order_id,
String customerUsername,
String productName,
int quantity,
double unit_price,
String orderStatus,
Double totalPrice
) {}
