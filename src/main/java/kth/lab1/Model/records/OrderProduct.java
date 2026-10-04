package kth.lab1.Model.records;
//Contains a product and the quantity a customer has ordered, 
// allows a customer to buy a desired quantity of product
public record OrderProduct(
    Product product,
    int quantity
) {

    public OrderProduct {
        if (quantity <= 0) {
            throw new IllegalArgumentException("quantity of a ordered product must be 1 or higher");
        }
    }
}
