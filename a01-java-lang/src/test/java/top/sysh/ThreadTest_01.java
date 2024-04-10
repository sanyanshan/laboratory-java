package top.sysh;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author 董虎杰
 * @Date 2024/4/10
 */
public class ThreadTest_01 {

    /**
     * 证明：
     * 锁重入 N 次后需要释放 N 次，才能完全解锁
     */
    @Test
    public void test_43() throws Exception{
        ReentrantLock lock = new ReentrantLock();
        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(()->{
            lock.lock();
            lock.lock();
            System.out.println("线程1，证明lock可重入");
            countDownLatch.countDown();
            lock.unlock();
//            lock.unlock();
        }).start();

        new Thread(()->{
            // 保证，上面的线程已加锁
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
                return;
            }
            lock.lock();
            System.out.println("线程2，lock重入后需要 unlock 1 次即可释放锁"); // 不会执行
        }).start();

        Thread.currentThread().join();
    }


}
