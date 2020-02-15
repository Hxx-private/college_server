package com.hxx.demo.service;

import com.hxx.demo.dao.SanitaryDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Sanitary;
import com.hxx.demo.utils.ParamsInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Martin
 * @description sanitary的相关service
 * @date 2019/10/31
 */
@Service
@Transactional
public class SanitaryService {

    @Autowired
    private SanitaryDao sanitaryDao;

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建卫生检查表
     * @Date 13:07 2019/11/2
     * @Param [sanitary]
     **/
    public int addSanitary(Sanitary sanitary) {
        return sanitaryDao.addSanitary(sanitary);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 查询所有卫生检查信息
     * @Date 13:08 2019/11/2
     * @Param []
     **/
    public List<Sanitary> selectAllSanitary() {
        return sanitaryDao.selectAllSanitary();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查询卫生检查信息
     * @Date 14:12 2019/11/2
     * @Param [roomId]
     **/
    public List<Sanitary> findBySaRoomid(String roomId) {
        return sanitaryDao.findBySaRoomid(roomId);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据id查询检查信息
     * @Date 14:36 2019/11/2
     * @Param [id]
     **/
    public List<Sanitary> findById(String id) {
        return sanitaryDao.findById(id);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据检查时间查看卫生信息
     * @Date 14:36 2019/11/2
     * @Param [id]
     **/
    public List<Sanitary> findBySaCheckTime(String checkTime) {
        return sanitaryDao.findBySaCheckTime(checkTime);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据宿舍id和检查时间查询
     * @Date 14:13 2019/11/2
     * @Param [roomId, checkTime]
     **/
    public List<Sanitary> findRidByCtm(String roomId, String checkTime) {
        return sanitaryDao.findRidByCtm(roomId, checkTime);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除卫生检查信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public int delBySaroomId(String roomId) {
        return sanitaryDao.delBySaroomId(roomId);
    }



    public int update(Sanitary sanitary) {
        return sanitaryDao.update(sanitary);
    }

    public int total() {
        return sanitaryDao.total();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Sanitary>
     * @Author Hxx
     * @Description //TODO 根据指定字段查询卫生状况
     * @Date 15:48 2019/12/19
     * @Param [gridJson]
     **/
    public List<Sanitary> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "sanitary");
        List<Sanitary> users = this.sanitaryDao.findBykeywords(sql);
        return users;
    }

    public List<Sanitary> findCheckList(Integer buildId, String roomId) {
        return sanitaryDao.findCheckList(buildId,roomId);
    }

    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        return sanitaryDao.deleteBatch(split) == split.length;
    }
}
