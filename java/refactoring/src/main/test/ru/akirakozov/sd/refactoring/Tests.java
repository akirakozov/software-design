package ru.akirakozov.sd.refactoring;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.Jsoup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/*
 * Checks server API for correctness. Expects server to be started in test environment.
 */
class MainTest {
    public enum RequestType {
        ADD,
        GET,
        QUERY,
        CLEAR,
    }

    String getRequestPathByType(RequestType requestType) {
        switch (requestType) {
            case ADD:
                return "/add-product";
            case GET:
                return "/get-products";
            case QUERY:
                return  "/query";
            case CLEAR:
                return "/clear";
            default:
                throw new RuntimeException("Invalid request type: " + requestType.toString());
        }
    }

    String request(RequestType requestType, List<Pair<String, String>> params) throws IOException {
        URI url;
        try {
            URIBuilder builder = new URIBuilder();
            builder.setScheme("http");
            builder.setHost("localhost");
            builder.setPort(8081);
            builder.setPath(getRequestPathByType(requestType));
            for (Pair<String, String> param : params) {
                builder.addParameter(param.getLeft(), param.getRight());
            }
            url = builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException("Invalid request: " + e.getMessage());
        }
        return Jsoup.connect(url.toString()).get().text();
    }

    String requestAdd(String name, Integer price) throws IOException {
        return request(RequestType.ADD,
                List.of(Pair.of("name", name), Pair.of("price", price.toString())));
    }

    String requestGet() throws IOException {
        return request(RequestType.GET, List.of());
    }

    String requestQuery(String queryType) throws IOException {
        return request(RequestType.QUERY, List.of(Pair.of("command", queryType)));
    }

    @BeforeEach
    @AfterEach
    void clearDB() {
        assertDoesNotThrow(() -> request(RequestType.CLEAR, List.of()));
    }

    @Test
    void emptyDatabase() throws IOException {
        assertEquals("", requestGet());
    }

    @Test
    void addSomeProducts() throws IOException {
        assertEquals("OK", requestAdd("milk", 100));
        assertEquals("OK", requestAdd("water", 20));
        assertEquals("OK", requestAdd("milk", 90));

        assertEquals("milk 100 water 20 milk 90", requestGet());
    }

    @Test
    void queryCount() throws IOException {
        assertEquals("Number of products: 0", requestQuery("count"));

        assertEquals("OK", requestAdd("milk", 100));
        assertEquals("OK", requestAdd("beer", 200));

        assertEquals("Number of products: 2", requestQuery("count"));
    }

    @Test
    void queryMin() throws IOException {
        assertEquals("Product with min price:", requestQuery("min"));

        assertEquals("OK", requestAdd("milk", 100));
        assertEquals("Product with min price: milk 100", requestQuery("min"));

        assertEquals("OK", requestAdd("beer", 200));
        assertEquals("OK", requestAdd("milk", 90));
        assertEquals("Product with min price: milk 90", requestQuery("min"));
    }

    @Test
    void queryMax() throws IOException {
        assertEquals("Product with max price:", requestQuery("max"));

        assertEquals("OK", requestAdd("milk", 100));
        assertEquals("Product with max price: milk 100", requestQuery("max"));

        assertEquals("OK", requestAdd("beer", 200));
        assertEquals("OK", requestAdd("milk", 90));
        assertEquals("Product with max price: beer 200", requestQuery("max"));
    }

    @Test
    void querySum() throws IOException {
        assertEquals("Summary price: 0", requestQuery("sum"));

        assertEquals("OK", requestAdd("milk", 100));
        assertEquals("Summary price: 100", requestQuery("sum"));

        assertEquals("OK", requestAdd("beer", 200));
        assertEquals("OK", requestAdd("milk", 90));
        assertEquals("Summary price: 390", requestQuery("sum"));
    }

    @Test
    void undefinedQuery() throws IOException {
        assertEquals("Unknown command: average", requestQuery("average"));
    }
}