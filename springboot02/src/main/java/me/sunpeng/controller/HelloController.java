package me.sunpeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author sp
 * @date 2021-10-25 10:38
 */

@RestController
public class HelloController {

    @GetMapping("/test")
    public String hello() throws InterruptedException {
        final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10,
                30,
                120,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        for (int i = 0; i < 10; i++) {
            final Future<?> submit = threadPoolExecutor.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "******" + "执行");
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        threadPoolExecutor.shutdown();
        threadPoolExecutor.awaitTermination(2,TimeUnit.SECONDS);
        System.out.println(Thread.currentThread().getName() + "******" + "执行");
        return "hello";

    }


}
