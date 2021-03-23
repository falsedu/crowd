package com.dcm.crowd.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class PortalHandler {
    @RequestMapping("/")
    public String showPortalPage(){


        //实际开发中加载的数据......

        return "portal";
    }

}
