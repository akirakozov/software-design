package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.dao.ProductTable;
import ru.akirakozov.sd.refactoring.html.Body;
import ru.akirakozov.sd.refactoring.html.Br;
import ru.akirakozov.sd.refactoring.html.H1;
import ru.akirakozov.sd.refactoring.html.Html;
import ru.akirakozov.sd.refactoring.html.HtmlElement;
import ru.akirakozov.sd.refactoring.html.Text;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.utils.ResponseUtils.setHtmlResponse;

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

        HtmlElement html = switch (command) {
            case "max" -> {
                Product maxProduct = productTable.maxByPrice();
                String name = maxProduct.name();
                long price = maxProduct.price();

                yield new Html(
                        new Body(
                                new H1(
                                        new Text("Product with max price: ")
                                ),
                                new Text(name + "\t" + price),
                                new Br()
                        )
                );
            }
            case "min" -> {
                Product minProduct = productTable.minByPrice();
                String name = minProduct.name();
                long price = minProduct.price();

                yield new Html(
                        new Body(
                                new H1(
                                        new Text("Product with min price: ")
                                ),
                                new Text(name + "\t" + price),
                                new Br()
                        )
                );
            }
            case "sum" -> {
                long sum = productTable.sumPrices();

                yield new Html(
                        new Body(
                                new Text("Summary price: "),
                                new Text(Long.toString(sum))
                        )
                );
            }
            case "count" -> {
                long count = productTable.count();

                yield new Html(
                        new Body(
                                new Text("Number of products: "),
                                new Text(Long.toString(count))
                        )
                );
            }
            default -> new Text("Unknown command: " + command);
        };

        setHtmlResponse(response, html);
    }
}
