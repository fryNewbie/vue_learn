package com.example.demo.user.service;

import org.springframework.stereotype.Service;

/**
 * testServerImpl
 */
@Service
public class testServerImpl implements ItestServer {

    @Override
    public String hello() {
        return "hello world";
    }

}