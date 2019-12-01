package com.example.demo.user.controller;

import javax.servlet.http.HttpServletRequest;

import com.example.demo.model.Result;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * loginController
 */
@RestController
@RequestMapping("/user")
@CrossOrigin
public class loginController {

    @PostMapping("/login")
    public Result account(HttpServletRequest res) {
        String username = res.getParameter("username");
        String password = res.getParameter("password");
        Map<String,Object> map=new HashMap<>();
        map.put("token",username);
        System.out.print(username);
        return Result.createSuccessResult().setCode(20000).setData(map);
    }

    @GetMapping("/info")
    public Result getInfo() {
        return Result.createSuccessResult().setData("123").setCode(20000);
    }

    @PostMapping("/logout")
    public Result logout() {
        return Result.createSuccessResult().setData("success").setCode(20000);
    }

}