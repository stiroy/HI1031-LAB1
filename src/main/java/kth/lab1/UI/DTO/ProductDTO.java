package kth.lab1.UI.DTO;

//Used to display products on webpage, so customers can add them to their shopping cart
public record ProductDTO(
    int id,
    String name,
    String description,
    String category,
    double price,
    int quantity
    ) {
        // Standard JavaBean getters for JSP EL resolution
        public int getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public String getCategory() { return category; }
        public double getPrice() { return price; }
        public int getQuantity() { return quantity; }
    }
