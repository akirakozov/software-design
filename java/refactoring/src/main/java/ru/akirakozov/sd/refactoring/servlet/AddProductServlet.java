package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private final DbController dbController;

    public AddProductServlet(DbController dbController) {
        this.dbController = dbController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        dbController.evaluateQuery(DbServletQueryType.ADD, request, response);
    }
}
