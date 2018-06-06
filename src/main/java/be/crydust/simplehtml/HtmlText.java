package be.crydust.simplehtml;

class HtmlText implements Html {
	private final String text;

	HtmlText(String text) {
		this.text = text == null ? "" : text;
	}

	@Override
	public String toString() {
		return HtmlElement.encode(text);
	}
}
