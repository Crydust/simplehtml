package be.crydust.simplehtml;

class HtmlText implements Html {
	private final String text;

	HtmlText(final String text) {
		this.text = text == null ? "" : text;
	}

	@Override
	public String toString() {
		return HtmlElement.encode(text);
	}
}
