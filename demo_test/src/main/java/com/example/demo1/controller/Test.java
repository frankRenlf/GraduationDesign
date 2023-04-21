package com.example.demo1.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author : Frank.Ren
 * @version : 1.0
 * @Project : demo1
 * @Package : com.example.demo1.controller
 * @createTime : 2022/10/29 17:38
 * @Email : sc19lr@leeds.ac.uk
 * @github : https://github.com/frankRenlf
 * @Description :
 */
@RestController
@RequestMapping("/test")
public class Test {

    @RequestMapping("/t1")
    public String demo1(){
        return "hello";
    }

}
