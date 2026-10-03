package kth.lab1.Model.records;
//A product, representative of a row in product table
public record Product(
    int id,
    String name,
    String description,
    String category,
    double price,
    int stockQuantity
) {
    
    public Product {
        //Object.requireNonNull(id);
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (stockQuantity < 0) {
            throw new IllegalArgumentException("stock cannot be negative");
        }
    }
    // public static Product unnamed(int id, String desp, double price, int stock){
    //      return new Product(id, "unnamed", desp, double, stock);
    //}
    // called in code Poduct.unnamed()
}
