package com.hxx.demo.dao;

import com.hxx.demo.entity.Repair;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface RepairDao {
    /**
     * @return com.hxx.demo.entity.Repair
     * @Author Hxx
     * @Description //TODO 申请报修
     * @Date 16:56 2019/10/31
     * @Param [repair]
     **/

    @Insert("INSERT INTO repair (id,buildId,roomId,description,applicant,status,time,flag,result) VALUES (#{repair.id},#{repair.buildId},#{repair.roomId},#{repair.description},#{repair.applicant},#{repair.status},#{repair.time},#{repair.flag},#{repair.result})")
    int createRepairForm(@Param("repair") Repair repair);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理报修
     * @Date 9:19 2019/11/1
     * @Param [repair]
     **/
    @Update("UPDATE repair SET status=#{repair.status},endTime=#{repair.endTime},operator=#{repair.operator} WHERE id=#{repair.id}")
    int handleRepair(@Param("repair") Repair repair);


    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 维修记录
     * @Date 15:10 2019/10/28
     * @Param [status]
     **/
    @Select("SELECT * FROM repair WHERE buildId=#{buildId} AND roomId=#{roomId} AND status =1 ")
    List<Repair> findByStatus(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

    /**
     * @return
     * @Author Hxx
     * @Description //TODO 报修列表
     * @Date 9:33 2019/10/30
     * @Param
     **/
    @Select("SELECT * FROM repair WHERE status =0")
    List<Repair> findAllRepair();


    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 根据报修人删除报修信息
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     **/
    @Delete("delete  from repair where applicant=#{applicant}")
    void deleteRepair(@Param("applicant") String applicant);


    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 清空维修记录
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     **/
    @Delete("DELETE  FROM repair WHERE status=1")
    int deleteRecord();

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 根据关键字查找报修信息
     * @Date 12:44 2019/12/27
     * @Param [sql]
     **/
    @Select(" SELECT * FROM repair WHERE status=0 AND ${sql} ")
    List<Repair> findBykeywords(@Param("sql") String sql);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 根据id删除报修信息
     * @Date 12:53 2020/2/10
     * @Param [id]
     **/
    @Delete("DELETE  FROM repair WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 批量删除报修信息
     * @Date 12:44 2019/12/27
     * @Param [ids]
     **/
    @Delete("<script>DELETE FROM repair WHERE id IN <foreach collection=\"ids\" separator=\",\" open=\"(\" close=\")\" item=\"id\"> #{id}</foreach></script>")
    int deleteBatch(@Param("ids") String[] ids);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 编辑报修信息
     * @Date 12:46 2019/12/27
     * @Param [repair]
     **/
    @Update("UPDATE repair SET description=#{repair.description},time=#{repair.time} WHERE id=#{repair.id}")
    int update(@Param("repair") Repair repair);

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 数据总量
     * @Date 12:53 2020/2/10
     * @Param []
     **/
    @Select("select count(1) from repair")
    int total();

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 申请报修列表
     * @Date 18:37 2019/12/21
     * @Param []
     **/
    @Select("SELECT * FROM repair WHERE buildId=#{buildId} and roomId=#{roomId}")
    List<Repair> findApplyList(@Param("buildId") Integer buildId, @Param("roomId") String roomId);

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 历史记录
     * @Date 19:40 2019/12/21
     * @Param []
     **/
    @Select("SELECT * FROM repair WHERE  status =1 ")
    List<Repair> findHistory();

    /**
     * @return java.util.List<com.hxx.demo.entity.Repair>
     * @Author Hxx
     * @Description //TODO 审核列表
     * @Date 11:51 2020/2/10
     * @Param []
     **/
    @Select("SELECT * FROM repair WHERE flag =0")
    List<Repair> findAudting();

    /**
     * @return int
     * @Author Hxx
     * @Description //TODO 确认审核
     * @Date 14:50 2020/2/10
     * @Param [repair]
     **/
    @Update("UPDATE repair SET flag=#{repair.flag} WHERE id=#{repair.id}")
    int handleAudting(@Param("repair")Repair repair);
}
