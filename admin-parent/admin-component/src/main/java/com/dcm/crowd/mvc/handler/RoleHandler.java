package com.dcm.crowd.mvc.handler;


import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.entity.Auth;
import com.dcm.crowd.entity.Role;
import com.dcm.crowd.service.api.RoleService;
import com.dcm.crowd.util.ResultEntity;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleHandler {
    @Autowired
    private RoleService roleService;



    @PreAuthorize("hasRole('部长')")
    @RequestMapping("/role/get/page/info.json")
    @ResponseBody
    public ResultEntity<PageInfo<Role>> getAll(@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                               @RequestParam(value = "pageSize",defaultValue = "5") Integer pageSize,
                                               @RequestParam(value = "keyword",defaultValue = "")String keyword){


            PageInfo<Role> allByKeyword = roleService.getAllByKeyword(pageNo, pageSize, keyword);

            return ResultEntity.successWithData(allByKeyword);



    }

    @RequestMapping("/role/add.json")
    @ResponseBody
    public ResultEntity<Integer> addRole(Role role){
       Integer i= roleService.addRole(role);
       return ResultEntity.successWithoutData();
    }

    @RequestMapping("/role/update.json")
    @ResponseBody
    public ResultEntity<Integer> updateRole(Role role){
        roleService.updateRole(role);
        return ResultEntity.successWithoutData();
    }
    @RequestMapping("/role/remove.json")
    @ResponseBody
    public ResultEntity<String> reomveRole(@RequestBody List<Integer> ids){
        roleService.removeRole(ids);
        return ResultEntity.successWithoutData();
    }


}
