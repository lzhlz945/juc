package com.zhang;



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
 *
 */
public class TestPuAndCon {
    private static int num=0;
    public synchronized  void get(){
        while (num>=1){
            System.out.println("产品已满！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

            System.out.println(Thread.currentThread().getName()+":"+ ++num);
            this.notifyAll();



    }
    public synchronized  void consumer(){
        while (num<=0){
            System.out.println("产品不足！");
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

            System.out.println(Thread.currentThread().getName()+":"+ --num);
            this.notifyAll();

    }

    public static void main(String[] args) {
        TestPuAndCon testPuAndCon = new TestPuAndCon();
        Product p = new Product(testPuAndCon);
        Consumer c = new Consumer(testPuAndCon);
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
class Product implements Runnable{
    TestPuAndCon testPuAndCon;

    public Product(TestPuAndCon testPuAndCon) {
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
class Consumer implements Runnable{
    TestPuAndCon testPuAndCon;

    public Consumer(TestPuAndCon testPuAndCon) {
        this.testPuAndCon = testPuAndCon;
    }

    @Override
    public void run() {

        for (int i = 0; i < 10; i++) {

            testPuAndCon.consumer();

        }
    }
}
