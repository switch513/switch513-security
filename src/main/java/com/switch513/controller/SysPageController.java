package com.switch513.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author switch
 * @create 2017-02-24-上午9:35
 */
@Controller
public class SysPageController {

    @RequestMapping("/sys/{url}.html")
    public String page(@PathVariable("url") String url) {
        return "sys/" + url + ".html";
    }

}
