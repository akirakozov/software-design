package ru.akirakozov.sd.refactoring.html;

public class Text extends HtmlElement {
    private final String text;

    public Text(String text) {
        this.text = text;
    }

    @Override
    protected String startTag() {
        return null;
    }

    @Override
    protected String endTag() {
        return null;
    }

    @Override
    public String toString() {
        return text;
    }
}
