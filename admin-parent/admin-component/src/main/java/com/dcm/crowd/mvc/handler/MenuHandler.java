package com.dcm.crowd.mvc.handler;

import com.dcm.crowd.entity.Menu;
import com.dcm.crowd.service.api.MenuService;
import com.dcm.crowd.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MenuHandler {
    @Autowired
    private MenuService menuService;


    @RequestMapping("/menu/get/whole/tree.json")
    @ResponseBody
    public ResultEntity<Menu> getWholeTreeNew(){
        List<Menu> menuList=menuService.getAll();

        Menu root=null;

        Map<Integer,Menu> menuMap=new HashMap<>();
        for(Menu menu:menuList){
            Integer id=menu.getId();
            menuMap.put(id,menu);

        }

        for (Menu menu :
                menuList) {
            Integer pid =menu.getPid();
            if(pid==null){
                root=menu;
                continue;
            }
            Menu father=menuMap.get(pid);
            father.getChildren().add(menu);
        }

        return ResultEntity.successWithData(root);
    }

}
