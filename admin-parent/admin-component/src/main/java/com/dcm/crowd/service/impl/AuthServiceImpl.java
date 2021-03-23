package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.Auth;
import com.dcm.crowd.entity.AuthExample;
import com.dcm.crowd.mapper.AuthMapper;
import com.dcm.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private AuthMapper authMapper;
    @Override
    public List<Auth> getAll() {
        AuthExample authExample=new AuthExample();
        return authMapper.selectByExample(authExample);

    }

    @Override
    public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {

        return authMapper.getAssignedAuthIdByRoleId(roleId);
    }

    @Override
    public void saveRoleAuthRelationship(Map<String, List<Integer>> map) {

        List<Integer> roleIdList=map.get("roleId");
        Integer roleId=roleIdList.get(0);
        authMapper.deleteOldRelationship(roleId);
        List<Integer> authIdList=map.get("authIdArray");
        if(authIdList!=null&&authIdList.size()>0){
            authMapper.insertNewRelationship(roleId,authIdList);
        }
    }

    @Override
    public List<String> getAssignedAuthNameByAdminId(Integer adminId) {

        return authMapper.getAssignedAuthNameByAdminId(adminId);


    }
}
