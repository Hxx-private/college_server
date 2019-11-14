package com.hxx.demo.dao;

import com.hxx.demo.entity.Repair;
import com.hxx.demo.entity.Security;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SecurityDao {
    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 显示所有安全隐患信息
     * @Date 15:40 2019/11/6
     * @Param []
     **/
    @Select("Select * from security")
    List<Security> findAll();

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建安全隐患信息
     * @Date 16:56 2019/11/7
     * @Param [security]
     **/
    @Insert("insert into security values(#{security.id},#{security.description},#{security.discover},#{security.operator},#{security.status},#{security.discoverTime},#{security.roomId})")
    void insertSecurity(@Param("security") Security security);

    /**
     * @return
     * @Author Hxx
     * @Description // TODO 根据id查询隐患信息
     * @Date 16:43 2019/11/7
     * @Param
     **/
    @Select("select * from security where id = #{id}")
    Security findById(@Param("id") String id);

    /**
     * @return
     * @Author Hxx
     * @Description // TODO 根据房间号查信息
     * @Date 16:43 2019/11/7
     * @Param
     **/
    @Select("select * from security where room_id = #{roomId}")
    List<Security> findByRoomId(@Param("roomId") String roomId);

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据发现人来查询
     * @Date 16:43 2019/11/7
     * @Param
     **/
    @Select("select * from security where discover = #{discover}")
    List<Security> findByDiscover(@Param("discover") String discover);

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 查询status=0，也就是未处理的报修事件;status=1,处理完的事件
     * @Date 14:47 2019/11/7
     * @Param
     **/
    @Select("select * from security where status=#{status}")
    List<Security> EventStatus(@Param("status") Integer status);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id来删除安全隐患信息
     * @Date 14:56 2019/11/7
     * @Param [user]
     **/
    @Delete("delete from security where room_id =#{roomId}")
    void delBySeroomId(@Param("roomId") String roomId);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据处理时间来删除安全隐患信息
     * @Date 15:00 2019/11/7
     * @Param [user]
     **/
    @Delete("delete  from security where operate_time= #{operateTime}")
    void delByoperateTime(@Param("operateTime") String operateTime);

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据处理时间来查询安全隐患信息
     * @Date 17:35 2019/11/7
     * @Param [operateTime]
     **/
    @Select("select * from security where operate_time=#{operateTime}")
    List<Security> findByOperTime(@Param("operateTime") String operateTime);


    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理安全隐患信息
     * @Date 9:19 2019/11/1
     * @Param [repair]
     **/
    @Update("UPDATE repair SET status=#{security.status},operate_time=#{operateTime},operator=#{security.operator} WHERE id=#{security.id}")
    void handleSecurity(@Param("security") Security security);
}
