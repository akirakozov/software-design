package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import ru.akirakozov.sd.refactoring.dao.Product;
import ru.akirakozov.sd.refactoring.dao.ProductTable;
import ru.akirakozov.sd.refactoring.db.Database;
import ru.akirakozov.sd.refactoring.utils.DatabaseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.stream.IntStream;

import static org.mockito.Mockito.mock;

public abstract class AbstractServletTest {
    protected static final List<Product> PRODUCTS = IntStream.range(0, 10)
            .mapToObj(i -> new Product("product" + i, i * 10L))
            .toList();
    protected HttpServletRequest mockRequest;
    protected HttpServletResponse mockResponse;
    protected StringWriter response;
    protected ProductTable productTable;

    @BeforeEach
    void init() throws IOException {
        DatabaseUtils.initTable();
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);

        response = new StringWriter();
        PrintWriter printWriter = new PrintWriter(response);
        Mockito.when(mockResponse.getWriter()).thenReturn(printWriter);

        Database database = new Database(DatabaseUtils.CONNECTION_URL);
        productTable = new ProductTable(database);
    }

    @AfterEach
    void shutdown() {
        DatabaseUtils.truncateTable();
    }

    protected String getResponse() {
        return response.toString().replace(System.lineSeparator(), "\n");
    }
}
