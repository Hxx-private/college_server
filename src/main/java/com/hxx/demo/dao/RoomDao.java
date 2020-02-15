package com.hxx.demo.dao;


import com.hxx.demo.entity.Build;
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
    @Insert("INSERT INTO room (roomId,remarks,num,createTime) VALUES (#{room.roomId},#{room.remarks},#{room.num},#{room.createTime})")
    int addRoom(@Param("room") Room room);

    @Insert("INSERT INTO build Set buildId=#{room.buildId}")
    int addBuild(@Param("room") Room room);

    @Insert("INSERT INTO build SET buildId=#{room.buildId},roomId=#{roomId}")
    int addRoomInfo(@Param("room") Room room);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 编辑宿舍信息
     * @Date 14:29 2019/12/27
     * @Param []
     **/
    @Update("UPDATE room SET roomId=#{room.roomId},remarks=#{room.remarks},num=#{room.num},createTime=#{room.createTime} WHERE id =#{room.id}")
    int editRoom(@Param("room") Room room);

    @Update("UPDATE build_room SET roomId=#{room.roomId},buildId=#{room.buildId} where id =#{room.id}")
    int editRoomInfo(@Param("room") Room room);

    @Select("select * from build_room where buildId=#{buildId} and roomId=#{roomId}")
    Room findInfo(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

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
     * @Description //TODO 显示所有宿舍信息
     * @Date 8:54 2019/11/11
     * @Param []
     **/
    @Select("SELECT * FROM v_build_room")
    List<Room> findAllRoomInfo();

    @Select(" SELECT * FROM v_build_room WHERE  ${sql}")
    List<Room> findBykeywords(@Param("sql") String sql);

    @Update("UPDATE room SET roomId=#{room.roomId},num=#{room.num},remarks=#{room.remarks} where roomId=#{roomId}")
    int updateRoom(@Param("room") Room room);

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查找宿舍
     * @Date 10:05 2019/11/7
     * @Param [roomId]
     **/
    @Select("SELECT *  FROM build_room WHERE buildId=#{buildId} AND roomId=#{roomId}")
    List<Room> findById(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

    @Select("SELECT COUNT(1) FROM v_build_room")
    int total();

    @Select(" SELECT * FROM room ")
    List<Room> find();

    @Delete("<script>DELETE FROM room WHERE roomId IN <foreach collection=\"ids\" separator=\",\" open=\"(\" close=\")\" item=\"id\"> #{roomId} </foreach></script>")
    int deleteBatch(@Param("roomId") String[] ids);

    @Delete("<script>DELETE FROM build_room WHERE roomId IN <foreach collection=\"ids\" separator=\",\" open=\"(\" close=\")\" item=\"id\"> #{roomId}</foreach></script>")
    int deleteBatchInfo(@Param("roomId") String[] ids);

    @Select("SELECT * FROM room WHERE roomId=#{roomId}")
    Room findByRoomId(@Param("roomId") String roomId);
}

