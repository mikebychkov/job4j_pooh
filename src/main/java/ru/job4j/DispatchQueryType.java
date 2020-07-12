package ru.job4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DispatchQueryType {

    private final Map<String, Function<String, Boolean>> dispatch = new HashMap<>();

    public DispatchQueryType() {
        init();
    }

    public void put(String key, Function<String, Boolean> f) {
        dispatch.put(key, f);
    }

    public void init() {
        put("topic", (s) -> true);
        put("queue", (s) -> false);
    }

    public boolean dispatch(String type) {
        Function<String, Boolean> f = dispatch.get(type);
        if (f != null) {
            return f.apply(type);
        }
        throw new IllegalStateException("Handle not defined for the type: " + type);
    }
}
