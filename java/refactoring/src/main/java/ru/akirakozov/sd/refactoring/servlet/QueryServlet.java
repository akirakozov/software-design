package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.dao.ProductTable;
import ru.akirakozov.sd.refactoring.html.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final ProductTable productTable;

    public QueryServlet(ProductTable productTable) {
        this.productTable = productTable;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        if ("max".equals(command)) {
            Product maxProduct = productTable.maxByPrice();
            String name = maxProduct.name();
            long price = maxProduct.price();

            Html html = new Html(
                    new Body(
                            new H1(
                                    new Text("Product with max price: ")
                            ),
                            new Text(name + "\t" + price),
                            new Br()
                    )
            );

            response.getWriter().println(html);
        } else if ("min".equals(command)) {
            Product minProduct = productTable.minByPrice();
            String name = minProduct.name();
            long price = minProduct.price();

            Html html = new Html(
                    new Body(
                            new H1(
                                    new Text("Product with min price: ")
                            ),
                            new Text(name + "\t" + price),
                            new Br()
                    )
            );

            response.getWriter().println(html);
        } else if ("sum".equals(command)) {
            long sum = productTable.sumPrices();

            Html html = new Html(
                    new Body(
                            new Text("Summary price: "),
                            new Text(Long.toString(sum))
                    )
            );

            response.getWriter().println(html);
        } else if ("count".equals(command)) {
            long count = productTable.count();

            Html html = new Html(
                    new Body(
                            new Text("Number of products: "),
                            new Text(Long.toString(count))
                    )
            );

            response.getWriter().println(html);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
