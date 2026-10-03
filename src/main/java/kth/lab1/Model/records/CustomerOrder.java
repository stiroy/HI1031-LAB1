package kth.lab1.Model.records;
//A row from the list the table that shows orders
public record CustomerOrder(
int order_id,
String customerUsername,
String productName,
int quantity,
double unit_price,
String orderStatus,
Double totalPrice
) {}
