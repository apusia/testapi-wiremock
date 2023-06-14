package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

class MapCollector implements Collector<String, Map<String, String>, Map<String, String>> {

    private String key;

    /**
     * @return An empty map to add keys with values to.
     */
    @Override
    public Supplier<Map<String, String>> supplier() {
        return HashMap::new;
    }

    /**
     * @return Accumulator to add key and value to the result map.
     */
    @Override
    public BiConsumer<Map<String, String>, String> accumulator() {
        return (map, value) -> {
            if (key != null) {
                map.put(key, value);
                key = null;
            } else {
                key = value;
            }
        };
    }

    /**
     * @return Combine two result maps into a single map.
     */
    @Override
    public BinaryOperator<Map<String, String>> combiner() {
        return (map1, map2) -> {
            map1.putAll(map2);
            return map1;
        };
    }

    /**
     * @return Use identity function to return result.
     */
    @Override
    public Function<Map<String, String>, Map<String, String>> finisher() {
        return Function.identity();
    }

    /**
     * @return Collector characteristic to indicate finisher method is identity function.
     */
    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(Characteristics.IDENTITY_FINISH);
    }
}
