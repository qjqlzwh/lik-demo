package com.lik;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println(Thread.currentThread().getName() + " 组长: 明天组团去旅游");
        });
        System.out.println("组长: 大家一会开个小会");
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " 准备");
                    int await = cyclicBarrier.await();
                    System.out.println(Thread.currentThread().getName() + " OK");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "t" + i).start();
        }
    }
}
