package be.crydust.simplehtml;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static be.crydust.simplehtml.HtmlUtil.encode;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

final class Element implements Html {

    // the ArrayDeque would grow capacity from 13528 to 20292 beyond this number
    private static final int MAX_DEPTH = 13_526;
    private static final Pattern EMPTY_TAGS = Pattern.compile("(?i)area|base|br|col|embed|hr|img|input|keygen|link|meta|param|source|track|wbr");
    private final String name;
    private final Set<Attribute> attributes;
    private final List<Html> children;
    private final boolean empty;

    Element(final String name, final Set<? extends Attribute> attributes, final List<? extends Html> children) {
        final String trimmedName = Objects.requireNonNull(name, "name").trim();
        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("tagname is empty");
        }
        this.name = trimmedName;
        this.empty = EMPTY_TAGS.matcher(name).matches();
        this.attributes = attributes == null
                ? emptySet()
                : unmodifiableSet(new HashSet<>(attributes));
        this.children = (this.empty || children == null)
                ? emptyList()
                : unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    @Override
    public void appendTo(StringBuilder sb) {
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
    }

    @Override
    public void appendStartTo(final StringBuilder sb) {
        sb.append('<').append(encode(name));
        for (final Attribute attribute : this.attributes) {
            attribute.appendTo(sb);
        }
        if (empty) {
            sb.append('/');
        }
        sb.append('>');
    }

    @Override
    public void appendEndTo(final StringBuilder sb) {
        if (empty) {
            return;
        }
        sb.append("</").append(encode(name)).append('>');
    }

    @Override
    public Iterator<Html> iterator() {
        return children.iterator();
    }

    private static class HtmlAndIterator {
        final Html html;
        final Iterator<Html> iterator;

        HtmlAndIterator(final Html html) {
            this.html = html;
            this.iterator = html.iterator();
        }
    }

}
