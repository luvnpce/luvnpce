import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class LearnRedisson {
    public static void main(String[] args) {

        RedissonClient redissonClient = Redisson.create();
        RLock lock = redissonClient.getFairLock("aaa");
        lock.lock();
        lock.tryLockAsync();
    }
}
