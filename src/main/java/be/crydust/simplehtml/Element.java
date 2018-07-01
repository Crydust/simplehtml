package be.crydust.simplehtml;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

final class Element implements Html {

    // the ArrayDeque would grow capacity from 13528 to 20292 beyond this number
    private static final int MAX_DEPTH = 13_526;
    // more restrictive than theoretically allowed:
    // must start with a-z and can contain dash, underscore, a-z and 0-9.
    private static final Pattern VALID_TAG_NAME = Pattern.compile("(?i)(?<name>(?:[a-z][-_a-z0-9]*:)?[a-z][-_a-z0-9]*)(?:[.#][a-z][-_a-z0-9]*)*");
    private static final Pattern ID_AND_CLASS = Pattern.compile("(?i)(?:[.#][a-z][-_a-z0-9]*)");
    private static final Pattern EMPTY_TAGS = Pattern.compile("(?i)area|base|br|col|embed|hr|img|input|keygen|link|meta|param|source|track|wbr");
    private final String name;
    private final Set<Attribute> attributes;
    private final List<Html> children;
    private final boolean empty;

    Element(final String name, final Set<? extends Attribute> attributes, final List<? extends Html> children) {
        final Matcher nameMatcher = VALID_TAG_NAME.matcher(Objects.requireNonNull(name, "name"));
        if (!nameMatcher.matches()) {
            throw new IllegalArgumentException("Element name '" + name + "' is not valid");
        }
        this.name = nameMatcher.group("name");
        this.empty = EMPTY_TAGS.matcher(name).matches();

        final HashMap<String, Attribute> attributeMap = new HashMap<>();
        if (attributes != null) {
            attributeMap.putAll(attributes.stream()
                    .collect(Collectors.toMap(Attribute::getName, a -> a)));
        }
        final Matcher idOrClasses = ID_AND_CLASS.matcher(name);
        while (idOrClasses.find()) {
            final String idOrClass = idOrClasses.group();
            if (idOrClass.startsWith("#")) {
                if (attributeMap.containsKey("id")) {
                    throw new IllegalArgumentException("duplicate id attribute");
                }
                attributeMap.put("id", new Attribute("id", idOrClass.substring(1)));
            } else {
                attributeMap.merge("class", new Attribute("class", idOrClass.substring(1)),
                        (a, b) -> new Attribute("class", a.getValue() + " " + b.getValue()));
            }
        }

        this.attributes = attributeMap.isEmpty()
                ? emptySet()
                : unmodifiableSet(new HashSet<>(attributeMap.values()));
        this.children = (this.empty || children == null)
                ? emptyList()
                : unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public String toString() {
        return "Element{name='" + name + "'}";
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
        sb.append('<').append(name);
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
        sb.append("</").append(name).append('>');
    }

    @Override
    public Iterator<Html> iterator() {
        return children.iterator();
    }

    @Override
    public Optional<String> getAttribute(String name) {
        if (Objects.requireNonNull(name, "name").isEmpty()) {
            throw new IllegalArgumentException("name is empty");
        }
        return this.attributes.stream()
                .filter(a -> name.equals(a.getName()))
                .map(Attribute::getValue)
                .findAny();
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
