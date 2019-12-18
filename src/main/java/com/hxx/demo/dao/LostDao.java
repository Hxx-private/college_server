package com.hxx.demo.dao;

import com.hxx.demo.entity.Lost;
import com.hxx.demo.entity.PeriodTime;
import org.apache.ibatis.annotations.*;

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
     * @Description //TODO 查询所有丢失信息
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
    @Insert("INSERT INTO lost VALUES(#{lost.id},  #{lost.content},#{lost.creater},#{lost.createTime})")
    int insertLost(@Param("lost") Lost lost);


    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据创建者查询他发过的所有信息
     * @Date 10:39 2019/10/31
     * @Param [creater]
     **/
    @Select("select  * from lost where creater=#{creater}")
    List<Lost> getLostByCreater(@Param("creater") String creater);

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据发布时间查询失物信息
     * @Date 10:39 2019/10/31
     * @Param [creater]
     **/
    @Select("select  * from lost where create_time=#{createTime}")
    List<Lost> findBycreateTime(@Param("createTime") String createTime);

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 查询当天所有的lost信息
     * @Date 10:40 2019/10/31
     * @Param []
     **/
    @Select("select * from lost where to_days(create_time)=to_days(now())")
    List<Lost> getTodayAllLost();

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据指定时间段查询丢失物品信息
     * @Date 10:41 2019/10/31
     * @Param [periodTime]
     **/
    @Select("select * from lost WHERE create_time BETWEEN #{periodTime.startTime} and #{periodTime.endTime}")
    List<Lost> getLostByPeriod(@Param("periodTime") PeriodTime periodTime);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据创建人来删除失物信息
     * @Date 14:56 2019/11/7
     * @Param [user]
     **/
    @Delete("delete from lost where creater =#{creater}")
    void delBycreater(@Param("creater") String creater);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据创建时间来删除失物信息帖子
     * @Date 15:00 2019/11/7
     * @Param [user]
     **/
    @Delete("delete  from lost where create_time= #{createTime}")
    void delBycreateTime(@Param("createTime") String createTime);


    @Select(" SELECT * FROM lost WHERE  ${sql}")
    List<Lost> findBykeywords(@Param("sql") String sql);


    @Select("select count(1) from lost")
    int total();

    @Select("SELECT COUNT(*) FROM lost_history")
    int HistoryTotal();

    @Select("SELECT * FROM lost_history")
    List<Lost> findAllHistoryLost();

    @Delete("DELETE from lost_history")
    int deleteAll();

    @Insert("INSERT INTO lost_history VALUES(#{lost.id},  #{lost.content},#{lost.creater},#{lost.createTime})")
    int insertLost_History(@Param("lost") Lost lost);
}

