package com.hxx.demo.dao;

import com.hxx.demo.entity.Security;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface SecurityDao {
    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 安全隐患列表
     * @Date 15:40 2019/11/6
     * @Param []
     **/
    @Select("SELECT * FROM security WHERE status=0")
    List<Security> findAll();

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 发布安全隐患信息
     * @Date 16:56 2019/11/7
     * @Param [security]
     **/
    @Insert("INSERT INTO security (id,buildId,roomId,description,discover,discoverTime,status,flag,result)VALUES(#{security.id},#{security.buildId},#{security.roomId},#{security.description},#{security.discover},#{security.discoverTime},#{security.status},#{security.flag},#{security.result})")
    int insertSecurity(@Param("security") Security security);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 根据id删除
     * @Date 11:35 2019/12/17
     * @Param [id]
     **/
    @Delete("DELETE FROM security WHERE id=#{id}")
    int deleteById(@Param("id") String id);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 编辑隐患信息
     * @Date 14:19 2019/12/17
     * @Param [security]
     **/
    @Update("UPDATE security SET buildId=#{security.buildId},roomId=#{security.roomId},description=#{security.description},discoverTime=#{security.discoverTime} where id = #{security.id}")
    int update(@Param("security") Security security);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 申请复查
     * @Date 9:19 2019/11/1
     * @Param [repair]
     **/
    @Update("UPDATE security SET status=#{security.status},flag=#{security.flag},result=#{security.result},operateTime=#{security.operateTime},operator=#{security.operator} WHERE id=#{security.id}")
    int handleSecurity(@Param("security") Security security);

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 处理复查
     * @Date 10:38 2019/12/17
     * @Param []
     **/
    @Update("UPDATE security SET status=#{security.status},flag=#{security.flag},result=#{security.result},checker=#{security.checker},checkTime=#{security.checkTime} WHERE id=#{security.id}")
    int handleWait(@Param("security") Security security);

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO  申请复查列表
     * @Date 11:38 2019/12/18
     * @Param []
     **/
    @Select("SELECT * FROM security WHERE buildId=#{buildId} AND roomId=#{roomId} AND result <> 2")
    List<Security> findApply(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

    @Select("SELECT COUNT(1) FROM security WHERE result <> 2")
    int applyTotal();

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 待复查列表
     * @Date 10:38 2019/12/17
     * @Param []
     **/
    @Select("SELECT * FROM security WHERE status=1 AND flag =1 AND result=1")
    List<Security> findWait();

    @Select("SELECT COUNT(1) FROM security WHERE status=1 AND flag =1 AND result=1 ")
    int waitTotal();

    /**
     * @return java.util.List<com.hxx.demo.entity.Security>
     * @Author Hxx
     * @Description //TODO 已处理列表
     * @Date 10:38 2019/12/17
     * @Param []
     **/
    @Select("SELECT * FROM security WHERE status=1 AND flag =1 AND result=2")
    List<Security> findSolved();

    @Select("SELECT COUNT(1) FROM security where status=1 AND flag =1 AND result=2 ")
    int solvedTotal();

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 根据指定字段查询隐患信息
     * @Date 16:08 2019/12/19
     * @Param []
     **/
    @Select(" SELECT * FROM security WHERE status=0 AND flag=0 AND ${sql} ")
    List<Security> findBykeywords(@Param("sql") String sql);

    @Select("SELECT * FROM security WHERE id=#{id}")
    Security findById(@Param("id") String id);


    @Select("SELECT COUNT(1) FROM security")
    int total();

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 批量删除
     * @Date 16:01 2019/12/27
     * @Param [ids]
     **/
    @Delete("<script>DELETE FROM security WHERE id IN <foreach collection=\"ids\" separator=\",\" open=\"(\" close=\")\" item=\"id\"> #{id}</foreach></script>")
    int deleteBatch(@Param("ids") String[] ids);
}
