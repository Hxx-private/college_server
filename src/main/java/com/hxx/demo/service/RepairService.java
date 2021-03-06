package com.hxx.demo.service;

import com.hxx.demo.dao.RepairDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Repair;
import com.hxx.demo.utils.ParamsInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Hxx
 */
@Service
@Transactional
public class RepairService {
    @Autowired
    private RepairDao repairDao;

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建报修信息
     * @Date 17:01 2019/10/31
     * @Param [repair]
     **/
    public int create(Repair repair) {
        return repairDao.createRepairForm(repair);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 申请报修列表
     * @Date 18:37 2019/12/21
     * @Param []
     **/
    public List<Repair> findApplyList(Integer buildId,String roomId) {
        return repairDao.findApplyList(buildId,roomId);
    }


    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 维修记录
     * @Date 17:05 2019/10/28
     * @Param [status]
     **/
    public List<Repair> findByStatus(Integer buildId,String roomId) {
        return repairDao.findByStatus(buildId,roomId);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理报修信息
     * @Date 10:13 2019/11/1
     * @Param [repair]
     **/
    public int handleRepair(Repair repair) {
        return repairDao.handleRepair(repair);
    }


    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 显示所有报修信息
     * @Date 9:36 2019/10/30
     * @Param []
     **/
    public List<Repair> findAllRepair() {
        return repairDao.findAllRepair();
    }





    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除报修信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public int deleteRecord() {
        return repairDao.deleteRecord();
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据报修人删除报修信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void deleteRepair(String applicant) {
        repairDao.deleteRepair(applicant);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 根据指定字段查询报修信息
     * @Date 16:04 2019/12/17
     * @Param [gridJson]
     **/
    public List<Repair> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "repair");
        List<Repair> repairs = this.repairDao.findBykeywords(sql);
        return repairs;
    }

    public int deleteById(String id) {
        return repairDao.deleteById(id);
    }

    /**
     * @return boolean
     * @Author Hxx
     * @Description //TODO 批量删除报修信息
     * @Date 17:31 2019/12/13
     * @Param [ids]
     **/
    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        return repairDao.deleteBatch(split) == split.length;
    }


    public int update(Repair repair) {
        return repairDao.update(repair);
    }

    public int total() {
        return repairDao.total();
    }

    public List<Repair> findHistory() {
        return repairDao.findHistory();
    }

    public List<Repair> findAudting() {
        return repairDao.findAudting();
    }

    public int handleAudting(Repair repair) {
        return repairDao.handleAudting(repair);
    }
}
