package be.crydust.simplehtml;

import java.util.Iterator;

class HtmlAndIterator {
    final Html html;
    final Iterator<Html> iterator;

    HtmlAndIterator(final Html html) {
        this.html = html;
        this.iterator = html.iterator();
    }
}
