package be.crydust.simplehtml;

final class HtmlText implements Html {
	private final String text;

	HtmlText(final String text) {
		this.text = text == null ? "" : text;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		appendTo(sb);
		return sb.toString();
	}

	@Override
	public void appendTo(StringBuilder sb) {
		sb.append(HtmlElement.encode(text));
	}
}
