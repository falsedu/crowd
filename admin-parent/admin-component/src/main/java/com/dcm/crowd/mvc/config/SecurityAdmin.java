package com.dcm.crowd.mvc.config;

import com.dcm.crowd.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;

/**
 * User 对象中仅包含账号和密码 ，为了获取到原始的Admin对象，专门创建这个类对User类进行扩展
 */

public class SecurityAdmin extends User {
    private static final long serialVersionUID=1L;

    private Admin originalAdmin;
    public SecurityAdmin (Admin originalAdmin, List<GrantedAuthority> authorities ){
        super(originalAdmin.getLoginAcct(),originalAdmin.getUserPswd(),authorities);
        this.originalAdmin=originalAdmin;

        originalAdmin.setUserPswd(null);


    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
