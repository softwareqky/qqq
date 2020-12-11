package project.edge.domain.business;

import java.util.concurrent.ConcurrentHashMap;

/**
 * HashMap的简单包装类，用作临时缓存。
 *
 */
public class SimpleCache {

    private static SimpleCache instance = null;

    private SimpleCache() {}

    public synchronized static SimpleCache instance() {
        if (instance == null) {
            instance = new SimpleCache();
        }
        return instance;
    }

    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();

    public void put(String key, Object value) {
        this.map.put(key, value);
    }

    public boolean containsKey(String key) {
        return this.map.containsKey(key);
    }

    public void remove(String key) {
        this.map.remove(key);
    }

    public Object get(String key) {
        return this.map.get(key);
    }
}
