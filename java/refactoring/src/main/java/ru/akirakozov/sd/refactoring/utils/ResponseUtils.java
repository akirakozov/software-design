package ru.akirakozov.sd.refactoring.utils;

import ru.akirakozov.sd.refactoring.html.HtmlElement;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    private ResponseUtils() {
    }

    public static void setHtmlResponse(HttpServletResponse response, HtmlElement htmlElement) throws IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(htmlElement);
    }
}
