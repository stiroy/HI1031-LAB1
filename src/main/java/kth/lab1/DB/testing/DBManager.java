package kth.lab1.DB.testing;

import javax.sql.DataSource;
import java.util.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;

public class DBManager {

    private final DataSource dataSource;
    private final ProductRepository products;

    //constructor
    public DBManager(DataSource dataSource) {
        this.dataSource = Objects.requireNonNull(dataSource);
        
        // repositories
        // TODO: user
        this.products = new ProductRepository(this);
    }

    // Access point for products: db.products().create("...")
    public ProductRepository products() {
        return products;
    }

    // Functional interface for mapping ResultSet -> Entity
    @FunctionalInterface
    public interface RowMapper<T> {
        T map(ResultSet rs) throws SQLException;
    }

    // Functional interface for setting statement parameters
    @FunctionalInterface
    public interface StatementBinder {
        void bind(PreparedStatement stmt) throws SQLException;
    }

    // Generic helper for single or list queries
    public <T> T query(String sql, StatementBinder binder, RowMapper<T> mapper) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (binder != null) {
                binder.bind(stmt);
            }
            
            try (ResultSet rs = stmt.executeQuery()) {
                return mapper.map(rs);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database query failed", e);
        }
    }

    // Generic helper for INSERT / UPDATE / DELETE
    public int update(String sql, StatementBinder binder) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            if (binder != null) {
                binder.bind(stmt);
            }
            return stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database execution failed", e);
        }
    }
}
