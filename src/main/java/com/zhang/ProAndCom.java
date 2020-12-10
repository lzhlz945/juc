package com.zhang;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/12/10
 */
public class ProAndCom {
    public static void main(String[] args) {
        ProAndCom proAndCom = new ProAndCom();
        Producter producter = new Producter(proAndCom);
        Consummer consummer = new Consummer(proAndCom);
        Thread t1 = new Thread(producter, "生产者");
        Thread t3 = new Thread(consummer, "消费者");
        Thread t2 = new Thread(producter, "生产者");
        Thread t4 = new Thread(consummer, "消费者");
        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
    private int num=0;
    private Lock lock=new ReentrantLock();
    private Condition cn = lock.newCondition();
    public void get(){
            lock.lock();
        try {
            while (num>=1){
                System.out.println("商店产品已满");
                try {
                    cn.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+ ++num);
            cn.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void summer(){
        lock.lock();
        try {
            while (num<=0){
                System.out.println("商店商品缺货中");
                try {
                    cn.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            System.out.println(Thread.currentThread().getName()+ --num);
            cn.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }


    }


}
class Producter implements Runnable{
  private ProAndCom proAndCom;

    Producter(ProAndCom proAndCom) {
        this.proAndCom = proAndCom;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
               proAndCom.get();
        }
    }
}
class Consummer implements Runnable{
  private ProAndCom proAndCom;

    Consummer(ProAndCom proAndCom) {
        this.proAndCom = proAndCom;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++) {
               proAndCom.summer();
        }
    }
}
