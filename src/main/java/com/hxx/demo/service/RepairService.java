package com.hxx.demo.service;

import com.hxx.demo.dao.RepairDao;
import com.hxx.demo.entity.Repair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hxx
 */
@Service
@Transactional
public class RepairService{
    @Autowired
    private RepairDao repairDao;

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建报修信息
     * @Date 17:01 2019/10/31
     * @Param [repair]
     **/
    public void create(Repair repair) {
        repairDao.createRepairForm(repair);
    }


    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据id查找报修信息
     * @Date 17:05 2019/10/28
     * @Param [userName]
     **/
    public Repair findById(String id) {
        return repairDao.findById(id);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理报修信息
     * @Date 10:13 2019/11/1
     * @Param [repair]
     **/
    public void handleRepair(Repair repair) {
        repairDao.handleRepair(repair);
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
    public Repair  getByApplicant(String applicant) {
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
    public void deleteRepByRid(String roomId) {
        repairDao.deleteRepByRid(roomId);
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

}
