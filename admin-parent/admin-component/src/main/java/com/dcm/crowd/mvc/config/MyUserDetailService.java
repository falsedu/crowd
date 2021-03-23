package com.dcm.crowd.mvc.config;

import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.entity.Auth;
import com.dcm.crowd.entity.Role;
import com.dcm.crowd.service.api.AdminService;
import com.dcm.crowd.service.api.AuthService;
import com.dcm.crowd.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private AdminService adminService;
   @Autowired
   private AuthService authService;
   @Autowired
   private RoleService roleService;






    @Override
    public UserDetails loadUserByUsername(String loginAcct) throws UsernameNotFoundException {

       Admin admin = adminService.getAdminByLoginAcct(loginAcct);
       Integer adminId=admin.getId();
       List<Role> assignedRole = roleService.getAssignedRole(adminId);
       List<String> assignedAuthNameByAdminId = authService.getAssignedAuthNameByAdminId(adminId);

       List<GrantedAuthority> authorities=new ArrayList<>();
       for(Role r:assignedRole){
          String roleName="ROLE_"+r.getName();
           SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(roleName);
           authorities.add(simpleGrantedAuthority);
       }
       for(String a:assignedAuthNameByAdminId){
           SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(a);
           authorities.add(simpleGrantedAuthority);
       }


       return new SecurityAdmin(admin,authorities);
    }
}
