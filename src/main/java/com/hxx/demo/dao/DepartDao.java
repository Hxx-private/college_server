package com.hxx.demo.dao;

import com.hxx.demo.entity.Depart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface DepartDao {
    @Select("SELECT * FROM depart")
    List<Depart>find();
}
