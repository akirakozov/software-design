package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetProductsServletTest extends AbstractServletTest {
    private GetProductsServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new GetProductsServlet(productTable);
    }

    @AfterEach
    void tearDown() {
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testGetAllProducts() throws IOException {
        DatabaseUtils.addProducts(PRODUCTS);

        servlet.doGet(mockRequest, mockResponse);
        String expectedHtmlResponse = PRODUCTS.stream()
                .map(product -> product.name() + "\t" + product.price() + "</br>")
                .collect(Collectors.joining("\n", "<html><body>\n", "\n</body></html>\n"));

        assertEquals(expectedHtmlResponse, getResponse());
    }
}
