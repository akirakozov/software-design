package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.*;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DbController dbController = new DbController("jdbc:sqlite:test.db");

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(dbController)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(dbController)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(dbController)),"/query");

        if (args.length > 0 && "test-env".equals(args[0])) {
            System.out.println("Running server in test environment.");
            context.addServlet(new ServletHolder(new ClearDatabaseServlet(dbController)), "/clear");
        }

        server.start();
        server.join();
    }
}
