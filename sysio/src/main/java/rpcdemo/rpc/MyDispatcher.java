package rpcdemo.rpc;

import java.util.concurrent.ConcurrentHashMap;

public class MyDispatcher {

    public static ConcurrentHashMap<String, Object> invokeMap = new ConcurrentHashMap<>();

    private static MyDispatcher dispatcher = null;

    static {
        dispatcher = new MyDispatcher();
    }

    public static MyDispatcher getDispatcher() {
        return dispatcher;
    }

    public void register(String key, Object obj) {
        invokeMap.put(key, obj);
    }

    public Object get(String key) {
        return invokeMap.get(key);
    }
}
