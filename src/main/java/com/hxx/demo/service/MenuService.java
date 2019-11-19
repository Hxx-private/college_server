package com.hxx.demo.service;

import com.hxx.demo.entity.Menu;
import com.hxx.demo.mapper.MenuMapper;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {
    @Autowired
    private MenuMapper menuMapper;


    public List<Menu> getAllMenu(){
        return menuMapper.getAllMenu();
    }

    public List<Menu> getMenusByUserId() {
        return menuMapper.getMenusByUserId(UserUtils.getCurrentUser().getId());
    }

    public List<Menu> menuTree() {
        return menuMapper.menuTree();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuMapper.getMenusByRid(rid);
    }
}
