package be.crydust.simplehtml;

import java.util.List;
import java.util.Map;

import static be.crydust.simplehtml.HtmlElement.mapToHtmlAttributes;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public interface Html extends Iterable<Html> {

    static Html h(final String name) {
        return new HtmlElement(name, null, null);
    }

    static Html h(final String name, final Map<String, String> attributeMap) {
        return new HtmlElement(name, mapToHtmlAttributes(attributeMap), null);
    }

    static Html h(final String name, final List<? extends Html> children) {
        return new HtmlElement(name, null, children);
    }

    static Html h(final String name, final Html... children) {
        return new HtmlElement(name, null, asList(children));
    }

    static Html h(final String name, final String text) {
        return new HtmlElement(name, null, singletonList(t(text)));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final Html... children) {
        return new HtmlElement(name, mapToHtmlAttributes(attributeMap), asList(children));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final String text) {
        return new HtmlElement(name, mapToHtmlAttributes(attributeMap), singletonList(t(text)));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final List<? extends Html> children) {
        return new HtmlElement(name, mapToHtmlAttributes(attributeMap), children);
    }

    static Html t(final String content) {
        return new HtmlText(content);
    }

    void appendStartTo(StringBuilder sb);

    void appendEndTo(StringBuilder sb);
}
