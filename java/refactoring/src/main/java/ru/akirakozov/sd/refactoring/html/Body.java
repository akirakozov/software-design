package ru.akirakozov.sd.refactoring.html;

import java.util.List;

public class Body extends HtmlElement {
    public Body(HtmlElement... elements) {
        super(elements);
    }

    public Body(List<HtmlElement> elements) {
        super(elements);
    }

    @Override
    protected String startTag() {
        return "<body>";
    }

    @Override
    protected String endTag() {
        return "</body>";
    }
}
