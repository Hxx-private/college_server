package com.hxx.demo.service;

import com.hxx.demo.mapper.MenuRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by sang on 2018/1/2.
 */
@Service
@Transactional
public class MenuRoleService {
    @Autowired
    private MenuRoleMapper menuRoleMapper;

    public int updateMenuRole(Long rid, Long[] mids) {
        menuRoleMapper.deleteMenuByRid(rid);
        if (mids.length == 0) {
            return 0;
        }
        return menuRoleMapper.addMenu(rid, mids);
    }
}
