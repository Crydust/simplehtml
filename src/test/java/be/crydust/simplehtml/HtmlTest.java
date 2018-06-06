package be.crydust.simplehtml;

import static be.crydust.simplehtml.Html.h;
import static be.crydust.simplehtml.Html.t;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HtmlTest {

	@Test
	public void example1() {
		final String html = h("div", Java9Map.of("id", "foo", "name", "bar"), "Hello!")
				.toString();
		assertThat(html, is("<div name=\"bar\" id=\"foo\">Hello&#33;</div>"));
	}

	@Test
	public void example2() {
		final String html = h("div", Java9Map.of("id", "foo"),
				h("p", "Look, a simple JSX DOM renderer!"),
				h("ul",
						h("li", h("p", "hello")),
						h("li", h("p", "there")),
						h("li", h("p", "people"))
				)
		).toString();
		assertThat(html, is("<div id=\"foo\"><p>Look&#44;&#32;a&#32;simple&#32;JSX&#32;DOM&#32;renderer&#33;</p><ul><li><p>hello</p></li><li><p>there</p></li><li><p>people</p></li></ul></div>"));
	}

	@Test
	public void example3() {
		final String html = h(
				"p",
				Java9Map.of("class", "editor-note"),
				t("My cat is "), h("strong", "very"), t(" grumpy")
		).toString();
		//<p class=\"editor-note\">My cat is <strong>very</strong> grumpy</p>
		assertThat(html, is("<p class=\"editor&#45;note\">My&#32;cat&#32;is&#32;<strong>very</strong>&#32;grumpy</p>"));
	}

	@Test
	public void example4() {
		final String html = h(
				"img",
				Java9Map.of("src", "images/firefox-icon.png", "alt", "My test image")
		).toString();
		assertThat(html, is("<img src=\"images&#x2F;firefox&#45;icon&#46;png\" alt=\"My&#32;test&#32;image\"/>"));
	}
}