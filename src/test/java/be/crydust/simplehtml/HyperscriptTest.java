package be.crydust.simplehtml;

import org.junit.Ignore;
import org.junit.Test;

import static be.crydust.simplehtml.Html.h;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HyperscriptTest {
    @Test
    public void simple() {
        assertThat(h("h1").getOuterHTML(), is("<h1></h1>"));
        assertThat(h("h1", "hello world").getOuterHTML(), is("<h1>hello world</h1>"));
    }

    @Test
    public void nested() {
        assertThat(h("div",
                h("h1", "Title"),
                h("p", "Paragraph")
        ).getOuterHTML(), is("<div><h1>Title</h1><p>Paragraph</p></div>"));
    }

    @Test
    public void arrays_for_nesting_is_ok() {
        assertThat(h("div",
                asList(h("h1", "Title"), h("p", "Paragraph"))
        ).getOuterHTML(), is("<div><h1>Title</h1><p>Paragraph</p></div>"));
    }

    @Test
    public void can_use_namespace_in_name() {
        assertThat(h("myns:mytag").getOuterHTML(), is("<myns:mytag></myns:mytag>"));
    }

    @Test
    public void can_use_id_selector() {
        assertThat(h("div#frame").getOuterHTML(), is("<div id=\"frame\"></div>"));
    }

    @Test
    public void can_use_class_selector() {
        assertThat(h("div.panel").getOuterHTML(), is("<div class=\"panel\"></div>"));
    }

    @Test
    public void can_default_element_types() {
        assertThat(h(".panel").getOuterHTML(), is("<div class=\"panel\"></div>"));
        assertThat(h("#frame").getOuterHTML(), is("<div id=\"frame\"></div>"));
    }

    @Test
    public void can_set_properties() {
        Html a = h("a", Java9Map.of("href", "http://google.com/"));
        assertThat(a.getAttribute("href").orElse(null), is("http://google.com/"));
        Html checkbox = h("input", Java9Map.of("name", "yes", "type", "checkbox"));
        assertThat(checkbox.getOuterHTML(), is("<input name=\"yes\" type=\"checkbox\"/>"));
    }

    @Test
    @Ignore("Not implemented")
    public void registers_event_handlers() {
    }

    @Test
    @Ignore("Not implemented")
    public void sets_styles() {
        // TODO add special syntax for style?
        //var div = h("div", {style: {"color": "red"}})
        //assertThat(div.style.color, "red")
    }

    @Test
    public void sets_styles_as_text() {
        // TODO add getter for style?
        Html div = h("div", Java9Map.of("style", "color: red"));
        //assertThat(div.style.color, is("red"));
        assertThat(div.getOuterHTML(), is("<div style=\"color: red\"></div>"));
    }

    @Test
    public void sets_data_attributes() {
        Html div = h("div", Java9Map.of("data-value", "5"));
        assertThat(div.getAttribute("data-value").orElse(null), is("5"));
    }

    @Test
    @Ignore("Not implemented")
    public void boolean_number_date_regex_get_to_string_ed() {
//    var e = h("p", true, false, 4, new Date("Mon Jan 15 2001"), /hello/)
//    t.assert(e.outerHTML.match(/<p>truefalse4Mon Jan 15.+2001.*\/hello\/<\/p>/))
    }

    @Test
    @Ignore("Not implemented")
    public void observable_content() {
        // TODO add support for observables
//        var title = o()
//        title("Welcome to HyperScript!")
//        var h1 = h("h1", title)
//        assertThat(h1.getOuterHTML(), is("<h1>Welcome to HyperScript!</h1>")
//        title("Leave, creep!")
//        assertThat(h1.getOuterHTML(), is("<h1>Leave, creep!</h1>")
    }

    @Test
    @Ignore("Not implemented")
    public void observable_property() {
        // TODO add support for observables
//        var checked = o()
//        checked(true)
//        var checkbox = h("input", {type: "checkbox", checked: checked})
//        assertThat(checkbox.checked, true)
//        checked(false)
//        assertThat(checkbox.checked, false)
    }

    @Test
    @Ignore("Not implemented")
    public void observable_style() {
//        var color = o()
//        color("red")
//        var div = h("div", {style: {"color": color}})
//        assertThat(div.style.color, "red")
//        color("blue")
//        assertThat(div.style.color, "blue")
    }

    @Test
    @Ignore("Not implemented")
    public void context_basic() {
//        var _h = h.context()
//        var p = _h("p", "hello")
//        assertThat(p.getOuterHTML(), is("<p>hello</p>")
//        _h.cleanup()
    }

    @Test
    @Ignore("Not implemented")
    public void context_cleanup_removes_observable_listeners() {
//        var _h = h.context()
//        var text = o()
//        text("hello")
//        var color = o()
//        color("red")
//        var className = o()
//        className("para")
//        var p = _h("p", {style: {color: color}, className: className}, text)
//        assertThat(p.getOuterHTML(), is("<p style=\"color: red; \" class=\"para\">hello</p>")
//        _h.cleanup()
//        color("blue")
//        text("world")
//        className("section")
//        assertThat(p.getOuterHTML(), is("<p style=\"color: red; \" class=\"para\">hello</p>")
    }

    @Test
    @Ignore("Not implemented")
    public void context_cleanup_removes_event_handlers() {
//        var _h = h.context()
//        var onClick = spy()
//        var button = _h("button", "Click me!", {onclick: onClick})
//        _h.cleanup()
//        simu.click(button)
//        t.assert(!onClick.called, "click listener was not triggered")
    }

    @Test
    @Ignore("Not implemented")
    public void unicode_selectors() {
        assertThat(h(".⛄").getOuterHTML(), is("<div class=\"⛄\"></div>"));
        assertThat(h("span#⛄").getOuterHTML(), is("<span id=\"⛄\"></span>"));
    }
}