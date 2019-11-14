package com.hxx.demo.dao;

import com.hxx.demo.entity.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by sang on 2018/1/1.
 */
public interface RoleDao {
    @Select(" SELECT * FROM role where id!=6")
    List<Role> roles();

    @Insert("  INSERT INTO role set name=#{role},nameZh=#{roleZh}")
    int addNewRole(@Param("role") String role, @Param("roleZh") String roleZh);

    @Delete("DELETE FROM role WHERE id=#{rid}")
    int deleteRoleById(Long rid);
}
