package be.crydust.simplehtml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableList;

final class Element implements Html {

    // more restrictive than theoretically allowed:
    // namespace, tagname, id and classnames must start with a-z and can contain dash, underscore, a-z and 0-9
    // format is: namespace:tagname#id.class0.class1
    // you can't use a period in the id read from the tag name
    private static final String TAG_NAME_PART_REGEX = "" +
            "(?:" +
            "(?:[A-Za-z][-_A-Za-z0-9]*:)?[A-Za-z][-_A-Za-z0-9]*" +
            "|" +
            "#[A-Za-z][-_A-Za-z0-9:]*" +
            "|" +
            "\\.[A-Za-z][-_A-Za-z0-9]*" +
            ")";
    private static final Pattern TAG_NAME_PART = Pattern.compile(TAG_NAME_PART_REGEX);
    private static final Pattern TAG_NAME_PARTS = Pattern.compile(TAG_NAME_PART_REGEX + "*");
    private static final Pattern EMPTY_TAGS = Pattern.compile("(?i)area|base|br|col|embed|hr|img|input|keygen|link|meta|param|source|track|wbr");
    private final String name;
    private final Set<Attribute> attributes;
    private final List<Html> children;
    private final boolean empty;

    Element(final String name, final Map<String, String> attributes, final List<? extends Html> children) {
        if (!TAG_NAME_PARTS.matcher(Objects.requireNonNull(name, "name")).matches()) {
            throw new IllegalArgumentException("Element name '" + name + "' is not valid");
        }
        String tempName = "div";

        final HashMap<String, String> tempAttributeMap = new HashMap<>(attributes == null ? emptyMap() : attributes);

        final Matcher nameOrIdOrClassMatcher = TAG_NAME_PART.matcher(name);
        while (nameOrIdOrClassMatcher.find()) {
            final String nameOrIdOrClass = nameOrIdOrClassMatcher.group();
            if (nameOrIdOrClass.startsWith("#")) {
                final String id = nameOrIdOrClass.substring(1);
                if (tempAttributeMap.containsKey("id") && !id.equals(tempAttributeMap.get("id"))) {
                    throw new IllegalArgumentException("Ambiguous id attribute: is it '" + id + "' or '" + tempAttributeMap.get("id") + "'");
                }
                tempAttributeMap.put("id", id);
            } else if (nameOrIdOrClass.startsWith(".")) {
                final String cssClass = nameOrIdOrClass.substring(1);
                tempAttributeMap.merge("class", cssClass, (a, b) -> a + " " + b);
            } else {
                tempName = nameOrIdOrClass;
            }
        }
        this.name = tempName;
        this.empty = EMPTY_TAGS.matcher(tempName).matches();

        this.attributes = Collections.unmodifiableSortedSet(tempAttributeMap.entrySet().stream()
                .map(entry -> new Attribute(entry.getKey(), entry.getValue()))
                .collect(Collectors.toCollection(TreeSet::new)));

        this.children = (this.empty || children == null)
                ? emptyList()
                : unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public String toString() {
        return "Element{name='" + name + "'}";
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

}
