package com.dcm.crowd.service.impl;

import com.dcm.crowd.entity.Menu;
import com.dcm.crowd.entity.MenuExample;
import com.dcm.crowd.mapper.MenuMapper;
import com.dcm.crowd.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> getAll() {
        return menuMapper.selectByExample(new MenuExample());
    }
}
