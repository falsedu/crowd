package com.dcm.crowd.mvc.handler;


import com.dcm.crowd.entity.Auth;
import com.dcm.crowd.entity.Role;
import com.dcm.crowd.service.api.AdminService;
import com.dcm.crowd.service.api.AuthService;
import com.dcm.crowd.service.api.RoleService;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
public class AssignHandler {
    @Autowired
    private RoleService roleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthService authService;

    @RequestMapping("/assign/to/assign/role/page.html")
    public String toAssignRolePage(
            @RequestParam("adminId")Integer adminId,
            ModelMap modelMap
    ){
        List<Role> assignedRoleList=roleService.getAssignedRole(adminId);

        List<Role> unAssignedRoleList=roleService.getUnAssignedRole(adminId);

        modelMap.put("assignedRoleList",assignedRoleList);
        modelMap.put("unAssignedRoleList",unAssignedRoleList);
        return "assign-role";
    }

    @RequestMapping("/assign/do/role/assign.html")
    public String saveAdminRoleRelationship(
            @RequestParam("adminId") Integer adminId,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("keyword") String keyword,
            @RequestParam(value="roleIdList", required=false) List<Integer> roleIdList
    ) {
        adminService.saveAdminRoleRelationship(adminId, roleIdList);
        return "redirect:/admin/get/page.html?pageNum="+pageNum+"&keyword="+keyword;
    }


    @ResponseBody
    @RequestMapping("/assgin/get/all/auth.json")
    public ResultEntity<List<Auth>> getAllAuth() {

        List<Auth> authList = authService.getAll();

        return ResultEntity.successWithData(authList);
    }



    @ResponseBody
    @RequestMapping("/assign/get/assigned/auth/id/by/role/id.json")
    public ResultEntity<List<Integer>> getAssignedAuthIdByRoleId(Integer roleId){

        List<Integer> authIdList=authService.getAssignedAuthIdByRoleId(roleId);


        return ResultEntity.successWithData(authIdList);

    }

    @ResponseBody
    @RequestMapping("/assign/do/role/assign/auth.json")
    public ResultEntity<Integer> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        System.out.println(map.get("roleId"));
        System.out.println(map.get("authIdArray"));
        authService.saveRoleAuthRelationship(map);

        return ResultEntity.successWithoutData();
    }
}
