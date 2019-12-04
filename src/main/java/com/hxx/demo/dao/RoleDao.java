package com.hxx.demo.dao;

import com.hxx.demo.entity.Role;
import org.apache.ibatis.annotations.*;

import java.util.List;
/**
 * @Author Hxx
 * @Description //TODO
 * @Date 11:01 2019/11/14
 * @Param
 * @return
 **/
@Mapper
public interface RoleDao {
    @Select(" SELECT * FROM role where id!=6")
    List<Role> roles();

    @Insert("  INSERT INTO role set name=#{role},nameZh=#{roleZh}")
    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    @Delete("DELETE FROM role WHERE id=#{rid}")
    int deleteRoleById(Long rid);
}
