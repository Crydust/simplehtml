package be.crydust.simplehtml;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static be.crydust.simplehtml.Html.f;
import static be.crydust.simplehtml.Html.h;
import static be.crydust.simplehtml.Html.r;
import static be.crydust.simplehtml.Html.t;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
    public void should_handle_fragments() {
        final String html = f(
                h("a"),
                h("b")
        ).toString();
        assertThat(html, is("<a></a><b></b>"));
    }

    @Test
    public void text_should_replace_html() {
        assertThat(t("abc").toString(), is("abc"));
        assertThat(t("abc&").toString(), is("abc&amp;"));
        assertThat(t("&abc").toString(), is("&amp;abc"));
        assertThat(t("a&bc").toString(), is("a&amp;bc"));
        assertThat(t("&a&b&c&").toString(), is("&amp;a&amp;b&amp;c&amp;"));
        assertThat(t("&<>\"'`").toString(), is("&amp;&lt;&gt;&quot;&#x27;&#x60;"));
    }

    @Test
    public void raw_should_not_replace_html() {
        assertThat(r("abc").toString(), is("abc"));
        assertThat(r("abc&").toString(), is("abc&"));
        assertThat(r("&abc").toString(), is("&abc"));
        assertThat(r("a&bc").toString(), is("a&bc"));
        assertThat(r("&a&b&c&").toString(), is("&a&b&c&"));
        assertThat(r("&<>\"'`").toString(), is("&<>\"'`"));
    }

    @Test
    public void should_nest_elements_correctly() {
        final String html = h("root",
                h("1",
                        h("11"), h("12"), h("13")
                ),
                h("2",
                        h("21"), h("22"), h("23")
                ),
                h("3",
                        h("31"), h("32"), h("33")
                )
        ).toString();
        assertThat(html, is("<root><1><11></11><12></12><13></13></1><2><21></21><22></22><23></23></2><3><31></31><32></32><33></33></3></root>"));
    }

    @Test
    public void should_handle_deep_nesting() {
        Html div = h("div");
        for (int i = 0; i < 10_000; i++) {
            div = h("div", div);
        }
        final String html = div.toString();
    }

    @Test
    public void should_throw_for_too_deep_nesting() {
        Html div = h("div");
        for (int i = 0; i < 20_000; i++) {
            div = h("div", div);
        }
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Sorry, html is nested too deeply. MAX_DEPTH = 13526");
        final String html = div.toString();
    }

    @Test
    public void should_handle_wide_nesting() {
        final List<Html> divs = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            divs.add(h("div"));
        }
        final Html root = h("root", divs);
        final String html = root.toString();
    }

}