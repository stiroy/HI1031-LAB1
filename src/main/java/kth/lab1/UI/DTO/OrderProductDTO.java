package kth.lab1.UI.DTO;
//Every unique product in the user's shopping cart that is delivered to model when purchased
public record OrderProductDTO(
    ProductDTO product,
    int quantity
) {}
