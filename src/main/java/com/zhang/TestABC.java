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
class Aa{
    private int count=1;

    Lock lock=new ReentrantLock();
    Condition condition1=lock.newCondition();
    Condition condition2=lock.newCondition();
    Condition condition3=lock.newCondition();
    public void loopA(int c){
        lock.lock();
        try {
            if(count != 1){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+"\t"+"\t"+"第"+c+"轮");
            }
            condition2.signal();
            count=2;
        }finally {
            lock.unlock();
        }
    }
    public void loopB(int c){
        lock.lock();
        try {
            if(count != 2){
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+"\t"+"\t"+"第"+c+"轮");
            }
            condition3.signal();
            count=3;
        }finally {
            lock.unlock();
        }
    }
    public void loopC(int c){
        lock.lock();
        try {
            if(count != 3){
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+"\t"+"\t"+"第"+c+"轮");
            }
            condition1.signal();
            count=1;
        }finally {
            lock.unlock();
        }
    }
}





public class TestABC {
    public static void main(String[] args) {
        Aa a = new Aa();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    a.loopA(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    a.loopB(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    a.loopC(i);
                }
                System.out.println("--------------------------------");
            }
        },"C").start();


    }
}
