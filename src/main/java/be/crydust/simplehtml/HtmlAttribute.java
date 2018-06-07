package be.crydust.simplehtml;

import java.util.Objects;

final class HtmlAttribute {
	private final String name;
	private final String value;

	HtmlAttribute(final String name, final String value) {
		final String trimmedName = Objects.requireNonNull(name, "name").trim();
		if (trimmedName.isEmpty()) {
			throw new IllegalArgumentException("tagname is empty");
		}
		this.name = trimmedName;
		this.value = value == null ? "" : value;
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		final HtmlAttribute that = (HtmlAttribute) o;
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

	void appendTo(StringBuilder sb) {
		sb.append(' ').append(HtmlElement.encode(name)).append("=\"").append(HtmlElement.encode(value)).append('"');
	}
}
