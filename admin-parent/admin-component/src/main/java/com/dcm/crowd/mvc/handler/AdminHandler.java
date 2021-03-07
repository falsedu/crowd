package com.dcm.crowd.mvc.handler;


import com.dcm.crowd.CrowdUtil;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.exception.LoginFailedException;
import com.dcm.crowd.service.api.AdminService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class AdminHandler {

    @Autowired
    private AdminService adminService;

    @RequestMapping("admin/do/login.html")
    public String login(@RequestParam("loginAcct") String loginAcct, @RequestParam("userPswd") String userPswd, HttpSession session){
        if(userPswd==null&&userPswd.length()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_STRING_IVALIDATE);
        }
        userPswd= CrowdUtil.md5(userPswd);
//        System.out.println(userPswd);
        Admin admin=adminService.getAdminByloginAct(loginAcct,userPswd);

        session.setAttribute(CrowdConstant.ATTR_NAME_LOGIN_ADMIN,admin);

        return "redirect:/admin/to/main/page.html";
    }
    @RequestMapping("admin/do/logout.html")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/admin/to/login/page.html";
    }

    @RequestMapping("admin/get/page.html")
    public String getPageInfo(
            @RequestParam(value = "keyword",defaultValue = "") String keyword,
            @RequestParam(value = "pageNo",defaultValue = "1") Integer pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
            ModelMap modelMap

    ){
        PageInfo<Admin> pageInfo = adminService.getPageInfo(keyword, pageNo, pageSize);
        modelMap.addAttribute(CrowdConstant.ATTR_NAME_PAGE_INFO,pageInfo);


        return "admin-page";

    }

    @RequestMapping("admin/remove/{aid}/{pageNo}/{keyword}.html")
    public String remove(@PathVariable("aid") Integer aid,@PathVariable("pageNo") Integer pageNo,
                         @PathVariable(value = "keyword") String keyword,
                         ModelMap modelMap){
        adminService.remove(aid);

//        return "admin-page";
        return "redirect:/admin/get/page.html?pageNo="+pageNo+"&keyword="+keyword;
    }
    @RequestMapping("admin/save.html")
    public String save(Admin admin) throws Exception {
        admin.setUserPswd(CrowdUtil.md5(admin.getUserPswd()));
        admin.setCreateTime(CrowdUtil.getSysDate());
        int i=adminService.saveAdmin(admin);

        return "redirect:/admin/get/page.html?pageNo="+Integer.MAX_VALUE;

    }
    @RequestMapping("/admin/to/edit/page.html")
    public String toEditPage(@RequestParam("aid") Integer aid,ModelMap modelMap){
      Admin admin=  adminService.getAdminById(aid);

      modelMap.addAttribute("a",admin);
      return "admin-edit";

    }
    @RequestMapping("/admin/do/update.html")
    public String update(Admin admin,@RequestParam("pageNo") Integer pageNo,@RequestParam("keyword") String keyword){
        adminService.updateAdmin(admin);

        return "redirect:/admin/get/page.html?pageNo="+pageNo+"&keyword="+keyword;
    }



}
