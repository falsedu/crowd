package com.dcm.crowd.handler;


import com.dcm.crowd.CrowdUtil;
import com.dcm.crowd.api.MySQLRemoteService;
import com.dcm.crowd.config.ShortMessageProperties;
import com.dcm.crowd.constant.CrowdConstant;
import com.dcm.crowd.entity.po.MemberPO;
import com.dcm.crowd.entity.vo.MemberLoginVO;
import com.dcm.crowd.entity.vo.MemberVO;
import com.dcm.crowd.entity.vo.MemberVO;
import com.dcm.crowd.util.ResultEntity;
import com.dcm.crowd.api.RedisRemoteService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Controller
public class MemberHandler {


    @Autowired
    private ShortMessageProperties shortMessageProperties;


    @Autowired
    private RedisRemoteService redisRemoteService;



    @Autowired
    private MySQLRemoteService mySQLRemoteService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @ResponseBody
    @RequestMapping("/auth/member/send/short/message.json")
    public ResultEntity<String>  sendMessage(@RequestParam("mobile") String mobile){

        String host = shortMessageProperties.getHost();
        String path = shortMessageProperties.getPath();
        String method = shortMessageProperties.getMethod();
        String appCode = shortMessageProperties.getAppCode();
        String smsSignId=shortMessageProperties.getSmsSignId();
        String templateId=shortMessageProperties.getTemplateId();
        ResultEntity<String> sendMessageResultEntity = CrowdUtil.sendCodeByShortMessage(host,path,method,mobile,appCode,smsSignId,templateId);

        if(ResultEntity.SUCCESS.equals(sendMessageResultEntity.getResult())){


            String code=sendMessageResultEntity.getData();
            String key= CrowdConstant.REDIS_CODE_PREFIX+mobile;
            ResultEntity<String> saveCodeResultEntity = redisRemoteService.setRedisKeyValueRemoteWithTimeout(key, code, "5", TimeUnit.MINUTES);

            if(ResultEntity.SUCCESS.equals(saveCodeResultEntity.getResult())){
                return ResultEntity.successWithoutData();
            }
            else{
                return saveCodeResultEntity;

            }

        }else {
            return sendMessageResultEntity;
        }



    }



    @RequestMapping("/auth/member/do/register")
    public String saveMember(MemberVO memberVO, ModelMap modelMap){

        String mobile= memberVO.getMobile();
        String key=CrowdConstant.REDIS_CODE_PREFIX+mobile;
        ResultEntity<String> codeResultEntity=redisRemoteService.getRedisStringValueByKeyRemote(key);
        if(ResultEntity.FAILED.equals(codeResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,codeResultEntity.getMessage());
            return "member-reg";
        }


        String redisCode=codeResultEntity.getData();
        if(redisCode==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_EXISTS);
            return "member-reg";
        }

        String formCode = memberVO.getCode();
        if(!Objects.equals(formCode,redisCode)) {
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_CODE_NOT_CORRECT);
            return "member-reg";

        }
        redisRemoteService.removeRedisKeyRemote(key);



            String userpswd = memberVO.getUserpswd();
            userpswd=bCryptPasswordEncoder.encode(userpswd);
            memberVO.setUserpswd(userpswd);


            MemberPO memberPO = new MemberPO();
            BeanUtils.copyProperties(memberVO,memberPO);


        ResultEntity<String> stringResultEntity = mySQLRemoteService.saveMember(memberPO);
        if(ResultEntity.FAILED.equals(stringResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,stringResultEntity.getMessage());
            return "member-reg";
        }


        return "redirect:/auth/member/to/login/page";









    }


    @RequestMapping("/auth/member/do/login")
    public String login(@RequestParam("loginacct") String loginacct, @RequestParam("userpswd")String userpswd, ModelMap modelMap, HttpSession session){
        ResultEntity<MemberPO> memberPOResultEntity = mySQLRemoteService.getMemberPOByLoginAcctRemote(loginacct);
        if(ResultEntity.FAILED.equals(memberPOResultEntity.getResult())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,memberPOResultEntity.getMessage());
            return "member-login";
        }


        MemberPO memberPO=memberPOResultEntity.getData();

        if(memberPO==null){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_LOGIN_ACCT_NOT_EXISTS);
            return "member-login";
        }
        if(!bCryptPasswordEncoder.matches(userpswd,memberPO.getUserpswd())){
            modelMap.addAttribute(CrowdConstant.ATTR_NAME_MESSAGE,CrowdConstant.MESSAGE_USERPSWD_NOT_CORRECT);
            return "member-login";
        }
        MemberLoginVO memberLoginVO=new MemberLoginVO(memberPO.getId(), memberPO.getUsername(), memberPO.getEmail());


        session.setAttribute(CrowdConstant.LOGIN_MEMBER,memberLoginVO);

        return "redirect:http://www.dcm.crowd.com/auth/member/to/center/page";
    }


    @RequestMapping("/auth/member/logout")
    public String logout(HttpSession session,ModelMap modelMap){
        session.removeAttribute(CrowdConstant.LOGIN_MEMBER);
        return "portal";
    }



}
