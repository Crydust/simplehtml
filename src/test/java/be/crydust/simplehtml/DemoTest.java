package be.crydust.simplehtml;

import org.junit.Ignore;
import org.junit.Test;

import java.util.Map;

import static be.crydust.simplehtml.Html.h;
import static be.crydust.simplehtml.Html.text;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @see <a href="http://hyperhype.github.io/hyperscript/">Interactive Demo</a>
 */
public class DemoTest {

    /**
     * Create HyperText with Java.
     */
    @Test
    public void example() {
        String html =
                h("div#page",
                        h("div#header",
                                h("h1.classy", Java9Map.of("style", "background-color: #22f;"), "h")),
                        h("div#menu", Java9Map.of("style", "background-color: #2f2;"),
                                h("ul",
                                        h("li", "one"),
                                        h("li", "two"),
                                        h("li", "three"))),
                        h("h2", Java9Map.of("style", "background-color: #f22;"), "content title"),
                        h("p",
                                text("so it's just like a templating engine,\n"),
                                text("but easy to use inline with java\n")),
                        h("p",
                                text("the intention is for this to be used to create\n"),
                                text("reusable, interactive html widgets.")))
                        .getOuterHTML();
        String expectedHtml = "" +
                "<div id=\"page\">" +
                "<div id=\"header\">" +
                "<h1 class=\"classy\" style=\"background-color: #22f;\">h</h1>" +
                "</div>" +
                "<div id=\"menu\" style=\"background-color: #2f2;\">" +
                "<ul>" +
                "<li>one</li>" +
                "<li>two</li>" +
                "<li>three</li>" +
                "</ul>" +
                "</div>" +
                "<h2 style=\"background-color: #f22;\">content title</h2>" +
                "<p>so it&#x27;s just like a templating engine,\n" +
                "but easy to use inline with java\n" +
                "</p>" +
                "<p>the intention is for this to be used to create\n" +
                "reusable, interactive html widgets.</p>" +
                "</div>";
        assertThat(html, is(expectedHtml));
    }

    /**
     * If the tag name is of form name.class1.class2#id that is a short cut for setting the class and id.
     */
    @Test
    public void classes_and_id() {
        assertThat(h("name.class1.class2#id").getOuterHTML(),
                is("<name class=\"class1 class2\" id=\"id\"></name>"));
    }

    /**
     * If a Map&lt;String, String&gt; is passed in, it's values will be used to set attributes.
     */
    @Test
    public void attributes() {
        assertThat(h("a", Java9Map.of("href", "https://npm.im/hyperscript"), "hyperscript").getOuterHTML(),
                is("<a href=\"https://npm.im/hyperscript\">hyperscript</a>"));

    }

    @Test
    @Ignore("Not implemented")
    public void events() {
    }

    /**
     * Style is just another attribute, there is no special notation for it.
     * There is no support for passing multiple Maps with attributes.
     */
    @Test
    public void styles() {
        assertThat(h("h1.fun", Java9Map.of("style", "font-family: 'Comic Sans MS';"), "Happy Birthday!").getOuterHTML(),
                is("<h1 class=\"fun\" style=\"font-family: &#x27;Comic Sans MS&#x27;;\">Happy Birthday!</h1>"));
    }

    /**
     * If an argument is a string, a TextNode is created in that position.
     * <p>
     * Java being type safe means we can't just mix String and Html objects in a varargs.
     * The {@link be.crydust.simplehtml.Html#text(String) text} method helps with that.
     */
    @Test
    public void children_string() {
        assertThat(h("p", "string").getOuterHTML(), is("<p>string</p>"));
        assertThat(h("p", text("first"), text("second")).getOuterHTML(), is("<p>firstsecond</p>"));
    }

    /**
     * If an argument is the return value of a call to h that's cool too.
     * <p>
     * We don't support Node nor HTMLElement objects.
     */
    @Test
    public void children_HtmlElement() {
        assertThat(h("p", text("first"), h("i", "second"), text("third")).getOuterHTML(),
                is("<p>first<i>second</i>third</p>"));
    }

    /**
     * Null values are just ignored.
     */
    @Test
    public void children_null() {
        assertThat(h("p", text("first"), null, text("third"), text(null)).getOuterHTML(),
                is("<p>firstthird</p>"));
    }

    /**
     * Each item in the List is treated like a ordinary child.
     * This is useful when you want to iterate over an object.
     * Wrapping the List&lt;Html&gt; in a call to h(List&lt;Html&gt;) returns an HtmlFragment.
     */
    @Test
    public void children_array() {
        Map<String, String> obj = Java9Map.of(
                "a", "Apple",
                "b", "Banana",
                "c", "Cherry",
                "d", "Durian",
                "e", "Elder Berry"
        );
        String html =
                h("table",
                        h("tr", h("th", "letter"), h("th", "fruit")),
                        obj.entrySet().stream()
                                .map(it -> h("tr",
                                        h("th", it.getKey()),
                                        h("td", it.getValue())))
                                .collect(collectingAndThen(toList(), Html::h))
                ).getOuterHTML();
        String expectedHtml = "" +
                "<table>" +
                "<tr><th>letter</th><th>fruit</th></tr>" +
                "<tr><th>a</th><td>Apple</td></tr>" +
                "<tr><th>b</th><td>Banana</td></tr>" +
                "<tr><th>c</th><td>Cherry</td></tr>" +
                "<tr><th>d</th><td>Durian</td></tr>" +
                "<tr><th>e</th><td>Elder Berry</td></tr>" +
                "</table>";
        assertThat(html, is(expectedHtml));
    }
}
