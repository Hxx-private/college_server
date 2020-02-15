package com.hxx.demo.dao;

import com.hxx.demo.entity.Special;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface SpecialDao {
    @Select("SELECT * FROM v_depart_special where departId=#{departId}")
    List<Special> find(@Param("departId") Integer departId);
}
