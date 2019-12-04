package com.hxx.demo.service;

import com.hxx.demo.dao.RoomDao;
import com.hxx.demo.entity.Room;
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

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 添加宿舍信息
     * @Date 9:31 2019/11/7
     * @Param [room]
     **/
    public void addRoom(Room room) {
        roomDao.addRoom(room);
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
    public void delRoom(String id) {
        roomDao.deltRoom(id);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Room>
     * @Author Hxx
     * @Description //TODO 根据id查找宿舍
     * @Date 10:06 2019/11/7
     * @Param [id]
     **/
    public List<Room> findById(String roomId) {
        return roomDao.findById(roomId);
    }
}
