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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		appendTo(sb);
		return sb.toString();
	}

	@Override
	public void appendTo(StringBuilder sb) {
		final String encodedName = encode(name);
		sb
				.append('<')
				.append(encodedName);
		for (final HtmlAttribute attribute : this.attributes) {
			attribute.appendTo(sb);
		}
		if (empty) {
			sb.append("/>");
		} else {
			sb.append('>');
			for (final Html child : this.children) {
				child.appendTo(sb);
			}
			sb.append("</")
					.append(encodedName)
					.append('>');
		}
	}

}
