import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RPermitExpirableSemaphore;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

public class LearnRedisson {
    public static void main(String[] args) throws InterruptedException {

        RedissonClient redissonClient = Redisson.create();
        RLock lock = redissonClient.getFairLock("aaa");
        lock.lock();
        lock.tryLockAsync();
        RSemaphore semaphore = redissonClient.getSemaphore("semaphore");
        semaphore.acquire(1);

        RPermitExpirableSemaphore expirableSemaphore = redissonClient.getPermitExpirableSemaphore("expirableSemaphore");
        expirableSemaphore.acquire(2, TimeUnit.SECONDS);
    }
}
