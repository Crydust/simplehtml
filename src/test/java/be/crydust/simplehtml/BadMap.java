package be.crydust.simplehtml;

import java.util.AbstractMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

class BadMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    private final Set<Entry<K, V>> entries = new LinkedHashSet<>();

    @Override
    public V put(K key, V value) {
        final Entry<K, V> e = new BadMapEntry<>(key, value);
        entries.add(e);
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return entries;
    }

    private static class BadMapEntry<K, V> implements Map.Entry<K, V> {

        private final K key;
        private final V value;

        private BadMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }
    }
}
