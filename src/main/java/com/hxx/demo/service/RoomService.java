package com.hxx.demo.service;

import com.hxx.demo.dao.BuildDao;
import com.hxx.demo.dao.RoomDao;
import com.hxx.demo.entity.Build;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Room;
import com.hxx.demo.utils.ParamsInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hxx
 */
@Service
@Transactional
public class RoomService {
    @Autowired
    private RoomDao roomDao;
    @Autowired
    private BuildDao buildDao;

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加宿舍信息
     * @Date 9:31 2019/11/7
     * @Param [room]
     **/
    public int addRoom(Room room) {
        return roomDao.addRoom(room);
    }

    public int addBuild(Integer buildId) {
        return buildDao.addBuild(buildId);
    }

    public int addInfo(Integer buildId, String roomId) {
        return buildDao.addInfo(buildId, roomId);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 查看所有宿舍信息
     * @Date 9:05 2019/11/11
     * @Param []
     **/
    public List<Room> findAll() {
        return roomDao.findAllRoomInfo();
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 删除宿舍信息
     * @Date 9:42 2019/11/7
     * @Param [id]
     **/
    public int delRoom(String roomId) {
        return roomDao.deltRoom(roomId);
    }

    public int delRoomInfo(String roomId) {
        return roomDao.delRoomInfo(roomId);
    }

    public List<Room> findById(Integer buildId, String roomId) {
        return roomDao.findById(buildId, roomId);
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 更新宿舍信息
     * @Date 15:48 2019/12/11
     * @Param [room]
     **/
    public int editRoom(Room room) {
        return roomDao.editRoom(room);
    }

    public int editRoomInfo(Room room) {
        return roomDao.editRoomInfo(room);
    }

    public Room findInfo(Integer buildId, String roomId) {
        return roomDao.findInfo(buildId, roomId);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 根据关键字查找宿舍信息
     * @Date 16:02 2019/12/11
     * @Param [gridJson]
     **/
    public List<Room> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "v_build_room");
        List<Room> rooms = this.roomDao.findBykeywords(sql);
        return rooms;
    }

    public int total() {
        return roomDao.total();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO  下拉菜单
     * @Date 14:02 2019/12/23
     * @Param []
     **/
    public List<Room> getRoom() {
        return roomDao.find();
    }

    public List<Build> getBuild() {
        return buildDao.getBuild();
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 15:50 2019/12/27
     * @Param [ids]
     **/
    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        return roomDao.deleteBatchInfo(split) == split.length;
    }

    /**
     * @return com.hxx.demo.entity.Room
     * @Author Hxx
     * @Description //TODO 根据roomId查找宿舍信息
     * @Date 15:20 2019/12/28
     * @Param [roomId]
     **/
    public Room findByRoomId(String roomId) {
        return roomDao.findByRoomId(roomId);
    }

    public Build findByBuildid(Integer buildId) {
       return buildDao.findByBuildId(buildId);
    }
}
