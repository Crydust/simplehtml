package be.crydust.simplehtml;

import java.util.Objects;
import java.util.regex.Pattern;

import static be.crydust.simplehtml.HtmlUtil.encode;

final class Attribute implements Comparable<Attribute> {

    // more restrictive than theoretically allowed:
    // must start with a-z and can contain dash, underscore, a-z and 0-9.
    private static final Pattern VALID_ATTRIBUTE_NAME = Pattern.compile("(?i)(?:[a-z][-_a-z0-9]*:)?[a-z][-_a-z0-9]*");

    private final String name;
    private final String value;

    Attribute(final String name, final String value) {
        if (!VALID_ATTRIBUTE_NAME.matcher(Objects.requireNonNull(name, "name")).matches()) {
            throw new IllegalArgumentException("Attribute name '" + name + "' is not valid");
        }
        this.name = name;
        this.value = value == null ? "" : value;
    }

    String getName() {
        return name;
    }

    String getValue() {
        return value;
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
        return "Attribute{name='" + name + "'}";
    }

    void appendTo(final StringBuilder sb) {
        sb.append(' ').append(name).append("=\"").append(encode(value)).append('"');
    }

    @Override
    public int compareTo(Attribute o) {
        return this.name.compareTo(o.name);
    }
}
