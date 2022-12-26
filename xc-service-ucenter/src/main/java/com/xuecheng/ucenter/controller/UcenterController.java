package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @version 1.0
 **/
@RestController
public class UcenterController implements UcenterControllerApi {
    @Autowired
    UserService userService;

    @Override
    @GetMapping("/getuserext")
    public ResponseResult getUserext(@RequestParam("username") String username) {
        return ResponseResult.SUCCESS(userService.getUserExt(username));
    }

    @Override
    @GetMapping("/getUserById")
    public ResponseResult getUserById(@RequestParam("uid") String userId) {
        return ResponseResult.SUCCESS(userService.getUserById(userId));
    }
}
