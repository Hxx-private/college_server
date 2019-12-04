package com.hxx.demo.dao;


import com.hxx.demo.entity.Room;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface RoomDao{
    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加宿舍信息
     * @Date 9:28 2019/11/7
     * @Param [room]
     **/
    @Insert("INSERT INTO user (room_id,remarks,num) VALUES (#{room.roomId},#{room.remarks},#{room.num}")
    void addRoom(@Param("room") Room room);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据id删除宿舍信息
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     **/
    @Delete("delete  from room where room_id=#{roomId}")
    void deltRoom(@Param("roomId") String roomId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查找宿舍
     * @Date 10:05 2019/11/7
     * @Param [roomId]
     **/
    @Select("delete  from room where room_id=#{roomId}")
    List<Room> findById(@Param("roomId") String roomId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 显示所有宿舍信息
     * @Date 8:54 2019/11/11
     * @Param []
     **/
    @Select("select * from room")
    List<Room> findAllRoomInfo();
}
