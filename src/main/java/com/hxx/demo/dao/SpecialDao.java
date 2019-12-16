package com.hxx.demo.dao;

import com.hxx.demo.entity.Special;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface SpecialDao {
    @Select("SELECT * FROM special")
    List<Special> find();
}
