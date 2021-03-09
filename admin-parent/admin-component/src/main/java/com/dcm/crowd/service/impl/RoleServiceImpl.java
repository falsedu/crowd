package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.Role;
import com.dcm.crowd.entity.RoleExample;
import com.dcm.crowd.exception.RoleNAmeException;
import com.dcm.crowd.mapper.RoleMapper;
import com.dcm.crowd.service.api.RoleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getAllByKeyword(Integer pageNo,Integer pageSize,String keyword){
        PageHelper.startPage(pageNo,pageSize);


        List<Role> roles = roleMapper.getAllByKeyword(keyword);
        return new PageInfo<>(roles);
    }

    @Override
    public Integer addRole(Role role) {
        int i=0;
        try{
            i=roleMapper.insert(role);
        }catch (Exception e){
            throw new RoleNAmeException("角色名重复");
        }


        return i;
    }

    @Override
    public int updateRole(Role role) {
        int i=0;
        try{
            i=roleMapper.updateByPrimaryKeySelective(role);
        }catch (Exception e){
            throw new RoleNAmeException("角色名重复");
        }


        return i;
    }

    @Override
    public void removeRole(List<Integer> ids) {
        RoleExample roleExample=new RoleExample();
        RoleExample.Criteria criteria = roleExample.createCriteria();
        criteria.andIdIn(ids);
        try{
            roleMapper.deleteByExample(roleExample);
        }catch (Exception e){
            throw new RoleNAmeException("删除失败");
        }

    }


}
