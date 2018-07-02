package be.crydust.simplehtml;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static be.crydust.simplehtml.Html.h;
import static be.crydust.simplehtml.Html.t;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void example1() {
        final String html = h("div", Java9Map.of("id", "foo", "name", "bar"), "Hello!")
                .getOuterHTML();
        assertThat(html, is("<div id=\"foo\" name=\"bar\">Hello!</div>"));
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
        ).getOuterHTML();
        assertThat(html, is("<div id=\"foo\"><p>Look, a simple JSX DOM renderer!</p><ul><li><p>hello</p></li><li><p>there</p></li><li><p>people</p></li></ul></div>"));
    }

    @Test
    public void example3() {
        final String html = h(
                "p",
                Java9Map.of("class", "editor-note"),
                t("My cat is "), h("strong", "very"), t(" grumpy")
        ).getOuterHTML();
        assertThat(html, is("<p class=\"editor-note\">My cat is <strong>very</strong> grumpy</p>"));
    }

    @Test
    public void example4() {
        final String html = h(
                "img",
                Java9Map.of("src", "images/firefox-icon.png", "alt", "My test image")
        ).getOuterHTML();
        assertThat(html, is("<img alt=\"My test image\" src=\"images/firefox-icon.png\"/>"));
    }

    @Test
    public void should_handle_fragments() {
        final String html = Html.h(
                h("a"),
                h("b")
        ).getOuterHTML();
        assertThat(html, is("<a></a><b></b>"));
    }

    @Test
    public void text_should_replace_html() {
        assertThat(t("abc").getOuterHTML(), is("abc"));
        assertThat(t("abc&").getOuterHTML(), is("abc&amp;"));
        assertThat(t("&abc").getOuterHTML(), is("&amp;abc"));
        assertThat(t("a&bc").getOuterHTML(), is("a&amp;bc"));
        assertThat(t("&a&b&c&").getOuterHTML(), is("&amp;a&amp;b&amp;c&amp;"));
        assertThat(t("&<>\"'`").getOuterHTML(), is("&amp;&lt;&gt;&quot;&#x27;&#x60;"));
    }

    @Test
    public void should_nest_elements_correctly() {
        final String html = h("root",
                h("x-1",
                        h("x-11"), h("x-12"), h("x-13")
                ),
                h("x-2",
                        h("x-21"), h("x-22"), h("x-23")
                ),
                h("x-3",
                        h("x-31"), h("x-32"), h("x-33")
                )
        ).getOuterHTML();
        assertThat(html, is("<root><x-1><x-11></x-11><x-12></x-12><x-13></x-13></x-1><x-2><x-21></x-21><x-22></x-22><x-23></x-23></x-2><x-3><x-31></x-31><x-32></x-32><x-33></x-33></x-3></root>"));
    }

    @Test
    public void should_handle_deep_nesting() {
        Html div = h("div");
        for (int i = 0; i < 10_000; i++) {
            div = h("div", div);
        }
        div.getOuterHTML();
    }

    @Test
    public void should_throw_for_too_deep_nesting() {
        Html div = h("div");
        for (int i = 0; i < 20_000; i++) {
            div = h("div", div);
        }
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("Sorry, html is nested too deeply. MAX_DEPTH = 13526");
        div.getOuterHTML();
    }

    @Test
    public void should_handle_wide_nesting() {
        final List<Html> divs = new ArrayList<>();
        for (int i = 0; i < 10_000; i++) {
            divs.add(h("div"));
        }
        final Html root = h("root", divs);
        root.getOuterHTML();
    }

    @Test
    public void should_throw_for_invalid_tag_name() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Element name '<plaintext>' is not valid");
        h("<plaintext>");
    }

    @Test
    public void should_throw_for_invalid_attribute_name() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Attribute name '<plaintext>' is not valid");
        h("div", Java9Map.of("<plaintext>", ""));
    }

}