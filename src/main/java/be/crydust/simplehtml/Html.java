package be.crydust.simplehtml;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static be.crydust.simplehtml.HtmlUtil.convertMapToAttributes;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public interface Html extends Iterable<Html> {

    @Nonnull
    static Html h(final String name) {
        return new Element(name, null, null);
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap) {
        return new Element(name, convertMapToAttributes(attributeMap), null);
    }

    @Nonnull
    static Html h(final String name, final List<? extends Html> children) {
        return new Element(name, null, children);
    }

    @Nonnull
    static Html h(final String name, final Html... children) {
        return new Element(name, null, asList(children));
    }

    @Nonnull
    static Html h(final String name, final String text) {
        return new Element(name, null, singletonList(t(text)));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final Html... children) {
        return new Element(name, convertMapToAttributes(attributeMap), asList(children));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final String text) {
        return new Element(name, convertMapToAttributes(attributeMap), singletonList(t(text)));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final List<? extends Html> children) {
        return new Element(name, convertMapToAttributes(attributeMap), children);
    }

    @Nonnull
    static Html t(final String content) {
        return new Text(content);
    }

    @Nonnull
    static Html h(final Html... children) {
        return new Fragment(asList(children));
    }

    @Nonnull
    static Html h(final List<? extends Html> children) {
        return new Fragment(children);
    }

    void appendTo(StringBuilder sb);

    void appendStartTo(StringBuilder sb);

    void appendEndTo(StringBuilder sb);

    default String getOuterHTML() {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    default Optional<String> getAttribute(String name) {
        return Optional.empty();
    }

}
