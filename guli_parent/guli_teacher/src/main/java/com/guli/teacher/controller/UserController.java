package com.guli.teacher.controller;

import com.guli.common.result.Result;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Tang on 2020/9/25
 */
@RestController
@RequestMapping("user")
@CrossOrigin
public class UserController {

    @PostMapping("login")
    public Result login(){
        return Result.ok().data("token","admin");
    }

    @GetMapping("info")
    public Result info(){
        return Result.ok()
                .data("roles","[\"admin\"]")
                .data("name","admin")
                .data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
