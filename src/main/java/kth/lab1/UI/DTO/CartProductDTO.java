package kth.lab1.UI.DTO;

public record CartProductDTO(ProductDTO product, int quantity) {
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }
    
    // JavaBean getters for JSP EL
    public ProductDTO getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public double getTotalPriceValue() { return getTotalPrice(); }
}