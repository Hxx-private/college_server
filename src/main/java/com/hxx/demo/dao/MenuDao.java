package com.hxx.demo.dao;

import com.hxx.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface MenuDao {
    /**
     * @return java.util.List<com.hxx.demo.entity.Menu>
     * @Author Hxx
     * @Description //TODO 获取所有菜单项
     * @Date 15:16 2019/11/12
     * @Param []
     **/
    @Select("SELECT m.*, r.id AS rid,r. NAME AS rname FROM menu m LEFT JOIN menu_role mr ON m.id = mr.mid LEFT JOIN role r ON mr.rid = r.id WHERE m.enabled = TRUE ORDER BY m.id ASC")
    List<Menu> getAllMenu();

    /**
     * @return java.util.List<com.hxx.demo.entity.Menu>
     * @Author Hxx
     * @Description //TODO 跟据用户id获取对应的菜单项
     * @Date 15:17 2019/11/12
     * @Param [userId]
     **/
    @Select("SELECT m1.id,m1.path,m1.component,m1.iconCls,m1. NAME,m1.requireAuth,m2.component AS component2,m2.iconCls AS iconCls2,m2.keepAlive AS keepAlive2,m2. NAME AS name2,m2.path AS path2,m2.requireAuth AS requireAuth2 FROM menu m1,menu m2 WHERE m1.id = m2.parentId AND m1.id != 1 AND m2.id IN (\n" +
            "SELECT mr.mid FROM user_role u_r,menu_role mr WHERE u_r.rid = mr.rid AND u_r.user_id =#{userId})AND m2.enabled = TRUE ORDER BY m1.id,m2.id")
    List<Menu> getMenusByUserId(@Param("userId") Long userId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Menu>
     * @Author Hxx
     * @Description //TODO
     * @Date 15:17 2019/11/12
     * @Param []
     **/
    @Select("SELECT m1.id,m1.name,m2.id AS id2,m2.name AS name2,m3.id AS id3,m3.name AS name3 FROM menu m1,menu m2,menu m3 WHERE m1.id = m2.parentId AND m2.id = m3.parentId AND m3.enabled = TRUE;")
    List<Menu> menuTree();

    /**
     * @return java.util.List<com.hxx.demo.entity.Menu>
     * @Author Hxx
     * @Description //TODO 跟据角色id获取对应菜单
     * @Date 15:18 2019/11/12
     * @Param [rid]
     **/
    @Select("SELECT mid from menu_role WHERE rid=#{rid}")
    List<Long> getMenusByRid(@Param("rid") Long rid);
}
