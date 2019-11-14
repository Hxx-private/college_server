package com.hxx.demo.service;

import com.hxx.demo.dao.MenuDao;
import com.hxx.demo.entity.Menu;
import com.hxx.demo.utils.UserUtils;
import org.sang.bean.Menu;
import org.sang.common.HrUtils;
import org.sang.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Service
@Transactional
@CacheConfig(cacheNames = "menus_cache")
public class MenuService {
    @Autowired
    private MenuDao menuDao;
    public List<Menu> getAllMenu(){
        return menuDao.getAllMenu();
    }

    public List<Menu> getMenusByUserId() {
        return menuDao.getMenusByUserId(UserUtils.getCurrentUser().getUserId());
    }

    public List<Menu> menuTree() {
        return menuDao.menuTree();
    }

    public List<Long> getMenusByRid(Long rid) {
        return menuDao.getMenusByRid(rid);
    }
}
