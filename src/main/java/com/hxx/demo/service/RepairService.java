package com.hxx.demo.service;

import com.hxx.demo.dao.RepairDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Repair;
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
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据status查找报修信息
     * @Date 17:05 2019/10/28
     * @Param [status]
     **/
    public List<Repair> findByStatus() {
        return repairDao.findByStatus();
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
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据报修人查询报修信息
     * @Date 10:42 2019/10/31
     * @Param [applicant]
     **/
    public Repair getByApplicant(String applicant) {
        return repairDao.getByApplicant(applicant);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 查询当天所有的报修信息
     * @Date 10:43 2019/10/31
     * @Param []
     **/
    public List<Repair> getTodayRepair() {
        return repairDao.getTodayRepair();
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
     * @Description //TODO 批量删除维修记录
     * @Date 17:31 2019/12/13
     * @Param [ids]
     **/
    public boolean deleteBatch(String ids) {
        String[] split = ids.split(",");
        return repairDao.deleteBatch(split) == split.length;
    }

    public int total(){
        return repairDao.total();
    }
}
