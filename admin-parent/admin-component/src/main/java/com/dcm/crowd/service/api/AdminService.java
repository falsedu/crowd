package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface AdminService {

    int saveAdmin(Admin admin) throws Exception;

    List<Admin> getAll();

    Admin getAdminByloginAct(String loginAcct, String userPswd);

    PageInfo<Admin> getPageInfo(String keyword, Integer pageNo, Integer pageSize);

    void remove(Integer aid);

    Admin getAdminById(Integer aid);

    void updateAdmin(Admin admin);
}
