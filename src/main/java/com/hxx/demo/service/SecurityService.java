package com.hxx.demo.service;

import com.hxx.demo.dao.SecurityDao;
import com.hxx.demo.entity.Security;
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
     * @Description //TODO 查询所有安全隐患信息
     * @Date 15:38 2019/11/8
     * @Param []
     **/
    public List<Security>  findAll() {
        return securityDao.findAll();
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 安全隐患信息检查结果发布
     * @Date 15:38 2019/11/8
     * @Param [security]
     **/
    public int insertSecurity(Security security) {
        return securityDao.insertSecurity(security);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据id查找隐患信息
     * @Date 15:39 2019/11/8
     * @Param [id]
     **/
    public Security findById(String id) {

        return securityDao.findById(id);
    }



    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据宿舍id查找检查结果
     * @Date 15:39 2019/11/8
     * @Param [roomId]
     **/
    public List<Security> findByRoomId(String roomId) {
        return securityDao.findByRoomId(roomId);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据发现人查看安全隐患信息
     * @Date 15:40 2019/11/8
     * @Param [discover]
     **/
    public List<Security> findByDiscover(String discover) {
        return securityDao.findByDiscover(discover);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据处理状态来查询隐患信息
     * @Date 15:41 2019/11/8
     * @Param [status]
     **/
    public List<Security> EventStatus(Integer status) {
        return securityDao.EventStatus(status);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除安全隐患信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delBySeroomId(String roomId) {
        securityDao.delBySeroomId(roomId);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据处理时间删除安全隐患信息
     * @Date 15:03 2019/11/7
     * @Param [user]
     **/
    public void delByoperateTime(String operateTime) {
        securityDao.delByoperateTime(operateTime);
    }

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 根据处理时间来查询安全隐患信息
     * @Date 17:36 2019/11/7
     * @Param [operateTime]
     **/
    public List<Security> findByOpTime(String operateTime) {
        return securityDao.findByOperTime(operateTime);
    }

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理安全隐患信息
     * @Date 10:13 2019/11/1
     * @Param [repair]
     **/
    public void handleSecurity(Security security) {
        securityDao.handleSecurity(security);
    }

}



