package com.hxx.demo.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/**
 * @author Hxx
 */
public interface BuildDao {
    @Insert("INSERT INTO build set buildId=#{buildId}")
    int addBuild(@Param("buildId") Integer buildId);

    @Insert("INSERT INTO build_room set buildId=#{buildId},roomId=#{roomId}")
    int addInfo(Integer buildId, String roomId);
}
