package com.lik;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreTest {

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " 抢到桌位，开始吃饭");
                    TimeUnit.SECONDS.sleep(new Random().nextInt(5));
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
//                    while (true) {}
                    System.out.println(Thread.currentThread().getName() + " 吃完饭，离开桌位");
                    semaphore.release();
                }
            }, "t" + i).start();
        }
    }
}
