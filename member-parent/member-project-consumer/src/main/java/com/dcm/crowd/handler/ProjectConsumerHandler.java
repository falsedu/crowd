package com.dcm.crowd.handler;

import com.dcm.crowd.CrowdUtil;

import com.dcm.crowd.api.MySQLRemoteService;
import com.dcm.crowd.config.OSSProperties;
import com.dcm.crowd.constant.CrowdConstant;

import com.dcm.crowd.entity.vo.*;
import com.dcm.crowd.util.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectConsumerHandler {

    Logger logger= LoggerFactory.getLogger(ProjectConsumerHandler.class);


    @Autowired
    private OSSProperties ossProperties;

    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @RequestMapping("/create/project/information")
    public String saveProjectBasicInfo(
            ProjectVO projectVO,
            MultipartFile headerPicture,
            List<MultipartFile> detailPictureList,

            HttpSession session,
            ModelMap modelMap
    ) throws IOException {
        boolean headerPictureIsEmpty=headerPicture.isEmpty();

        if(headerPictureIsEmpty){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_HEADER_PIC_EMPTY);
            return "project-launch";
        }

        ResultEntity<String> uploadHeaderPicResultEntity = CrowdUtil.upLoadFileToOSS(
                ossProperties.getEndPoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(),
                headerPicture.getInputStream(),
                ossProperties.getBucketName(),
                ossProperties.getBucketDomain(),
                headerPicture.getOriginalFilename()
        );
        String result =uploadHeaderPicResultEntity.getResult();

        if(ResultEntity.SUCCESS.equals(result)){
            String headerPicturePath = uploadHeaderPicResultEntity.getData();

            projectVO.setHeaderPicturePath(headerPicturePath);
        }
        else{
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_HEADER_PIC_UPLOAD_FAILED);
            return "project-launch";
        }



        List<String> detailPicturePathList =new ArrayList<>();
        if(detailPictureList==null||detailPictureList.size()==0){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
            return "project-launch";
        }
        for(MultipartFile detailPicture:detailPictureList){
            if(detailPicture.isEmpty()){
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_DETAIL_PIC_EMPTY);
                return "project-launch";
            }
            ResultEntity<String> detailUploadResultEntity = CrowdUtil.upLoadFileToOSS(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(),
                    detailPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), detailPicture.getOriginalFilename());
            String detailUploadResult = detailUploadResultEntity.getResult();
            if(ResultEntity.SUCCESS.equals(detailUploadResult)) {
                String detailPicturePath = detailUploadResultEntity.getData();
// 8.收集刚刚上传的图片的访问路径
                detailPicturePathList.add(detailPicturePath);
            } else {
// 9.如果上传失败则返回到表单页面并显示错误消息
                modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,
                        CrowdConstant.MESSAGE_DETAIL_PIC_UPLOAD_FAILED);
                return "project-launch";
            }
        }

        projectVO.setDetailPicturePathList(detailPicturePathList);

        System.out.println(projectVO);

        session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLATE_PROJECT,projectVO);

        return "redirect:http://192.168.3.54/project/return/info/page";






    }

    @ResponseBody
    @RequestMapping("/create/upload/return/picture.json")
    public ResultEntity<String> uploadReturnPicture(

            @RequestParam("returnPicture") MultipartFile returnPicture
    ) throws IOException {

        ResultEntity<String> returnPicResultEntity = CrowdUtil.upLoadFileToOSS(ossProperties.getEndPoint(), ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret(), returnPicture.getInputStream(), ossProperties.getBucketName(), ossProperties.getBucketDomain(), returnPicture.getOriginalFilename());

        return returnPicResultEntity;


    }



    @ResponseBody
    @RequestMapping("/create/save/return.json")
    public ResultEntity<String> saveReturn (ReturnVO returnVO,HttpSession session){


        try{
            ProjectVO projectVO = (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLATE_PROJECT);
            if(projectVO==null){
                return ResultEntity.failed(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
            }
            List<ReturnVO> returnVOList=projectVO.getReturnVOList();


            if(returnVOList==null||returnVOList.size()==0){
                returnVOList=new ArrayList<>();

                projectVO.setReturnVOList(returnVOList);
            }

            returnVOList.add(returnVO);
            session.setAttribute(CrowdConstant.ATTR_NAME_TEMPLATE_PROJECT,projectVO);


            return ResultEntity.successWithoutData();

        }
        catch(Exception e){
            e.printStackTrace();
            return ResultEntity.failed(e.getMessage());
        }
    }


    @RequestMapping("/create/confirm")
    public String confirm(ModelMap modelMap,MemberConfirmInfoVO confirmInfoVO,HttpSession session){
        ProjectVO projectVO= (ProjectVO) session.getAttribute(CrowdConstant.ATTR_NAME_TEMPLATE_PROJECT);
        if(projectVO==null){
            throw new RuntimeException(CrowdConstant.MESSAGE_TEMPLE_PROJECT_MISSING);
        }

        projectVO.setMemberConfirmInfoVO(confirmInfoVO);
        MemberLoginVO  memberLoginVO= (MemberLoginVO) session.getAttribute(CrowdConstant.LOGIN_MEMBER);


        Integer memberId = memberLoginVO.getId();

       // logger.info(projectVO.toString());
        ResultEntity<String> saveResultEntity=mySQLRemoteService.saveProjectVORemote(projectVO,memberId);
        String result=saveResultEntity.getResult();
        if(ResultEntity.FAILED.equals(result)){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_PROJECT_SAVE_FAILED);
            return "project-confirm";

        }
        System.out.println(projectVO);
        session.removeAttribute(CrowdConstant.ATTR_NAME_TEMPLATE_PROJECT);
        return  "redirect:http://192.168.3.54/project/create/success";
    }

    @RequestMapping("/portal/show/project/detail/{projectId}")
    public String getDetailProject(@PathVariable("projectId") Integer projectId, Model model){
        ResultEntity<DetailProjectVO> detailProjectVORemote = mySQLRemoteService.getDetailProjectVORemote(projectId);
        if(ResultEntity.SUCCESS.equals(detailProjectVORemote.getResult())){
            DetailProjectVO detailProjectVO = detailProjectVORemote.getData();
            model.addAttribute("detailProjectVO",detailProjectVO);
        }

        return "project-detail-show";
    }




}
