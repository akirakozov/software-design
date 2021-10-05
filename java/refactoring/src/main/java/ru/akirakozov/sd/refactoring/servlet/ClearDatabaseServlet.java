package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ClearDatabaseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                stmt.executeUpdate("DELETE FROM PRODUCT");
                stmt.close();

                response.getWriter().println("<html><body></body></html>");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
