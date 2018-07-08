package be.crydust.simplehtml;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

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
        return new Element(name, null, singletonList(text(text)));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final Html... children) {
        return new Element(name, attributeMap, asList(children));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final String text) {
        return new Element(name, attributeMap, singletonList(text(text)));
    }

    @Nonnull
    static Html h(final String name, final Map<String, String> attributeMap, final List<? extends Html> children) {
        return new Element(name, attributeMap, children);
    }

    @Nonnull
    static Html text(final String content) {
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

    static void walk(Html root, Consumer<Html> startElement, Consumer<Html> endElement) {
        final LimitedSizeStack<HtmlAndIterator> stack = new LimitedSizeStack<>(MAX_DEPTH);
        stack.push(new HtmlAndIterator(root));
        startElement.accept(stack.peek().html);
        while (!stack.isEmpty()) {
            while (stack.peek().iterator.hasNext()) {
                stack.push(new HtmlAndIterator(stack.peek().iterator.next()));
                startElement.accept(stack.peek().html);
            }
            endElement.accept(stack.pop().html);
        }
    }

    default String getOuterHTML() {
        final Set<String> ids = new HashSet<>();
        final StringBuilder sb = new StringBuilder();
        walk(
                this,
                html -> {
                    html.appendStartTo(sb);
                    html.getAttribute("id")
                            .filter(id -> !ids.add(id))
                            .ifPresent(id -> {
                                throw new IllegalStateException("The '" + id + "' id is used twice");
                            });
                },
                html -> html.appendEndTo(sb)
        );
        return sb.toString();
    }

    default void appendStartTo(StringBuilder sb) {
        // NOOP
    }

    default void appendEndTo(StringBuilder sb) {
        // NOOP
    }

    default Optional<String> getAttribute(String name) {
        return Optional.empty();
    }

}
