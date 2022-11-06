package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.dao.ProductTable;
import ru.akirakozov.sd.refactoring.html.Text;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ru.akirakozov.sd.refactoring.utils.ResponseUtils.setHtmlResponse;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    private final ProductTable productTable;

    public AddProductServlet(ProductTable productTable) {
        this.productTable = productTable;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));

        productTable.add(new Product(name, price));

        Text text = new Text("OK");

        setHtmlResponse(response, text);
    }
}
