package be.crydust.simplehtml;

import java.util.Collections;
import java.util.Iterator;

final class Raw implements Html {
    private final String text;

    Raw(final String text) {
        this.text = text == null ? "" : text;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    @Override
    public void appendTo(StringBuilder sb) {
        appendStartTo(sb);
    }

    @Override
    public void appendStartTo(final StringBuilder sb) {
        sb.append(text);
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
