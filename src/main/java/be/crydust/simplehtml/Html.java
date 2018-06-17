package be.crydust.simplehtml;

import java.util.List;
import java.util.Map;

import static be.crydust.simplehtml.HtmlUtil.convertMapToAttributes;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public interface Html extends Iterable<Html> {

    static Html h(final String name) {
        return new Element(name, null, null);
    }

    static Html h(final String name, final Map<String, String> attributeMap) {
        return new Element(name, convertMapToAttributes(attributeMap), null);
    }

    static Html h(final String name, final List<? extends Html> children) {
        return new Element(name, null, children);
    }

    static Html h(final String name, final Html... children) {
        return new Element(name, null, asList(children));
    }

    static Html h(final String name, final String text) {
        return new Element(name, null, singletonList(t(text)));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final Html... children) {
        return new Element(name, convertMapToAttributes(attributeMap), asList(children));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final String text) {
        return new Element(name, convertMapToAttributes(attributeMap), singletonList(t(text)));
    }

    static Html h(final String name, final Map<String, String> attributeMap, final List<? extends Html> children) {
        return new Element(name, convertMapToAttributes(attributeMap), children);
    }

    static Html t(final String content) {
        return new Text(content);
    }

    static Html r(final String content) {
        return new Raw(content);
    }

    static Html h(final Html... children) {
        return new Fragment(asList(children));
    }

    static Html h(final List<? extends Html> children) {
        return new Fragment(children);
    }

    void appendTo(StringBuilder sb);

    void appendStartTo(StringBuilder sb);

    void appendEndTo(StringBuilder sb);

}
