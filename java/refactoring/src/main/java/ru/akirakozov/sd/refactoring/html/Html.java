package ru.akirakozov.sd.refactoring.html;

import java.util.List;

public class Html extends HtmlElement {
    public Html(HtmlElement... elements) {
        super(elements);
    }

    public Html(List<HtmlElement> elements) {
        super(elements);
    }

    @Override
    protected String startTag() {
        return "<html>";
    }

    @Override
    protected String endTag() {
        return "</html>";
    }
}
