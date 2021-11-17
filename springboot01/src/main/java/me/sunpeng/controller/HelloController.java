package me.sunpeng.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author sp
 * @date 2021-10-25 10:38
 */

@RestController
@Api(tags = "hello 欢迎界面")
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/test")
    @ApiOperation(value = "测试", notes = "测试a")
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

    @GetMapping("/testGetParams")
    @ApiOperation(value = "测试get请求参数", notes = "测试a")
    public ResponseEntity testGetParams(@RequestParam(name = "name")String name){
        return new ResponseEntity(name, HttpStatus.OK);
    }

}
