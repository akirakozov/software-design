package ru.akirakozov.sd.refactoring.dao;

import ru.akirakozov.sd.refactoring.db.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ProductTable {
    private final Database database;

    public ProductTable(Database database) {
        this.database = database;
    }

    public List<Product> findAll() {
        return database.select("SELECT * FROM PRODUCT", ProductTable::map);
    }

    public void add(Product product) {
        String sql = """
                INSERT INTO PRODUCT
                (NAME, PRICE)
                VALUES
                ("%s", %d)
                """.formatted(product.name(), product.price());
        database.update(sql);
    }

    public Product maxByPrice() {
        return database.aggregate("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", ProductTable::map);
    }

    public Product minByPrice() {
        return database.aggregate("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", ProductTable::map);
    }

    public long sumPrices() {
        return database.aggregateLong("SELECT SUM(price) FROM PRODUCT");
    }

    public long count() {
        return database.aggregateLong("SELECT COUNT(*) FROM PRODUCT");
    }

    public void init() {
        String sql = """
                CREATE TABLE IF NOT EXISTS PRODUCT
                (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                NAME           TEXT    NOT NULL,
                PRICE          INT     NOT NULL)""";
        database.update(sql);
    }

    public static Product map(ResultSet rs) throws SQLException {
        String name = rs.getString("name");
        long price = rs.getLong("price");

        return new Product(name, price);
    }
}
