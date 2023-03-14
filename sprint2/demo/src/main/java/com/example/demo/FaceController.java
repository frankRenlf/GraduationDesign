package com.example.demo;


import cn.hutool.http.HttpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/face")
public class FaceController {



    @PostMapping("/koutu")
    public String koutu(@RequestBody  Map body){
        body.put("api_key","7sz5c2XbXG3Z1shhtZvb4zjvbYPEyq2-");
        body.put("api_secret","L2xsMQmNpW7lCGv0dVViaJda0iMdETWB");
        return HttpUtil.post("https://api-cn.faceplusplus.com/humanbodypp/v2/segment", body);
    }





}
