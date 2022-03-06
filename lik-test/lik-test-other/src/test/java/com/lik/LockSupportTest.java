package com.lik;

import java.util.concurrent.locks.LockSupport;

public class LockSupportTest {

    public static void main(String[] args) {
        Object o = new Object();
        Thread main = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + " 11111111 ");

        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(main.isAlive() + " 2 " + main.getState() + main.isInterrupted());
            System.out.println(Thread.currentThread().getName() + " aaaaaaaa ");
            LockSupport.unpark(main);
            LockSupport.unpark(main);
        }, "t1").start();

        System.out.println(Thread.currentThread().getName() + " 22222222 ");

        System.out.println(main.isAlive() + " 1 " + main.getState() + main.isInterrupted());

        LockSupport.park(main);

        System.out.println(Thread.currentThread().getName() + " 33333333 ");

    }
}
