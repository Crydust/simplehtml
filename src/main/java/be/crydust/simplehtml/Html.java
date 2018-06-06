package be.crydust.simplehtml;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static be.crydust.simplehtml.HtmlElement.mapToHtmlAttributes;

import java.util.List;
import java.util.Map;

public interface Html {

	static HtmlElement h(String name) {
		return new HtmlElement(name, null, null);
	}

	static HtmlElement h(String name, Map<String, String> attributeMap) {
		return new HtmlElement(name, mapToHtmlAttributes(attributeMap), null);
	}

	static HtmlElement h(String name, List<Html> children) {
		return new HtmlElement(name, null, children);
	}

	static HtmlElement h(String name, Html... children) {
		return new HtmlElement(name, null, asList(children));
	}

	static HtmlElement h(String name, String text) {
		return new HtmlElement(name, null, singletonList(t(text)));
	}

	static HtmlElement h(String name, Map<String, String> attributeMap, Html... children) {
		return new HtmlElement(name, mapToHtmlAttributes(attributeMap), asList(children));
	}

	static HtmlElement h(String name, Map<String, String> attributeMap, String text) {
		return new HtmlElement(name, mapToHtmlAttributes(attributeMap), singletonList(t(text)));
	}

	static HtmlElement h(String name, Map<String, String> attributeMap, List<Html> children) {
		return new HtmlElement(name, mapToHtmlAttributes(attributeMap), children);
	}

	static Html t(String content) {
		return new HtmlText(content);
	}

}
