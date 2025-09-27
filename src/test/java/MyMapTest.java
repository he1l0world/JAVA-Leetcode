import org.junit.jupiter.api.Test;
import org.leetcode.jdk.MyMap;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class MyMapTest {
    @Test
    void putGet_basic() {
        MyMap<String,Integer> m = new MyMap<>();
        assertNull(m.put("a", 1));               // new insert returns null
        assertEquals(1, m.size());
        assertEquals(1, m.get("a"));
        assertNull(m.get("missing"));
    }

    @Test
    void put_overwriteReturnsOldValue() {
        MyMap<String,Integer> m = new MyMap<>();
        assertNull(m.put("a", 1));
        assertEquals(1, m.put("a", 2));          // overwrite → returns old
        assertEquals(2, m.get("a"));
        assertEquals(1, m.size());               // size unchanged on overwrite
    }

    @Test
    void remove_basicAndMissing() {
        MyMap<String,Integer> m = new MyMap<>();
        m.put("a", 1); m.put("b", 2);
        assertEquals(2, m.size());
        assertEquals(1, m.remove("a"));
        assertNull(m.get("a"));
        assertEquals(1, m.size());
        assertNull(m.remove("nope"));            // removing absent → null
        assertEquals(1, m.size());
    }

    @Test
    void nullKey_supported() {
        MyMap<String,Integer> m = new MyMap<>();
        assertNull(m.put(null, 42));
        assertEquals(42, m.get(null));
        assertEquals(42, m.remove(null));
        assertNull(m.get(null));
    }

    @Test
    void collisions_sameBucketDifferentKeys() {
        MyMap<KeyWithForcedHash,String> m = new MyMap<>();
        KeyWithForcedHash k1 = new KeyWithForcedHash("x", 7);
        KeyWithForcedHash k2 = new KeyWithForcedHash("y", 7); // same hash → same bucket
        KeyWithForcedHash k3 = new KeyWithForcedHash("z", 7);

        m.put(k1, "X");
        m.put(k2, "Y");
        m.put(k3, "Z");

        assertEquals("X", m.get(k1));
        assertEquals("Y", m.get(k2));
        assertEquals("Z", m.get(k3));

        // remove middle node in chain
        assertEquals("Y", m.remove(k2));
        assertNull(m.get(k2));
        assertEquals("X", m.get(k1));
        assertEquals("Z", m.get(k3));
    }

    @Test
    void iterator_visitsAllEntries_noDuplicates() {
        MyMap<String,Integer> m = new MyMap<>();
        Map<String,Integer> expected = new HashMap<>();
        for (int i = 0; i < 50; i++) {
            String k = "k" + i;
            expected.put(k, i);
            m.put(k, i);
        }
        // collect through iterator
        Map<String,Integer> seen = new HashMap<>();
        for (MyMap.Entry<String,Integer> e : m) {
            assertNull(seen.put(e.key, e.value), "duplicate key in iteration");
        }
        assertEquals(expected.size(), seen.size());
        for (var kv : expected.entrySet()) {
            assertEquals(kv.getValue(), seen.get(kv.getKey()));
        }
    }

    @Test
    void resize_keepsAllMappings() {
        // NOTE: This assumes your MyMap implements resize + threshold.
        MyMap<Integer,Integer> m = new MyMap<>();
        int n = 10_000;
        for (int i = 0; i < n; i++) m.put(i, -i);
        for (int i = 0; i < n; i++) assertEquals(-i, m.get(i));
        // remove half and recheck
        for (int i = 0; i < n; i += 2) assertEquals(-i, m.remove(i));
        for (int i = 1; i < n; i += 2) assertEquals(-i, m.get(i));
        for (int i = 0; i < n; i += 2) assertNull(m.get(i));
    }

    // --- helper: keys that collide on demand ---
    static final class KeyWithForcedHash {
        final String id;
        final int forcedHash;
        KeyWithForcedHash(String id, int forcedHash) { this.id = id; this.forcedHash = forcedHash; }
        @Override public int hashCode() { return forcedHash; }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof KeyWithForcedHash)) return false;
            return Objects.equals(id, ((KeyWithForcedHash) o).id);
        }
        @Override public String toString() { return id; }
    }

}
