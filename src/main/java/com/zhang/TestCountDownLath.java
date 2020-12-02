package com.zhang;

import java.util.concurrent.CountDownLatch;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/28
 * 在完成某些运算时，当其他线程完成后，当前线程运算才执行
 *
 * CountDownLatch 在run方法运行完后必须让它减1 latch1.countDown();，然后在执行方法
 * 中没有执行完时，让这个闭锁进行等待 latch1.await();
 *
 */
public class TestCountDownLath {
private static CountDownLatch latch;

    public TestCountDownLath(CountDownLatch latch) {
        this.latch = latch;
    }

    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(5);
        long start = System.currentTimeMillis();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    synchronized (this){
                        try {
                            for (int i = 0; i < 50000; i++) {
                                if(i % 2==0){
                                    System.out.println(i);
                                }
                            }
                        } finally {
                            latch1.countDown();
                        }

                    }


                }
            }).start();

        }
        try {
            latch1.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end = System.currentTimeMillis();
        System.out.println("时间为："+(end-start));
    }
}

