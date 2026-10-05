package kth.lab1.UI.DTO;

import java.util.ArrayList;
import java.util.List;
import kth.lab1.UI.DTO.CartProductDTO;

public class Cart {
    private final List<CartProductDTO> items = new ArrayList<>();

    public void addProduct(ProductDTO product, int qty) {
        for (int i = 0; i < items.size(); i++) {
            CartProductDTO current = items.get(i);
            if (current.product().id() == product.id()) {
                int newQty = Math.min(current.quantity() + qty, product.quantity());
                items.set(i, new CartProductDTO(product, newQty));
                return;
            }
        }
        items.add(new CartProductDTO(product, Math.min(qty, product.quantity())));
    }

    public void updateQuantity(int productId, int qty) {
        if (qty <= 0) {
            removeItem(productId);
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            CartProductDTO current = items.get(i);
            if (current.product().id() == productId) {
                items.set(i, new CartProductDTO(current.product(), Math.min(qty, current.product().quantity())));
                return;
            }
        }
    }

    public void removeItem(int productId) {
        items.removeIf(item -> item.product().id() == productId);
    }

    public void clear() {
        items.clear();
    }

    public List<CartProductDTO> getItems() { return items; }
    
    public int getTotalItemCount() {
        return items.stream().mapToInt(CartProductDTO::quantity).sum();
    }

    public double getTotalAmount() {
        return items.stream().mapToDouble(CartProductDTO::getTotalPrice).sum();
    }
}