package kth.lab1.DB.testing;

import kth.lab1.DB.testing.DBProduct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepository {

    private final DBManager db;

    ProductRepository(DBManager db) {
        this.db = db;
    }

    public DBProduct create(String name, double price, int stock) {
        var sql = """
            INSERT INTO T_products (name, price, quantity)
            VALUES (?, ?, ?)
            RETURNING product_id, name, price, stock
            """;

        return db.query(sql, 
            stmt -> {
                stmt.setString(1, name);
                stmt.setDouble(2, price);
                stmt.setInt(3, stock);
            },
            rs -> {
                if (rs.next()) {
                    return mapRow(rs);
                }
                throw new IllegalStateException("Failed to create product");
            }
        );
    }

    public Optional<DBProduct> findById(int id) {
        var sql = "SELECT id, name, price, quantity FROM T_products WHERE product_id = ?";

        return db.query(sql, 
            stmt -> stmt.setInt(1, id),
            rs -> rs.next() ? Optional.of(mapRow(rs)) : Optional.empty()
        );
    }

    public List<DBProduct> findAll() {
        var sql = "SELECT product_id, name, price, quantity FROM T_products ORDER BY name";

        return db.query(sql, null, rs -> {
            var list = new ArrayList<DBProduct>();
            while (rs.next()) {
                list.add(mapRow(rs));
            }
            return list;
        });
    }

    // Helper mapper local to Product
    private DBProduct mapRow(java.sql.ResultSet rs) throws java.sql.SQLException {
        return new DBProduct(
            rs.getInt("product_id"),
            rs.getString("name"),
            rs.getDouble("price"),
            rs.getInt("quantity")
        );
    }
}