package com.hxx.demo.mapper;


import com.hxx.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by sang on 2017/12/28.
 */
@Mapper
public interface MenuMapper {


    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long userId);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
