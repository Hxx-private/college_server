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
    @Insert("insert into sanitary values(#{sanitary.id},#{sanitary.checkTime},#{sanitary.roomId},#{sanitary.content},#{sanitary.grade})")
    void addSanitary(@Param("sanitary") Sanitary sanitary);


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

    @Select("select * from sanitary from check_time=#{checkTime}")
    List<Sanitary> findBySaCheckTime(@Param("checkTime") String checkTime);
    /**
     * @return
     * @Author Hxx
     * @Description //TODO 根据宿舍id查询历史检查信息
     * @Date 13:11 2019/11/2
     * @Param
     **/

    @Select("select * from sanitary from room_id=#{roomId}")
    List<Sanitary> findBySaRoomid(@Param("roomId") String roomId);


    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据宿舍id和检查时间查询卫生信息
     * @Date 14:06 2019/11/2
     * @Param []
     **/
    @Select("SELECT * FROM sanitary WHERE room_id =#{roomId} AND check_time like '%${checkTime}%'")
    List<Sanitary> findRidByCtm(@Param("roomId") String roomId, @Param("checkTime") String checkTime);


    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id来删除卫生检查信息
     * @Date 14:56 2019/11/7
     * @Param [user]
     **/
    @Delete("delete from sanitary where room_id =#{roomId}")
    void delBySaroomId(@Param("roomId") String roomId);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据检查时间来删除卫生检查信息
     * @Date 15:00 2019/11/7
     * @Param [user]
     **/
    @Delete("delete  from sanitary where check_time= #{checkTime}")
    void delBycheckTime(@Param("checkTime") String checkTime);


}
