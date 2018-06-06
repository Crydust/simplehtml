package be.crydust.simplehtml;

import static java.util.Collections.emptyList;
import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableList;
import static java.util.Collections.unmodifiableSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

final class HtmlElement implements Html {

	private static final Pattern EMPTY_TAGS = Pattern.compile("(?i)area|base|br|col|embed|hr|img|input|keygen|link|meta|param|source|track|wbr");

	private final String name;
	private final Set<HtmlAttribute> attributes;
	private final List<Html> children;
	private final boolean empty;

	HtmlElement(String name, Set<HtmlAttribute> attributes, List<Html> children) {
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

	static Set<HtmlAttribute> mapToHtmlAttributes(Map<String, String> attributeMap) {
		final Set<HtmlAttribute> attributes = new HashSet<>();
		for (Map.Entry<String, String> entry : attributeMap.entrySet()) {
			if (!attributes.add(new HtmlAttribute(entry.getKey(), entry.getValue()))) {
				throw new IllegalArgumentException("duplicate attribute '" + entry.getKey() + "=" + entry.getValue() + "'");
			}
		}
		return attributes;
	}

	static String encode(String s) {
		if (s == null) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if (c >= 256) {
				sb.append(c);
			} else if (c == '&') {
				sb.append("&amp;");
			} else if (c == '<') {
				sb.append("&lt;");
			} else if (c == '>') {
				sb.append("&gt;");
			} else if (c == '"') {
				sb.append("&quot;");
			} else if (c == '\'') {
				sb.append("&#x27;");
			} else if (c == '/') {
				sb.append("&#x2F;");
			} else if (!Character.isLetterOrDigit((int) c)) {
				sb.append("&#");
				sb.append((int) c);
				sb.append(';');
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder()
				.append('<')
				.append(encode(name));
		for (HtmlAttribute attribute : this.attributes) {
			sb.append(attribute);
		}
		if (empty) {
			sb.append("/>");
		} else {
			sb.append('>');
			for (Html child : this.children) {
				sb.append(child);
			}
			sb.append("</")
					.append(encode(name))
					.append('>');
		}
		return sb.toString();
	}

}
