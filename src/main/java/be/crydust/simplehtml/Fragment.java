package be.crydust.simplehtml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.util.Collections.emptyList;
import static java.util.Collections.unmodifiableList;

final class Fragment implements Html {

    private final List<Html> children;

    Fragment(final List<? extends Html> children) {
        this.children = children == null
                ? emptyList()
                : unmodifiableList(new ArrayList<>(children));
    }

    @Override
    public String toString() {
        return "Fragment{}";
    }

    @Override
    public Iterator<Html> iterator() {
        return this.children.iterator();
    }
}
