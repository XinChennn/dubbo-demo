package org.example.controller;

import com.example.service.UserService;
import org.apache.dubbo.common.stream.StreamObserver;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by XinChen on 2022-09-07
 *
 * @Description: TODO
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @DubboReference()
    private UserService userService;

    @RequestMapping("/say")
    public String sayHello() {
        return userService.sayHello();
    }

    @RequestMapping("/server")
    public String server() {
        userService.sayHelloStream("hello", new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接受到结果" + data);
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("接受到错误:" + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("---结束---");
            }
        });

        return "ok";
    }

    @RequestMapping("/client")
    public String client() {
        StreamObserver<String> serverStreamObserver = userService.sayHelloStream(new StreamObserver<String>() {
            @Override
            public void onNext(String data) {
                System.out.println("接收到" + data);
            }

            @Override
            public void onError(Throwable throwable) {
            }

            @Override
            public void onCompleted() {
                System.out.println("---结束---");
            }
        });

        serverStreamObserver.onNext("1");
        serverStreamObserver.onNext("2");
        serverStreamObserver.onCompleted();
        return "ok";
    }
}
