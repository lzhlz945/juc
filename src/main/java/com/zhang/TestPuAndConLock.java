package com.zhang;


import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/29
 *
 * 消费者和生产这模式
 *
 * 当生产者和消费者，的个数为一个时，就不能有else
 *
 * 虚假唤醒，在多个线程同时访问生产者和消费者时，如果线程不在循环中会出现虚假唤醒。
 *
 *修改成lock锁
 * lock的唤醒机制是 使用Condition 工具实现的
 *
 *
 */
public class TestPuAndConLock {
    private static int num=0;
    private Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();
    public   void get(){
        lock.lock();
        try {
            while (num>=1){
                System.out.println("产品已满！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.println(Thread.currentThread().getName()+":"+ ++num);
            condition.signalAll();
        } finally {

            lock.unlock();
        }


    }
    public   void consumer(){
        lock.lock();
        try {
            while (num<=0){
                System.out.println("产品不足！");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(Thread.currentThread().getName()+":"+ --num);
            condition.signalAll();
        } finally {

            lock.unlock();
        }

    }

    public static void main(String[] args) {
        TestPuAndConLock testPuAndCon = new TestPuAndConLock();
        Product1 p = new Product1(testPuAndCon);
        Consumer1 c = new Consumer1(testPuAndCon);
        Thread t1 = new Thread(p);
        t1.start();
        Thread t2 = new Thread(c);
        t2.start();
        Thread t3 = new Thread(p);
        t3.start();
        Thread t4 = new Thread(c);
        t4.start();
    }
}

class Product1 implements Runnable{
    TestPuAndConLock testPuAndCon;

    public Product1(TestPuAndConLock testPuAndCon) {
        this.testPuAndCon = testPuAndCon;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            testPuAndCon.get();

        }
    }
}

class Consumer1 implements Runnable{
    TestPuAndConLock testPuAndCon;

    public Consumer1(TestPuAndConLock testPuAndCon) {
        this.testPuAndCon = testPuAndCon;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

            testPuAndCon.consumer();

        }
    }
}
