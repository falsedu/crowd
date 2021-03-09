package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    PageInfo<Role> getAllByKeyword(Integer pageNo,Integer pageSize,String keyword);

    Integer addRole(Role role);

    int updateRole(Role role);

    void removeRole(List<Integer> ids);
}
