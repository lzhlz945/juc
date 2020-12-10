package com.zhang.study;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author: create by zhl
 * @version: v1.0
 * @description: com.zhang.study
 * @date:2020/12/10
 */
public class PoolTst {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService pools=Executors.newFixedThreadPool(5);
        List<Future<Integer>> list=new ArrayList<>();
        for (int i=0;i<10;i++)
        {
            Future<Integer> future = pools.submit(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {

                    Thread.sleep(500);
                    int random = (int) (Math.random() * 10);
                    System.out.println(Thread.currentThread().getName());
                    return random;
                }

            });
            list.add(future);
        }
        for (Future<Integer> futureTask : list) {
            System.out.println(futureTask.get());
        }
        pools.shutdown();

    }
}

