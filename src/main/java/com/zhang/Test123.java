package com.zhang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/11/2
 */

class A123{
    private int num=1;
     Lock lock=new ReentrantLock();
    Condition c1=lock.newCondition();
    Condition c2=lock.newCondition();
    Condition c3=lock.newCondition();

    public void loop1(int count){
        lock.lock();

        try {
            if(num!=1){
                try {
                    c1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"第"+count+"轮");

            }
            num=2;
            c2.signal();
        } finally {

            lock.unlock();
        }
    }
    public void loop2(int count){
        lock.lock();
        try {
            if(num!=2){
                try {
                    c2.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"第"+count+"轮");

            }
            num=3;
            c3.signal();
        } finally {

            lock.unlock();
        }
    }
    public void loop3(int count){
        lock.lock();
        try {
            if(num!=3){
                try {
                    c3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getName()+"第"+count+"轮");

            }
            num=1;
            c1.signal();
        } finally {

       lock.unlock();
        }
    }


}

public class Test123 {
    public static void main(String[] args) {
        A123 a123 = new A123();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    a123.loop1(i);
                }
            }
        },"A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    a123.loop2(i);
                }
            }
        },"B").start();

        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 10; i++) {
                    a123.loop3(i);
                }
            }
        },"C").start();


    }

}
