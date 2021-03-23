package com.dcm.crowd.service.impl;

import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.Admin;
import com.dcm.crowd.entity.AdminExample;
import com.dcm.crowd.exception.LoginFailedException;
import com.dcm.crowd.exception.MySqlException;
import com.dcm.crowd.exception.MySqlException1;
import com.dcm.crowd.exception.MySqlException2;
import com.dcm.crowd.mapper.AdminMapper;
import com.dcm.crowd.service.api.AdminService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.accessibility.AccessibleRelation;
import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;
    @Override
    public int saveAdmin(Admin admin) throws Exception {
        int i=0;
        try{
            i=adminMapper.insert(admin);
        }catch (Exception e){
            if(e instanceof DuplicateKeyException)
                throw new MySqlException1(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }



       return  i;
//        throw new RuntimeException("lalalal");

    }

    @Override
    public List<Admin> getAll() {
        return adminMapper.selectByExample(new AdminExample());
    }

    @Override
    public Admin getAdminByloginAct(String loginAcct, String userPswd) {

        AdminExample adminExample=new AdminExample();


        AdminExample.Criteria criteria = adminExample.createCriteria();
        criteria.andLoginAcctEqualTo(loginAcct);

        List<Admin> admins=adminMapper.selectByExample(adminExample);
        if(admins==null||admins.size()==0){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        if(admins.size()>1){
            throw new  RuntimeException(CrowdConstant.MESSAGE_SYSTEM_LOGIN_NOT_UNIQUE);
        }
        Admin admin=admins.get(0);
        if(admin==null){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        String userPswd1 = admin.getUserPswd();
        if(!Objects.equals(userPswd,userPswd1)){
            throw new LoginFailedException(CrowdConstant.MESSAGE_LOGIN_FAILED);
        }
        return admin;
    }


    @Override
    public PageInfo<Admin> getPageInfo(String keyword, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);

        List<Admin> admins = adminMapper.selectByKeyword(keyword);



        return new PageInfo<>(admins);
    }

    @Override
    public void remove(Integer aid) {
        adminMapper.deleteByPrimaryKey(aid);
    }

    @Override
    public Admin getAdminById(Integer aid) {
       Admin admin= adminMapper.selectByPrimaryKey(aid);
        return admin;
    }

    @Override
    public void updateAdmin(Admin admin) {

        try{
            adminMapper.updateByPrimaryKeySelective(admin);
        }catch(Exception e){
            if(e instanceof DuplicateKeyException)
                throw new MySqlException2(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
        }
    }

    @Override
    public void saveAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {

        adminMapper.deleteOLdRelationship(adminId);
// 2.根据 roleIdList 和 adminId 保存新的关联关系
        if(roleIdList != null && roleIdList.size() > 0) {
            adminMapper.insertNewRelationship(adminId, roleIdList);
        }
    }

    @Override
    public Admin getAdminByLoginAcct(String loginAcct) {
        AdminExample adminExample = new AdminExample();
        adminExample.createCriteria().andLoginAcctEqualTo(loginAcct);
        return adminMapper.selectByExample(adminExample).get(0);
    }


}
