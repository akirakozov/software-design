package ru.akirakozov.sd.refactoring.html;

public class H1 extends HtmlElement {
    public H1(HtmlElement element) {
        super(element);
    }

    @Override
    protected String startTag() {
        return "<h1>";
    }

    @Override
    protected String endTag() {
        return "</h1>";
    }
}
