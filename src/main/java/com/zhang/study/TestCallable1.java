package com.zhang.study;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.study
 * @date:2020/12/10
 */
public class TestCallable1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        MyCallable1 callable1 = new MyCallable1();
        FutureTask<Integer> futureTask=new FutureTask<>(callable1);
        new Thread(futureTask).start();
        Integer integer = futureTask.get();
        System.out.println(integer);
    }
}
class MyCallable1 implements Callable<Integer>{


    @Override
    public Integer call() throws Exception {
        int num=0;
        for (int i = 0; i < 10; i++) {
            num++;
        }
        return num;
    }
}