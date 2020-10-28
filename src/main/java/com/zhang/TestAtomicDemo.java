package com.zhang;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/28
 */
public class TestAtomicDemo {
    public static void main(String[] args) {
        MyRunnable2 runnable2=new MyRunnable2();
        MyRunnable3 runnable3=new MyRunnable3();
        for (int i = 0; i < 11; i++) {
            new Thread(runnable3).start();
        }
    }


}
/*
原子变量：jdk1.5后java.util.concurrent.atomic包下提供了常用的原子变量
   1、volatile保证内存可见性
   2、CAS(compare-And-swap)算法保证数据的原子性
      CAS算法是硬件对于并发操作的支持
         c - 原值
         a - 预估值
         s - 更新值  当c==a时，c=s CAS算法比同步锁的优点，线程不需要阻塞了



 */


//MyRunnable2 不采用Atomic时出现了脏数据
class MyRunnable2 implements Runnable{

    private int number=0;
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getNumber());
    }

    public int getNumber() {
        return number++;
    }
}
class MyRunnable3 implements Runnable{

    private AtomicInteger number=new AtomicInteger();
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(getNumber());
    }

    public int getNumber() {
        return number.getAndIncrement();
    }
}