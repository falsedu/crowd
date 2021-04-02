package com.dcm.crowd.handler;

import com.dcm.crowd.api.MySQLRemoteService;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.vo.DetailProjectVO;
import com.dcm.crowd.entity.vo.PortalTypeVO;
import com.dcm.crowd.util.ResultEntity;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
public class PortalHandler {

    @Autowired
    private MySQLRemoteService mySQLRemoteService;
    @RequestMapping("/")
    public String showPortalPage(Model model){
        ResultEntity<List<PortalTypeVO>> portalTypeProjectDataRemote = mySQLRemoteService.getPortalTypeProjectDataRemote();
        String result=portalTypeProjectDataRemote.getResult();
        if(ResultEntity.SUCCESS.equals(result)){
            List<PortalTypeVO> data = portalTypeProjectDataRemote.getData();
            model.addAttribute(CrowdConstant.ATTR_NAME_PORTAL_DATA,data);
        }


        //实际开发中加载的数据......

        return "portal";
    }




}
