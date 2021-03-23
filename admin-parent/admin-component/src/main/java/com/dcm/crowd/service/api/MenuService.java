package com.dcm.crowd.service.api;

import com.dcm.crowd.entity.Menu;
import com.dcm.crowd.mapper.MenuMapper;

import java.util.List;

public interface MenuService {
    List<Menu> getAll();


    void saveMenu(Menu menu);

    void removeMenu(Integer id);



    void updateMenu(Menu menu);
}
