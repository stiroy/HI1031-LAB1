package kth.lab1.UI.DTO;

//Used to display products on webpage, so customers can add them to their shopping cart
public record ProductDTO(
    int id,
    String name,
    String description,
    String category,
    double price,
    int quantity
    ) {}
