package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddProductServletTest extends AbstractServletTest {
    private static final Product SAMPLE = new Product("product name", 42);

    private AddProductServlet servlet;

    @BeforeEach
    void setUp() {
        servlet = new AddProductServlet(productTable);
        mockRequest();
    }

    @AfterEach
    void tearDown() {
        Mockito.verify(mockResponse).setStatus(HttpServletResponse.SC_OK);
    }

    @Test
    void testResponseAddNewProduct() throws IOException {
        servlet.doGet(mockRequest, mockResponse);

        assertEquals("OK" + System.lineSeparator(), response.toString());
    }

    @Test
    void testDBAddNewProduct() throws IOException {
        servlet.doGet(mockRequest, mockResponse);

        List<Product> products = DatabaseUtils.getAllProducts();

        assertEquals(List.of(SAMPLE), products);
    }

    private void mockRequest() {
        Mockito.when(mockRequest.getParameter("name")).thenReturn(SAMPLE.name());
        Mockito.when(mockRequest.getParameter("price")).thenReturn(Long.toString(SAMPLE.price()));
    }
}