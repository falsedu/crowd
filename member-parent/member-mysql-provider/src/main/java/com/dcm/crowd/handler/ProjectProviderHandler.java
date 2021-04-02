package com.dcm.crowd.handler;

import com.dcm.crowd.entity.vo.DetailProjectVO;
import com.dcm.crowd.entity.vo.OrderProjectVO;
import com.dcm.crowd.entity.vo.PortalTypeVO;
import com.dcm.crowd.entity.vo.ProjectVO;
import com.dcm.crowd.service.api.ProjectService;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectProviderHandler {


    @Autowired
    private ProjectService projectService;

    @RequestMapping("/save/project/vo/remote")
    ResultEntity<String> saveProjectVORemote(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId){
        try{
            projectService.saveProject(projectVO,memberId);
            return ResultEntity.successWithoutData();
        }
        catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }



    @RequestMapping("/get/portal/type/project/data/remote")

    public ResultEntity<List<PortalTypeVO>>getPortalTypeProjectDataRemote(){

        try{
            List<PortalTypeVO> portalTypeVO = projectService.getPortalTypeVO();
            return ResultEntity.successWithData(portalTypeVO);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }

    @RequestMapping("/get/project/detail/remote/{projectId}")
    public ResultEntity<DetailProjectVO> getDetailProjectVORemote(@PathVariable("projectId") Integer projectId){


        try{
            DetailProjectVO detailProjectVO = projectService.getDetailProjectVO(projectId);
            System.out.println(detailProjectVO);
            return ResultEntity.successWithData(detailProjectVO);
        }catch (Exception e){
            e.printStackTrace();

            return ResultEntity.failed(e.getMessage());
        }
    }








}
