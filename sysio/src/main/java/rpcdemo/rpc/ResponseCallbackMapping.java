package rpcdemo.rpc;


import rpcdemo.rpc.protocol.Package;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class ResponseCallbackMapping {

    private static ConcurrentHashMap<Long, CompletableFuture> mapping = new ConcurrentHashMap<>();

    public static void addCallback(long requestId, CompletableFuture cf) {
        mapping.putIfAbsent(requestId, cf);
    }

    public static void runCallback(Package pkg) {
        long requestId = pkg.getHeader().getRequestId();
        CompletableFuture cf = mapping.get(requestId);
        if (null != cf) {
            cf.complete(pkg.getContent().getResult());
            removeCallback(requestId);
        }
    }

    public static void removeCallback(long requestId) {
        mapping.remove(requestId);
    }
}
