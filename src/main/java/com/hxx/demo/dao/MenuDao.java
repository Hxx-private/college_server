package com.hxx.demo.dao;

import com.hxx.demo.entity.Menu;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {


    List<Menu> getAllMenu();


    //    @Select("SELECT\n" +
//            "\tm1.`id`,\n" +
//            "\tm1.`path`,\n" +
//            "\tm1.`component`,\n" +
//            "\tm1.`iconCls`,\n" +
//            "\tm1.`name`,\n" +
//            "\tm1.`requireAuth`,\n" +
//            "\tm2.`component` AS component2,\n" +
//            "\tm2.`iconCls` AS iconCls2,\n" +
//            "\tm2.`keepAlive` AS keepAlive2,\n" +
//            "\tm2.`name` AS name2,\n" +
//            "\tm2.`path` AS path2,\n" +
//            "\tm2.`requireAuth` AS requireAuth2\n" +
//            "FROM\n" +
//            "\tmenu m1,\n" +
//            "\tmenu m2\n" +
//            "WHERE\n" +
//            "\tm1.`id` = m2.`parentId`\n" +
//            "AND m1.`id` != 1\n" +
//            "AND m2.`id` IN (\n" +
//            "\tSELECT\n" +
//            "\t\tmr.`mid`\n" +
//            "\tFROM\n" +
//            "\t\tuser_role u_r,\n" +
//            "\t\tmenu_role mr\n" +
//            "\tWHERE\n" +
//            "\t\tu_r.`rid` = mr.`rid`\n" +
//            "\tAND u_r.`userid` = #{userId}) and m2.`enabled`=true order by m1.`id`,m2.`id`")
//    @Results(id = "menuMap", value = {
//            @Result(property = "id", column = "id", id = true),
//            @Result(property = "meta", one = @One(select = "com.hxx.demo.dao.UserDao.getMenusByUserId")),
//            @Result(property = "roles", many = @Many(select = "com.hxx.demo.dao.UserDao.getMenusByUserId")),
//            @Result(property = "", column = ""),
//    })
    List<Menu> getMenusByUserId(Long id);

    List<Menu> menuTree();

    List<Long> getMenusByRid(Long rid);
}
