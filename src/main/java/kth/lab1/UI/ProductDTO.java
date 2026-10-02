package kth.lab1.UI;


public record ProductDTO(
    int id,
    String name,
    String description,
    String category,
    double price,
    int quantity
    ) {}
