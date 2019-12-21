package com.hxx.demo.dao;

import com.hxx.demo.entity.Sanitary;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * @author Martin
 * @description 操作数据库表Sanitary
 * @date 2019/10/31
 */
@Mapper
public interface SanitaryDao {

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建卫生检查信息表
     * @Date 13:08 2019/11/2
     * @Param [sanitary]
     **/
    @Insert("insert into sanitary values(#{sanitary.id},#{sanitary.uName},#{sanitary.checkTime},#{sanitary.roomId},#{sanitary.content},#{sanitary.grade},#{sanitary.buildId})")
    int addSanitary(@Param("sanitary") Sanitary sanitary);


    /**
     * @return
     * @Author Hxx
     * @Description //TODO 查询所有卫生检查信息
     * @Date 13:08 2019/11/2
     * @Param
     **/
    @Select("select * from sanitary")
    List<Sanitary> selectAllSanitary();


    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据id查询检查信息
     * @Date 13:11 2019/11/2
     * @Param
     **/

    @Select("select * from sanitary where id=#{id}")
    List<Sanitary> findById(@Param("id") String id);

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据检查时间查看卫生信息
     * @Date 13:11 2019/11/2
     * @Param
     **/

    @Select("select * from sanitary where checkTime=#{checkTime}")
    List<Sanitary> findBySaCheckTime(@Param("checkTime") String checkTime);

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据宿舍id查询历史检查信息
     * @Date 13:11 2019/11/2
     * @Param
     **/

    @Select("select * from sanitary where roomId=#{roomId}")
    List<Sanitary> findBySaRoomid(@Param("roomId") String roomId);


    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据宿舍id和检查时间查询卫生信息
     * @Date 14:06 2019/11/2
     * @Param []
     **/
    @Select("SELECT * FROM sanitary WHERE roomId =#{roomId} AND checkTime like '%${checkTime}%'")
    List<Sanitary> findRidByCtm(@Param("roomId") String roomId, @Param("checkTime") String checkTime);


    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id来删除卫生检查信息
     * @Date 14:56 2019/11/7
     * @Param [user]
     **/
    @Delete("DELETE FROM sanitary WHERE roomId = #{roomId}")
    int delBySaroomId(@Param("roomId") String roomId);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据检查时间来删除卫生检查信息
     * @Date 15:00 2019/11/7
     * @Param [user]
     **/
    @Delete("delete  from sanitary where checkTime= #{checkTime}")
    void delBycheckTime(@Param("checkTime") String checkTime);


    @Update("update sanitary set uName=#{sanitary.uName},checkTime=#{sanitary.checkTime},roomId=#{sanitary.roomId}," +
            "content=#{sanitary.content},grade=#{sanitary.grade},buildId=#{sanitary.buildId} where id = #{sanitary.id}")
    int update(@Param("sanitary") Sanitary sanitary);

    @Select("SELECT COUNT(1) FROM sanitary")
    int total();

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据指定字段查询卫生信息
     * @Date 15:51 2019/12/19
     * @Param [sql]
     **/
    @Select(" SELECT * FROM sanitary WHERE  ${sql}")
    List<Sanitary> findBykeywords(@Param("sql") String sql);
}
