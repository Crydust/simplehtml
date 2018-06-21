package be.crydust.simplehtml;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class HtmlUtil {
    private static final Pattern HTML_CHARS = Pattern.compile("[&<>\"'`]");

    private HtmlUtil() {
        throw new UnsupportedOperationException("please don't create instances of this class");
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

    static Set<Attribute> convertMapToAttributes(final Map<String, String> attributeMap) {
        final Set<Attribute> attributes = new HashSet<>();
        for (final Map.Entry<String, String> entry : attributeMap.entrySet()) {
            if (!attributes.add(new Attribute(entry.getKey(), entry.getValue()))) {
                throw new IllegalArgumentException("duplicate attribute '" + entry.getKey() + "=" + entry.getValue() + "'");
            }
        }
        return attributes;
    }
}
