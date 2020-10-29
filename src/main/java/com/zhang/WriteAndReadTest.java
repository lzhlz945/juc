package com.zhang;


import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/29
 */

class RAWDeme{
    private int num=0;

    private ReadWriteLock lock=new ReentrantReadWriteLock();

    public int getNum() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            return num;
        } finally {
            lock.readLock().unlock();
        }
    }

    public void setNum(int num) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.num = num;
        } finally {
            lock.writeLock().unlock();
        }
    }
}

public class WriteAndReadTest {
    public static void main(String[] args) {
        RAWDeme rawDeme = new RAWDeme();

        new Thread(() -> rawDeme.setNum(1),"write start:").start();

        for (int i = 0; i < 100; i++)
            new Thread(() -> {
                int num = rawDeme.getNum();
                System.out.println(num);
            }).start();

    }
}
