package com.hxx.demo.dao;


import com.hxx.demo.entity.Room;
import com.hxx.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface RoomDao {
    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加宿舍信息
     * @Date 9:28 2019/11/7
     * @Param [room]
     **/
    @Insert("INSERT INTO user (roomId,remarks,num,createTime) VALUES (#{room.roomId},#{room.remarks},#{room.num},#{room.createTime}")
    int addRoom(@Param("room") Room room);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据id删除宿舍信息
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     **/
    @Delete("DELETE  FROM room where roomId=#{roomId}")
    int deltRoom(@Param("roomId") String roomId);

    @Delete("DELETE  FROM build_room where roomId=#{roomId}")
    int delRoomInfo(@Param("roomId") String roomId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查找宿舍
     * @Date 10:05 2019/11/7
     * @Param [roomId]
     **/
    @Select("DELETE  FROM room where roomId=#{roomId}")
    List<Room> findById(@Param("roomId") String roomId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 显示所有宿舍信息
     * @Date 8:54 2019/11/11
     * @Param []
     **/
    @Select("SELECT * FROM v_build_room")
    List<Room> findAllRoomInfo();

    @Select(" SELECT * FROM v_build_room WHERE  ${sql}")
    List<Room> findBykeywords(@Param("sql") String sql);

    @Update("update room set roomId=#{roomId},num=#{num},remarks=#{remarks}")
    int updateRoom(@Param("room") Room room);
}

