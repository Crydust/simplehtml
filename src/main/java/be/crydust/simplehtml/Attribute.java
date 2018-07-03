package be.crydust.simplehtml;

import java.util.Objects;
import java.util.regex.Pattern;

import static be.crydust.simplehtml.HtmlUtil.encode;

final class Attribute implements Comparable<Attribute> {

    // more restrictive than theoretically allowed:
    // must start with a-z and can contain dash, underscore, a-z and 0-9.
    private static final Pattern VALID_ATTRIBUTE_NAME = Pattern.compile("(?:[A-Za-z][-_A-Za-z0-9]*:)?[A-Za-z][-_A-Za-z0-9]*");

    // https://www.w3.org/TR/html401/types.html#type-name
    // ID and NAME tokens must begin with a letter ([A-Za-z]) and may be followed by any number of letters, digits ([0-9]), hyphens ("-"), underscores ("_"), colons (":"), and periods (".").
    private static final Pattern VALID_ID_OR_NAME_VALUE = Pattern.compile("[A-Za-z][-_:.A-Za-z0-9]*");

    private final String name;
    private final String value;

    Attribute(final String name, final String value) {
        if (!VALID_ATTRIBUTE_NAME.matcher(Objects.requireNonNull(name, "name")).matches()) {
            throw new IllegalArgumentException("Attribute name '" + name + "' is not valid");
        }
        if ("id".equalsIgnoreCase(name) || "name".equalsIgnoreCase(name)) {
            if (value == null || !VALID_ID_OR_NAME_VALUE.matcher(value).matches()) {
                throw new IllegalArgumentException("Attribute value '" + value + "' is not valid for '" + name + "'");
            }
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
