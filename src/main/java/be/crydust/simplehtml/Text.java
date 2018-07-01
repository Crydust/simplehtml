package be.crydust.simplehtml;

import java.util.Collections;
import java.util.Iterator;

import static be.crydust.simplehtml.HtmlUtil.encode;

final class Text implements Html {
    private final String text;

    Text(final String text) {
        this.text = text == null ? "" : text;
    }

    @Override
    public String toString() {
        return "Text{}";
    }

    @Override
    public void appendTo(StringBuilder sb) {
        appendStartTo(sb);
    }

    @Override
    public void appendStartTo(final StringBuilder sb) {
        sb.append(encode(text));
    }

    @Override
    public void appendEndTo(final StringBuilder sb) {
        // NOOP
    }

    @Override
    public Iterator<Html> iterator() {
        return Collections.emptyIterator();
    }
}
