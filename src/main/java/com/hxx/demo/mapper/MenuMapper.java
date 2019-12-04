package com.hxx.demo.mapper;


import com.hxx.demo.entity.Menu;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
public interface MenuMapper  {


    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long userId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
