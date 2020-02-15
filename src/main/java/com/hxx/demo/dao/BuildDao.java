package com.hxx.demo.dao;

import com.hxx.demo.entity.Build;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Hxx
 */
public interface BuildDao {
    @Insert("INSERT INTO build set buildId=#{buildId}")
    int addBuild(@Param("buildId") Integer buildId);

    @Insert("INSERT INTO build_room set buildId=#{buildId},roomId=#{roomId}")
    int addInfo(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

    @Select("select * from build")
    List<Build> getBuild();

    @Select("SELECT * FROM build WHERE buildId=#{buildId}")
    Build findByBuildId(@Param("buildId") Integer buildId);
}
