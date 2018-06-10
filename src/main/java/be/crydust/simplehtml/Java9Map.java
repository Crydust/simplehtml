package be.crydust.simplehtml;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

/**
 * Helper class which should be replaced by java 9 Map.
 */
public interface Java9Map {

	static <K, V> Map<K, V> of() {
		return Collections.emptyMap();
	}

	static <K, V> Map<K, V> of(final K k1, final V v1) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5, final K k6, final V v6) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k6, "k6 is null"), requireNonNull(v6, "v6 is null")))) {
			throw new IllegalArgumentException("k6 '" + k6 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5, final K k6, final V v6, final K k7, final V v7) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k6, "k6 is null"), requireNonNull(v6, "v6 is null")))) {
			throw new IllegalArgumentException("k6 '" + k6 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k7, "k7 is null"), requireNonNull(v7, "v7 is null")))) {
			throw new IllegalArgumentException("k7 '" + k7 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5, final K k6, final V v6, final K k7, final V v7, final K k8, final V v8) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k6, "k6 is null"), requireNonNull(v6, "v6 is null")))) {
			throw new IllegalArgumentException("k6 '" + k6 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k7, "k7 is null"), requireNonNull(v7, "v7 is null")))) {
			throw new IllegalArgumentException("k7 '" + k7 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k8, "k8 is null"), requireNonNull(v8, "v8 is null")))) {
			throw new IllegalArgumentException("k8 '" + k8 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5, final K k6, final V v6, final K k7, final V v7, final K k8, final V v8, final K k9, final V v9) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k6, "k6 is null"), requireNonNull(v6, "v6 is null")))) {
			throw new IllegalArgumentException("k6 '" + k6 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k7, "k7 is null"), requireNonNull(v7, "v7 is null")))) {
			throw new IllegalArgumentException("k7 '" + k7 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k8, "k8 is null"), requireNonNull(v8, "v8 is null")))) {
			throw new IllegalArgumentException("k8 '" + k8 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k9, "k9 is null"), requireNonNull(v9, "v9 is null")))) {
			throw new IllegalArgumentException("k9 '" + k9 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}

	static <K, V> Map<K, V> of(final K k1, final V v1, final K k2, final V v2, final K k3, final V v3, final K k4, final V v4, final K k5, final V v5, final K k6, final V v6, final K k7, final V v7, final K k8, final V v8, final K k9, final V v9, final K k10, final V v10) {
		final Map<K, V> map = new HashMap<>();
		map.put(requireNonNull(k1, "k1 is null"), requireNonNull(v1, "v1 is null"));
		if (nonNull(map.put(requireNonNull(k2, "k2 is null"), requireNonNull(v2, "v2 is null")))) {
			throw new IllegalArgumentException("k2 '" + k2 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k3, "k3 is null"), requireNonNull(v3, "v3 is null")))) {
			throw new IllegalArgumentException("k3 '" + k3 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k4, "k4 is null"), requireNonNull(v4, "v4 is null")))) {
			throw new IllegalArgumentException("k4 '" + k4 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k5, "k5 is null"), requireNonNull(v5, "v5 is null")))) {
			throw new IllegalArgumentException("k5 '" + k5 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k6, "k6 is null"), requireNonNull(v6, "v6 is null")))) {
			throw new IllegalArgumentException("k6 '" + k6 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k7, "k7 is null"), requireNonNull(v7, "v7 is null")))) {
			throw new IllegalArgumentException("k7 '" + k7 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k8, "k8 is null"), requireNonNull(v8, "v8 is null")))) {
			throw new IllegalArgumentException("k8 '" + k8 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k9, "k9 is null"), requireNonNull(v9, "v9 is null")))) {
			throw new IllegalArgumentException("k9 '" + k9 + "' is a duplicate key");
		}
		if (nonNull(map.put(requireNonNull(k10, "k10 is null"), requireNonNull(v10, "v10 is null")))) {
			throw new IllegalArgumentException("k10 '" + k10 + "' is a duplicate key");
		}
		return Collections.unmodifiableMap(map);
	}
}
