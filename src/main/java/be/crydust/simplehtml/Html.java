package be.crydust.simplehtml;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public interface Html extends Iterable<Html> {

    // the ArrayDeque would grow capacity from 13528 to 20292 beyond this number
    int MAX_DEPTH = 13_526;

    @Nonnull
    static Html h(final String name) {
        return new Element(name, null, null);
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap) {
        return new Element(name, attributeMap, null);
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
        return new Element(name, attributeMap, asList(children));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final String text) {
        return new Element(name, attributeMap, singletonList(t(text)));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final List<? extends Html> children) {
        return new Element(name, attributeMap, children);
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

    void appendStartTo(StringBuilder sb);

    void appendEndTo(StringBuilder sb);

    default String getOuterHTML() {
        final StringBuilder sb = new StringBuilder();
        // this leeds to a StackOverflowError given deeply nested tags
        // appendStartTo(sb);
        // for (Html html : this) {
        //     html.appendTo(sb);
        // }
        // appendEndTo(sb);
        //
        // it is replaced by the more complex logic below
        // this is a depth first traversal of the tree
        final Deque<HtmlAndIterator> stack = new ArrayDeque<>();
        HtmlAndIterator current = new HtmlAndIterator(this);
        stack.push(current);
        current.html.appendStartTo(sb);
        while (!stack.isEmpty()) {
            current = Objects.requireNonNull(stack.peek(), "current");
            while (current.iterator.hasNext()) {
                if (stack.size() > MAX_DEPTH) {
                    throw new IllegalStateException("Sorry, html is nested too deeply. MAX_DEPTH = " + MAX_DEPTH);
                }
                current = new HtmlAndIterator(current.iterator.next());
                stack.push(current);
                current.html.appendStartTo(sb);
            }
            current.html.appendEndTo(sb);
            stack.pop();
        }
        return sb.toString();
    }

    default Optional<String> getAttribute(String name) {
        return Optional.empty();
    }

}
