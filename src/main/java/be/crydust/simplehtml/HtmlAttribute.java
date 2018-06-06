package be.crydust.simplehtml;

import java.util.Objects;

class HtmlAttribute {
	private final String name;
	private final String value;

	HtmlAttribute(String name, String value) {
		final String trimmedName = Objects.requireNonNull(name, "name").trim();
		if (trimmedName.isEmpty()) {
			throw new IllegalArgumentException("tagname is empty");
		}
		this.name = trimmedName;
		this.value = value == null ? "" : value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		HtmlAttribute that = (HtmlAttribute) o;
		return Objects.equals(name, that.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public String toString() {
		return ' ' + HtmlElement.encode(name) + "=\"" + HtmlElement.encode(value) + '"';
	}
}
