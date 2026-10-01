public class ItemDTO {
    private final int id;
    private final String name;
    private final String description;
    private final String category;
    private final int quantity;

    public ItemDTO(int id, String name, String description, String category, int quantity){
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
    }

    public int getId(){return this.id;}
    public String getName(){return this.name;}
    public String getDescription(){return this.description;}
    public String getCategory(){return this.category;}
    public int getQuantity(){return this.quantity;}
}
