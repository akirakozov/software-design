package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.dao.ProductTable;
import ru.akirakozov.sd.refactoring.html.Body;
import ru.akirakozov.sd.refactoring.html.Br;
import ru.akirakozov.sd.refactoring.html.Html;
import ru.akirakozov.sd.refactoring.html.HtmlElement;
import ru.akirakozov.sd.refactoring.html.Text;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import static ru.akirakozov.sd.refactoring.utils.ResponseUtils.setHtmlResponse;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    private final ProductTable productTable;

    public GetProductsServlet(ProductTable productTable) {
        this.productTable = productTable;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Product> products = productTable.findAll();

        List<HtmlElement> elements = products.stream()
                .map(product -> product.name() + "\t" + product.price())
                .flatMap(text -> Stream.of(new Text(text), new Br()))
                .toList();
        Html html = new Html(new Body(elements));

        setHtmlResponse(response, html);
    }
}
