package com.hxx.demo.service;

import com.hxx.demo.dao.LostDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Lost;
import com.hxx.demo.entity.PeriodTime;
import com.hxx.demo.utils.ParamsInitUtils;
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
     * @Description //TODO 根据时间段来查询lost信息
     * @Date 10:43 2019/10/31
     * @Param [periodTime]
     **/
    public List<Lost> getLostByPeriod(PeriodTime periodTime) {
        return lostDao.getLostByPeriod(periodTime);
    }


    public int total() {
        return lostDao.total();
    }

    /**
     * 功能描述//TODO 一键删除所有的丢失记录
     *
     * @param
     * @return int
     * @author Mzx
     * @date 2019/12/17
     */

    public int deleteAll() {
        return lostDao.deleteAll();
    }

    public List<Lost> findAllHistoryLost(String creater) {
        return lostDao.findAllHistoryLost(creater);
    }

    public int Historytotal() {
        return lostDao.HistoryTotal();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 失物信息中心
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
        if (lostDao.insertLost(lost) > 0 && lostDao.insertLost_History(lost) > 0) {
            return 1;
        }
        return 0;
    }


    public List<Lost> findByKewords(String keywords){
        return lostDao.findByKewords(keywords);
    }
}
