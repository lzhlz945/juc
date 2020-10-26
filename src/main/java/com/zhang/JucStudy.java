package com.zhang;

import org.junit.jupiter.api.Test;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang
 * @date:2020/10/26
 */
public class JucStudy {
    public static void main(String[] args) {
        MyRunable myRunable=new MyRunable();
        new Thread(myRunable).start();
        while (true){

           if(myRunable.isFlag()) {
               System.out.println("-----------------");
               break;
           }
        }
    }

    @Test
    public void test01(){
        System.out.println("juc-初始化");
        System.out.println("新建bran1分支");
        System.out.println("测试分支添加是否成功");


    }

    /**
     * 内存可见性：当多个线程操作共享数据时，彼此不可见；
     *
     * volatile关键字：可以保证彼此共享数据是可见的，可以理解他们操作
     * 数据是在主存中完成的。
     * 是一种轻量级的同步策略，不具备互斥性，不能保证变量的原子性
     */




    @Test
    public void test02(){


    }
}
class MyRunable implements Runnable{

    private volatile boolean flag=false;

    public boolean isFlag() {

        return flag;
    }


    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag=true;
        System.out.println(isFlag());
    }
}








































