package com.lik;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {

    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.out.println(countDownLatch.getCount());

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + countDownLatch.getCount());
            countDownLatch.countDown();
        }, "t1").start();

        System.out.println(Thread.currentThread().getName() + " " + countDownLatch.getCount());

        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " " + countDownLatch.getCount());
    }
}
