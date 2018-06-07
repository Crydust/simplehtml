package be.crydust.simplehtml;

import static be.crydust.simplehtml.Html.h;
import static be.crydust.simplehtml.Html.t;
import static be.crydust.simplehtml.HtmlElement.encode;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class HtmlTest {

	@Test
	public void example1() {
		final String html = h("div", Java9Map.of("id", "foo", "name", "bar"), "Hello!")
				.toString();
		assertThat(html, is("<div name=\"bar\" id=\"foo\">Hello!</div>"));
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
		assertThat(html, is("<div id=\"foo\"><p>Look, a simple JSX DOM renderer!</p><ul><li><p>hello</p></li><li><p>there</p></li><li><p>people</p></li></ul></div>"));
	}

	@Test
	public void example3() {
		final String html = h(
				"p",
				Java9Map.of("class", "editor-note"),
				t("My cat is "), h("strong", "very"), t(" grumpy")
		).toString();
		assertThat(html, is("<p class=\"editor-note\">My cat is <strong>very</strong> grumpy</p>"));
	}

	@Test
	public void example4() {
		final String html = h(
				"img",
				Java9Map.of("src", "images/firefox-icon.png", "alt", "My test image")
		).toString();
		assertThat(html, is("<img src=\"images/firefox-icon.png\" alt=\"My test image\"/>"));
	}

	@Test
	public void encode_should_replace_html() {
		assertThat(encode("abc"), is("abc"));
		assertThat(encode("abc&"), is("abc&amp;"));
		assertThat(encode("&abc"), is("&amp;abc"));
		assertThat(encode("a&bc"), is("a&amp;bc"));
		assertThat(encode("&a&b&c&"), is("&amp;a&amp;b&amp;c&amp;"));
		assertThat(encode("&<>\"'`"), is("&amp;&lt;&gt;&quot;&#x27;&#x60;"));
	}
}