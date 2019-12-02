package com.hxx.demo.mapper;

import org.apache.ibatis.annotations.Param;


/**
 * @author Hxx
 */

public interface MenuRoleMapper {
    int deleteMenuByRid(@Param("rid") Long rid);

    int addMenu(@Param("rid") Long rid, @Param("mids") Long[] mids);
}
