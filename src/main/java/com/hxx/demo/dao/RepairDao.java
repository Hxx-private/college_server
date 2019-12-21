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

    @Insert("INSERT INTO repair (id,buildId,roomId,description,applicant,status,time) VALUES (#{repair.id},#{repair.buildId},#{repair.roomId},#{repair.description},#{repair.applicant},#{repair.status},#{repair.time})")
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
    @Select("SELECT * FROM repair WHERE status =1 ")
    List<Repair> findByStatus();

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
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据报修人查询他所提交的报修信息
     * @Date 10:39 2019/10/31
     * @Param [applicant]
     **/
    @Select("SELECT  * FROM repair WHERE applicant=#{applicant}")
    Repair getByApplicant(@Param("applicant") String applicant);

    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 查询当天所有的报修信息
     * @Date 10:40 2019/10/31
     * @Param []
     **/
    @Select("SELECT * FROM repair WHERE to_days(time)=to_days(now())")
    List<Repair> getTodayRepair();

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


    @Select(" SELECT * FROM repair WHERE status=0 AND ${sql} ")
    List<Repair> findBykeywords(@Param("sql") String sql);

    @Delete("DELETE  FROM repair WHERE id = #{id}")
    int deleteById(@Param("id") String id);

    @Delete("<script>DELETE FROM repair WHERE id IN <foreach collection=\"ids\" separator=\",\" open=\"(\" close=\")\" item=\"id\">\n" +
            "        #{id}\n" +
            "    </foreach></script>")
    int deleteBatch(@Param("ids") String[] ids);


    @Update("UPDATE repair SET buildId=#{repair.buildId},roomId=#{repair.roomId},description=#{repair.description},time=#{repair.time} WHERE id=#{repair.id}")
    int update(@Param("repair") Repair repair);

    @Select("select count(1) from repair")
    int total();
}
