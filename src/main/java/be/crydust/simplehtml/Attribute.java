package be.crydust.simplehtml;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

import static be.crydust.simplehtml.HtmlUtil.encode;

final class Attribute {

    // more restrictive than theoretically allowed:
    // must start with a-z and contain dash, underscore a-z and 0-9.
    private static final Pattern VALID_ATTRIBUTE_NAME = Pattern.compile("(?i)[a-z][-_a-z0-9]*");

    private final String name;
    private final String value;

    private Attribute(final String name, final String value) {
        if (!VALID_ATTRIBUTE_NAME.matcher(Objects.requireNonNull(name, "name")).matches()) {
            throw new IllegalArgumentException("name '" + name + "' is not valid");
        }
        this.name = name;
        this.value = value == null ? "" : value;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Attribute that = (Attribute) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    void appendTo(final StringBuilder sb) {
        sb.append(' ').append(name).append("=\"").append(encode(value)).append('"');
    }
}
