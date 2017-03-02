package com.switch513.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by switch on 17/2/22.
 */
@Controller
public class TestController {

    @RequestMapping("/test1")
    @ResponseBody
    public String test1() {
        return "test1";
    }

    @RequestMapping("/")
    public String index() {
        return "index";
    }

}
