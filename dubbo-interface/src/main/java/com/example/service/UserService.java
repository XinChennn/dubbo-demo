package com.example.service;

import org.apache.dubbo.common.stream.StreamObserver;

/**
 * Created by XinChen on 2022-09-07
 *
 * @Description: TODO
 */
public interface UserService {
    String sayHello();

    // Server Stream
    default void sayHelloStream(String name, StreamObserver<String> response){
    }

    // Client Stream
    default StreamObserver<String> sayHelloStream(StreamObserver<String> response){
        return response;
    }
}
