package rpc;

import java.util.concurrent.ConcurrentHashMap;

public class MyDispatcher {

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    public void register(String key, Object obj) {
        invokeMap.put(key, obj);
    }

    public Object get(String key) {
        return invokeMap.get(key);
    }
}
