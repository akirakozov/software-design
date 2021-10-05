package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final DbController dbController;

    public QueryServlet(DbController dbController) {
        this.dbController = dbController;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        switch (command) {
            case "max" -> dbController.evaluateQuery(DbServletQueryType.MAX, request, response);
            case "min" -> dbController.evaluateQuery(DbServletQueryType.MIN, request, response);
            case "sum" -> dbController.evaluateQuery(DbServletQueryType.SUM, request, response);
            case "count" -> dbController.evaluateQuery(DbServletQueryType.COUNT, request, response);
            default -> {
                response.getWriter().println("Unknown command: " + command);
                response.setContentType("text/html");
                response.setStatus(HttpServletResponse.SC_OK);
            }
        }
    }
}
