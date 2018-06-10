package be.crydust.simplehtml;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

final class HtmlElement implements Html {

    private static final Pattern EMPTY_TAGS = Pattern.compile("(?i)area|base|br|col|embed|hr|img|input|keygen|link|meta|param|source|track|wbr");
    private static final Pattern HTML_CHARS = Pattern.compile("[&<>\"'`]");

    private final String name;
    private final Set<HtmlAttribute> attributes;
    private final List<Html> children;
    private final boolean empty;

    HtmlElement(final String name, final Set<? extends HtmlAttribute> attributes, final List<? extends Html> children) {
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

    static Set<HtmlAttribute> mapToHtmlAttributes(final Map<String, String> attributeMap) {
        final Set<HtmlAttribute> attributes = new HashSet<>();
        for (final Map.Entry<String, String> entry : attributeMap.entrySet()) {
            if (!attributes.add(new HtmlAttribute(entry.getKey(), entry.getValue()))) {
                throw new IllegalArgumentException("duplicate attribute '" + entry.getKey() + "=" + entry.getValue() + "'");
            }
        }
        return attributes;
    }

    static String encode(final String s) {
        // Escape html without regard for unquoted attributes.
        // see https://wonko.com/post/html-escaping
        if (s == null || s.isEmpty()) {
            return "";
        }
        final Matcher matcher = HTML_CHARS.matcher(s);
        int cursor = 0;
        if (!matcher.find()) {
            return s;
        }
        final StringBuilder sb = new StringBuilder();
        do {
            final String replacement;
            switch (matcher.group().charAt(0)) {
                case '&':
                    replacement = "&amp;";
                    break;
                case '<':
                    replacement = "&lt;";
                    break;
                case '>':
                    replacement = "&gt;";
                    break;
                case '"':
                    replacement = "&quot;";
                    break;
                case '\'':
                    replacement = "&#x27;";
                    break;
                case '`':
                    replacement = "&#x60;";
                    break;
                default:
                    throw new IllegalStateException("this character is not supported");
            }
            sb.append(s, cursor, matcher.start());
            cursor = matcher.end();
            sb.append(replacement);
        } while (matcher.find());
        sb.append(s, cursor, s.length());
        return sb.toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Deque<HtmlAndIterator> stack = new ArrayDeque<>();
        HtmlAndIterator current = new HtmlAndIterator(this);
        stack.push(current);
        current.html.appendStartTo(sb);
        while (!stack.isEmpty()) {
            current = Objects.requireNonNull(stack.peek(), "current");
            while (current.iterator.hasNext()) {
                current = new HtmlAndIterator(current.iterator.next());
                stack.push(current);
                current.html.appendStartTo(sb);
            }
            current.html.appendEndTo(sb);
            stack.pop();
        }
        return sb.toString();
    }

    @Override
    public void appendStartTo(StringBuilder sb) {
        sb.append('<').append(encode(name));
        for (final HtmlAttribute attribute : this.attributes) {
            attribute.appendTo(sb);
        }
        sb.append(empty ? "/>" : '>');
    }

    @Override
    public void appendEndTo(StringBuilder sb) {
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

        HtmlAndIterator(Html html) {
            this.html = html;
            this.iterator = html.iterator();
        }
    }

}
