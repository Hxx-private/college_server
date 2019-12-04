package com.hxx.demo.service;

import com.hxx.demo.dao.LostDao;
import com.hxx.demo.entity.Lost;
import com.hxx.demo.entity.PeriodTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Hxx
 * @description
 * @date 2019/10/29
 */
@Service
@Transactional
public class LostService {

    @Autowired
    private LostDao lostDao;

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 显示所有丢失信息
     * @Date 9:36 2019/10/30
     * @Param []
     **/
    public List<Lost> findAllLost() {
        return lostDao.findAllLost();
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 创建丢失信息
     * @Date 9:37 2019/10/30
     * @Param [lost]
     **/
    public int insertLost(Lost lost) {
        if (!lostDao.findBycreateTime(lost.getCreateTime()).isEmpty()) {
            return -1;
        }
        return lostDao.insertLost(lost);
    }


    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据creater创建人来查询他所发布过的帖子
     * @Date 10:42 2019/10/31
     * @Param [creater]
     **/
    public List<Lost> getLostByCreater(String creater) {
        return lostDao.getLostByCreater(creater);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据当天日期来查询当天所有的lost信息
     * @Date 10:43 2019/10/31
     * @Param []
     **/
    public List<Lost> getTodayAllLost() {
        return lostDao.getTodayAllLost();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据时间段来查询lost信息
     * @Date 10:43 2019/10/31
     * @Param [periodTime]
     **/
    public List<Lost> getLostByPeriod(PeriodTime periodTime) {
        return lostDao.getLostByPeriod(periodTime);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据发布时间查询失物信息帖子
     * @Date 16:36 2019/11/8
     * @Param [createTime]
     **/
    public List<Lost> findBycreateTime(String createTime) {
        return lostDao.findBycreateTime(createTime);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据创建人来删除失物信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delBycreater(String creater) {
        lostDao.delBycreater(creater);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据创建时间来删除失物信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delBycreateTime(String createTime) {
        lostDao.delBycreateTime(createTime);
    }

}
