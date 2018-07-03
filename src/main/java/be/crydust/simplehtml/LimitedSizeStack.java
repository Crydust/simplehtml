package be.crydust.simplehtml;

import java.util.ArrayDeque;
import java.util.Deque;

class LimitedSizeStack<E> {
    private final int maxSize;
    private final Deque<E> stack = new ArrayDeque<>();

    LimitedSizeStack(int maxSize) {
        this.maxSize = maxSize;
    }

    E peek() {
        return stack.peek();
    }

    void push(E htmlAndIterator) {
        if (stack.size() >= maxSize) {
            throw new IllegalStateException("Stack size exceeds " + maxSize);
        }
        stack.push(htmlAndIterator);
    }

    E pop() {
        return stack.pop();
    }

    boolean isEmpty() {
        return stack.isEmpty();
    }
}
