package com.hxx.demo.dao;

import com.hxx.demo.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {


    List<Menu> getAllMenu();

    List<Menu> getMenusByUserId(Long id);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
