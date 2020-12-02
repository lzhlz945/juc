package com.zhang;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/28
 */
public class TestLock {
    public static void main(String[] args) {
        MyLock myLock = new MyLock();
        Thread thread1 = new Thread(myLock,"窗口一");
        Thread thread2 = new Thread(myLock,"窗口二");
        Thread thread3 = new Thread(myLock,"窗口三");
        thread1.start();
        thread2.start();
        thread3.start();
    }
}
class MyLock implements Runnable{

    private static int number=100;
    private Lock lock=new ReentrantLock();
    @Override
    public void run() {

        try {
            lock.lock();
            while (true){

                if(number>0){

                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"该窗口的余票为:"+--number);
            }
                }
        } finally {

            lock.unlock();
        }
    }
}
