package com.hxx.demo.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 *
 * @author sang
 * @date 2018/1/2
 */
public interface MenuRoleDao {
    @Delete("DELETE FROM menu_role WHERE rid=#{rid}")
    int deleteMenuByRid(@Param("rid") Long rid);
    @Insert("<script>INSERT INTO menu_role(mid,rid) VALUES <foreach collection=\"mids\" item=\"mid\" separator=\",\"> (#{mid},#{rid})</foreach></script>")
    int addMenu(@Param("rid") Long rid, @Param("mids") Long[] mids);
}
