package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryServletTest extends AbstractServletTest {
    private QueryServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new QueryServlet(productTable);
        DatabaseUtils.addProducts(PRODUCTS);
    }

    @AfterEach
    void tearDown() {
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testMax() throws IOException {
        Mockito.when(mockRequest.getParameter("command")).thenReturn("max");

        servlet.doGet(mockRequest, mockResponse);

        Product maxProduct = Collections.max(PRODUCTS, Comparator.comparingLong(Product::price));
        String expectedResponse = """
                <html>
                <body>
                <h1>
                Product with max price:\s
                </h1>
                %s	%d
                <br>
                </body>
                </html>
                """.formatted(maxProduct.name(), maxProduct.price());

        assertEquals(expectedResponse, getResponse());
    }

    @Test
    void testMin() throws IOException {
        Mockito.when(mockRequest.getParameter("command")).thenReturn("min");

        servlet.doGet(mockRequest, mockResponse);

        Product maxProduct = Collections.min(PRODUCTS, Comparator.comparingLong(Product::price));
        String expectedResponse = """
                <html>
                <body>
                <h1>
                Product with min price:\s
                </h1>
                %s	%d
                <br>
                </body>
                </html>
                """.formatted(maxProduct.name(), maxProduct.price());

        assertEquals(expectedResponse, getResponse());
    }

    @Test
    void testSum() throws IOException {
        Mockito.when(mockRequest.getParameter("command")).thenReturn("sum");

        servlet.doGet(mockRequest, mockResponse);

        long sumPrices = PRODUCTS.stream().mapToLong(Product::price).sum();
        String expectedResponse = """
                <html>
                <body>
                Summary price:\s
                %d
                </body>
                </html>
                """.formatted(sumPrices);

        assertEquals(expectedResponse, getResponse());
    }

    @Test
    void testCount() throws IOException {
        Mockito.when(mockRequest.getParameter("command")).thenReturn("count");

        servlet.doGet(mockRequest, mockResponse);

        long count = PRODUCTS.size();
        String expectedResponse = """
                <html>
                <body>
                Number of products:\s
                %d
                </body>
                </html>
                """.formatted(count);

        assertEquals(expectedResponse, getResponse());
    }
}
