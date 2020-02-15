package com.hxx.demo.dao;

import com.hxx.demo.entity.Lost;
import com.hxx.demo.entity.PeriodTime;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * @Author Hxx
 * @Description //TODO  失物信息表信息持久化
 * @Date 9:34 2019/10/30
 * @Param
 * @return
 **/

@Mapper
public interface LostDao {

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 失物信息中心
     * @Date 9:33 2019/10/30
     * @Param
     **/
    @Select("SELECT * FROM lost")
    List<Lost> findAllLost();

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建丢失信息帖子
     * @Date 9:34 2019/10/30
     * @Param [lost]
     **/
    @Insert("INSERT INTO lost VALUES(#{lost.id}, #{lost.content},#{lost.creater},#{lost.createTime},#{lost.imgUrl})")
    int insertLost(@Param("lost") Lost lost);






    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据指定时间段查询丢失物品信息
     * @Date 10:41 2019/10/31
     * @Param [periodTime]
     **/
    @Select("select * from lost WHERE createTime BETWEEN #{periodTime.startTime} and #{periodTime.endTime}")
    List<Lost> getLostByPeriod(@Param("periodTime") PeriodTime periodTime);


    @Select("select count(1) from lost")
    int total();

    @Select("SELECT COUNT(*) FROM lost_history")
    int HistoryTotal();

    @Select("SELECT * FROM lost_history where creater=#{creater}")
    List<Lost> findAllHistoryLost(@Param("creater") String creater);

    @Delete("DELETE from lost_history")
    int deleteAll();

    @Insert("INSERT INTO lost_history VALUES(#{lost.id}, #{lost.content},#{lost.creater},#{lost.imgUrl},#{lost.createTime})")
    int insertLost_History(@Param("lost") Lost lost);


    @Select("SELECT * FROM lost WHERE concat(content,creater,id,createTime) LIKE \'%${keywords}%\' ")
    List<Lost> findByKewords(@Param("keywords") String keywords);
}

