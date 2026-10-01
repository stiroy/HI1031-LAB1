package kth.lab1.DB;
public class ItemDTO {
    private final int id;
    private final String name;
    private final String description;
    private final String category;
    private final int quantity;
    private final double price;

    public ItemDTO(int id, String name, String description, String category, int quantity, double price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }
    public ItemDTO(int id, String name, String description, String category, double price){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = 1;
        this.price = price;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getDescription(){return this.description;}
    public String getCategory(){return this.category;}
    public int getQuantity(){return this.quantity;}
    public double getPrice(){return this.price;}
}
