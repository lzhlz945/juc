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


class A{
    private int count=1;
    Lock lock=new ReentrantLock();
    Condition condition1=lock.newCondition();
    Condition condition2=lock.newCondition();
    Condition condition3=lock.newCondition();

    public void get1(int count1){
        lock.lock();
        try{
            if(count != 1){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+count1);

            }
            count=2;
            condition2.signal();

        }finally {
            lock.unlock();
        }

    }
    public void get2(int count1){
        lock.lock();
        try{
            if(count != 2){
                try {
                    condition2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+count1);

            }
            count=3;
            condition3.signal();

        }finally {
            lock.unlock();
        }

    }
    public void get3(int count1){
        lock.lock();
        try{
            if(count != 3){
                try {
                    condition3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"\t"+count1);

            }
            count=1;
            condition1.signal();

        }finally {
            lock.unlock();
        }

    }

}
public class TestABC01 {
    public static void main(String[] args) {
        A a=new A();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    a.get1(i);
                }
            }
        },"A").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    a.get2(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    a.get3(i);
                }
            }
        },"C").start();



    }
}
