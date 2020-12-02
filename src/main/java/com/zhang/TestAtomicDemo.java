package com.zhang;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
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

    @Test
    public void test01(){
        MyRunnable4 runnable4=new MyRunnable4();
        for (int i = 0; i < 10; i++) {
            new Thread(runnable4).start();

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

/**
 * concurrentMap:锁分段机制，分为16个段，每个段都有锁，从原来的串行到并行，
 * jdk1.8也是采用的cas算法，这样可以不阻塞和不上下文切换的解决线程安全问题
 *
 * CopyOnWriteArrayList 在遍历时在多线程下可以增加
 */
class MyRunnable4 implements Runnable{

//    private static List<String> list= Collections.synchronizedList(new ArrayList<>());
    private static List<String> list=new CopyOnWriteArrayList<>();
    static {
        list.add("aa");
        list.add("bb");
        list.add("cc");
    }
    @Override
    public void run() {
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            list.add("gg");
            System.out.println(next);
        }
    }


}