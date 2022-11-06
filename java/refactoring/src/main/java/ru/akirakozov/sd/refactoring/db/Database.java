package ru.akirakozov.sd.refactoring.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private final String connection;

    public Database(String connection) {
        this.connection = connection;
    }

    public <T> List<T> select(String sql, Mapper<T> mapper) {
        try (
                Connection c = DriverManager.getConnection(connection);
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            List<T> result = new ArrayList<>();

            while (rs.next()) {
                result.add(mapper.map(rs));
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(String sql) {
        try (
                Connection c = DriverManager.getConnection(connection);
                Statement stmt = c.createStatement();
        ) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T aggregate(String sql, Mapper<T> mapper) {
        try (
                Connection c = DriverManager.getConnection(connection);
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sql);
        ) {
            return rs.next() ? mapper.map(rs) : null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public long aggregateLong(String sql) {
        return aggregate(sql, rs -> rs.getLong(1));
    }
}
