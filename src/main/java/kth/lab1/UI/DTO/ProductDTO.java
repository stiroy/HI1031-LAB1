package kth.lab1.UI.DTO;

public class ProductDTO {
    private int id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantity;

    public ProductDTO(int id, String name, String description, String category, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
    }

    // JavaBean Getters for JSP EL
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }

    // Setters for Admin Updates
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(String category) { this.category = category; }
    public void setPrice(double price) { this.price = price; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
