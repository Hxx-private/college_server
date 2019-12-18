package com.hxx.demo.dao;

import com.hxx.demo.entity.Security;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface SecurityHistoryDao {
    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 历史记录
     * @Date 9:32 2019/12/17
     * @Param [securityHistory]
     *
     **/
    @Insert("INSERT INTO security_history(id,buildId,roomId,description,discover,discoverTime,status,flag) values(#{securityHistory.id},#{securityHistory.buildId},#{securityHistory.roomId},#{securityHistory.description},#{securityHistory.discover},#{securityHistory.discoverTime},#{securityHistory.status},#{securityHistory.flag})")
    int addhistory(@Param("securityHistory") Security securityHistory);

    @Select("SELECT * FROM security_history")
    List<Security> find();

    @Select("SELECT COUNT(1) FROM security_history")
    int total();

    @Delete("DELETE * FROM security_history")
    int delete();


//    operator,operateTime,
//    #{securityHistory.operator},#{securityHistory.operateTime},
}
