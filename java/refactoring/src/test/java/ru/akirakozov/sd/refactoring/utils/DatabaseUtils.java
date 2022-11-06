package ru.akirakozov.sd.refactoring.utils;

import ru.akirakozov.sd.refactoring.dao.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseUtils {
    private DatabaseUtils() {
    }

    public static final String CONNECTION_URL = "jdbc:sqlite:test.db";

    public static List<Product> getAllProducts() {
        String sql = "SELECT * FROM PRODUCT";
        try (
                Connection c = DriverManager.getConnection(CONNECTION_URL);
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            List<Product> products = new ArrayList<>();

            while (rs.next()) {
                String name = rs.getString("name");
                long price = Long.parseLong(rs.getString("price"));

                products.add(new Product(name, price));
            }

            return products;
        } catch (SQLException e) {
            return null;
        }
    }

    public static void addProducts(List<Product> products) {
        try (
                Connection c = DriverManager.getConnection(CONNECTION_URL);
                Statement stmt = c.createStatement();
        ) {
            String sqlProducts = products.stream()
                    .map(product -> "(\"%s\", %d)".formatted(product.name(), product.price()))
                    .collect(Collectors.joining(", "));
            String sql = "INSERT INTO PRODUCT " +
                    "(NAME, PRICE) VALUES " + sqlProducts;

            stmt.executeUpdate(sql);
        } catch (SQLException ignored) {
        }
    }

    public static void initTable() {
        try (
                Connection c = DriverManager.getConnection(CONNECTION_URL);
                Statement stmt = c.createStatement();
        ) {
            String sql = "DROP TABLE IF EXISTS PRODUCT";
            stmt.executeUpdate(sql);

            sql = """
                    CREATE TABLE PRODUCT
                    (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    NAME           TEXT    NOT NULL,
                    PRICE          INT     NOT NULL)""";
            stmt.executeUpdate(sql);
        } catch (SQLException ignored) {
        }
    }

    public static void truncateTable() {
        try (
                Connection c = DriverManager.getConnection(CONNECTION_URL);
                Statement stmt = c.createStatement();
        ) {
            String sql = "DELETE FROM PRODUCT where 1 = 1";
            stmt.executeUpdate(sql);
        } catch (SQLException ignored) {
        }
    }
}
