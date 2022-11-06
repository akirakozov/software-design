package ru.akirakozov.sd.refactoring.html;

import java.util.List;
import java.util.stream.Collectors;

public abstract class HtmlElement {
    private final List<HtmlElement> elements;

    protected HtmlElement(List<HtmlElement> elements) {
        this.elements = elements;
    }

    protected HtmlElement(HtmlElement... elements) {
        this(List.of(elements));
    }

    protected abstract String startTag();

    protected abstract String endTag();

    @Override
    public String toString() {
        return elements.stream()
                .map(HtmlElement::toString)
                .collect(Collectors.joining("\n", startTag() + "\n",    "\n" + endTag()));
    }
}
