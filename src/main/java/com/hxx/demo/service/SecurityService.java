package com.hxx.demo.service;

import com.hxx.demo.dao.SecurityDao;
import com.hxx.demo.entity.GridRequest;
import com.hxx.demo.entity.Security;
import com.hxx.demo.utils.ParamsInitUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SecurityService {

    @Autowired
    private SecurityDao securityDao;

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 隐患列表
     * @Date 15:38 2019/11/8
     * @Param []
     **/
    public List<Security> findAll() {
        return securityDao.findAll();
    }

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 根据id删除
     * @Date 11:25 2019/12/17
     * @Param [id]
     **/
    public int deleteById(String id) {
        return securityDao.deleteById(id);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 发布
     * @Date 15:38 2019/11/8
     * @Param [security]
     **/
    public int insertSecurity(Security security) {
        return securityDao.insertSecurity(security);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理安全隐患
     * @Date 10:13 2019/11/1
     * @Param [repair]
     **/
    public int handleSecurity(Security security) {
        return securityDao.handleSecurity(security);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 申请复查
     * @Date 10:41 2019/12/17
     * @Param []
     **/
    public List<Security> findApply() {
        return securityDao.findApply();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 待复查
     * @Date 10:42 2019/12/17
     * @Param []
     **/
    public List<Security> findWait() {
        return securityDao.findWait();
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 已处理
     * @Date 10:45 2019/12/17
     * @Param []
     **/
    public List<Security> findSolved() {
        return securityDao.findSolved();
    }

    public int update(Security security) {

        return securityDao.update(security);
    }
    public int total() {
        return securityDao.total();
    }


    public List<Security> getGrid(GridRequest gridJson) {
        ParamsInitUtils paramsInitUtils = new ParamsInitUtils();
        String sql = paramsInitUtils.initParams(gridJson, "security");
        List<Security> securitys = this.securityDao.findBykeywords(sql);
        return securitys;
    }
}



