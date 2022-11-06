package ru.akirakozov.sd.refactoring.html;

public class Br extends HtmlElement {
    @Override
    protected String startTag() {
        return "<br>";
    }

    @Override
    protected String endTag() {
        return "";
    }

    @Override
    public String toString() {
        return "<br>";
    }
}
