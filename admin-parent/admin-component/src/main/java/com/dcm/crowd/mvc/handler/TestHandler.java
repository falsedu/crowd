package com.dcm.crowd.mvc.handler;


import com.dcm.crowd.CrowdUtil;
import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.service.api.AdminService;
import com.dcm.crowd.test.Student;
import com.dcm.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;
    Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/ssm.html")
    public String testSSM(ModelMap modelMap){
        List<Admin> adminList=adminService.getAll();

        modelMap.addAttribute("adminList",adminList);

        return "target";
    }
    @RequestMapping("/test/ssm1.html")
    public String testSSM1(ModelMap modelMap) throws Exception {
        Admin admin = new Admin();
        admin.setLoginAcct("dcm");
        admin.setUserName("大馋猫");
        admin.setUserPswd("1234");
        admin.setEmail("dcm@qq.com");
        int x=adminService.saveAdmin(admin);

        modelMap.addAttribute("adminList",x);

        return "target";
    }
    @ResponseBody
    @RequestMapping("/send/array1.html")
    public String testReceiveArray1(@RequestParam("array[]") List<Integer> array){

        for (Integer i :
                array) {
            System.out.println("number="+i);
        }

        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/array2.html")
    public String testReceiveArray2(@RequestParam("array[]") Integer[] array1){

        for (Integer i :
                array1) {
            System.out.println("number="+i);
        }

        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/array3.html")
    public String testReceiveArray3(@RequestBody List<Integer> array){


        for (Integer i :
                array) {
            logger.info("number3="+i);

        }

        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/complexSubject.html")
    public String testComplex(@RequestBody Student student){

        logger.info(student.toString());

        return "success";
    }
    @ResponseBody
    @RequestMapping("/send/complexSubject2.json")
    public ResultEntity<Student> testComplex2(@RequestBody Student student,HttpServletRequest request){

//        logger.info(student.toString());
        if(CrowdUtil.judgeRequestType(request)){
            System.out.println("这是一个ajax请求");
        }
        else{
            System.out.println("这不是一个ajax请求");
        }

        return ResultEntity.successWithData(student);
    }

    @RequestMapping("/send/error.html")
    public String ifError() throws Exception{


        int i=12/0;

        return "target";
    }
    @RequestMapping("/send/success.html")
    public String ifSuccess(HttpServletRequest request) throws Exception{
        if(CrowdUtil.judgeRequestType(request)){
            System.out.println("这是一个ajax请求");
        }
        else{
            System.out.println("这不是一个ajax请求");
        }

        int i=12/1;

        return "target";
    }

    @RequestMapping("/send/useAnno.html")
    public String useAnnotationException() throws NullPointerException{

        String s = null;
        try{
            System.out.println(s.equals("88"));
        }catch(NullPointerException e){
            throw new NullPointerException("空指针异常");
        }


        return "target";
    }






}
