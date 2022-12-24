package com.xuecheng.ucenter.controller;

import com.xuecheng.api.ucenter.UcenterControllerApi;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public XcUserExt getUserext(@RequestAttribute String userId, @RequestParam("username") String username) {
        System.out.println(userId+ "...................");
        return userService.getUserExt(username);
    }

    @Override
    @GetMapping("/getUserById")
    public XcUserExt getUserById(@RequestParam("uid") String userId) {
        return userService.getUserById(userId);
    }
}
