package com.zhang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/29
 */
class Clerk{
    private int num=0;

    private Lock lock=new ReentrantLock();
    Condition condition=lock.newCondition();
    public  void get(){

        lock.lock();
        try {
            while (num >= 1){
                System.out.println("商店产品已满");
                try {
                   condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName()+": "+ ++num);
           condition.signalAll();
        } finally {

            lock.unlock();
        }


    }
    public  void consu(){

        lock.lock();
        try {
            while (num <= 0){

                System.out.println("商店商品缺货中");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+": "+ --num);
           condition.signalAll();
        } finally {
            lock.unlock();

        }

    }


}
class P1 implements Runnable{
 Clerk clerk;

    public P1(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
            clerk.get();

        }
    }
}

class C1 implements Runnable{

    Clerk clerk;

    public C1(Clerk clerk) {
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            clerk.consu();

        }
    }
}
public class TestConsumerAndProduct {

    public static void main(String[] args) {
        Clerk clerk = new Clerk();

        P1 p1 = new P1(clerk);
        C1 c1 = new C1(clerk);

        new Thread(p1,"线程A").start();
        new Thread(c1,"线程B").start();
        new Thread(p1,"线程C").start();
        new Thread(c1,"线程D").start();
        //增加为多线程访问，购买和出售商品不在循环中 会出现虚假唤醒


    }
}
