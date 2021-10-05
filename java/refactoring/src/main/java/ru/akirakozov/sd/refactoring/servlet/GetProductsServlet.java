package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final DbController dbController;

    public GetProductsServlet(DbController dbController) {
        this.dbController = dbController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        dbController.evaluateQuery(DbServletQueryType.GET, request, response);
    }
}
