package be.crydust.simplehtml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

final class HtmlFragment implements Html {

    private final List<Html> children;

    HtmlFragment(final List<? extends Html> children) {
        this.children = children == null
                ? emptyList()
                : unmodifiableList(new ArrayList<>(children));
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
    public void appendStartTo(StringBuilder sb) {
        for (final Html child : this.children) {
            child.appendTo(sb);
        }
    }

    @Override
    public void appendEndTo(StringBuilder sb) {
        //NOOP
    }

    @Override
    public Iterator<Html> iterator() {
        return this.children.iterator();
    }
}
