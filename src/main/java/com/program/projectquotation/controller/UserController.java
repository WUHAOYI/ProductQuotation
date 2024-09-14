package com.program.projectquotation.controller;

import com.program.projectquotation.pojo.User;
import com.program.projectquotation.result.Result;
import com.program.projectquotation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by WHY on 2024/9/14.
 * Functions:
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Result getUserInfo(){
        return userService.getUserInfo();
    }
}
