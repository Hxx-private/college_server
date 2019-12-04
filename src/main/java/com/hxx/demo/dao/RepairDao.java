package com.hxx.demo.dao;

import com.hxx.demo.entity.Repair;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author Hxx
 */
@Mapper
public interface RepairDao{
    /**
     * @return com.hxx.demo.entity.Repair
     * @Author Hxx
     * @Description //TODO 提交报修信息
     * @Date 16:56 2019/10/31
     * @Param [repair]
     **/

    @Insert("INSERT INTO repair (id,room_id,description,applicant,status,time) VALUES (#{repair.id},#{repair.roomId},#{repair.description},#{repair.applicant},#{repair.status},#{repair.time})")
    void createRepairForm(@Param("repair") Repair repair);

    /**
     * @return void
     * @Author Hxx
     * @Description //TODO 处理报修信息
     * @Date 9:19 2019/11/1
     * @Param [repair]
     **/
    @Update("UPDATE repair SET status=#{repair.status},end_time=#{repair.endTime},operator=#{repair.operator} WHERE id=#{repair.id}")
    void handleRepair(@Param("repair") Repair repair);


    /**
     * @return com.hxx.demo.entity.User
     * @Author Hxx
     * @Description //TODO 根据id查找报修信息
     * @Date 15:10 2019/10/28
     * @Param [userName]
     **/
    @Select("SELECT * FROM repair WHERE id = #{id} ")
    Repair findById(@Param("id") String id);
    /**
     * @return
     * @Author Hxx
     * @Description //TODO 查询所有报修信息
     * @Date 9:33 2019/10/30
     * @Param
     **/
    @Select("SELECT * FROM repair")
    List<Repair> findAllRepair();


    /**
     * @return java.util.List<com.hxx.demo.entity.Lost>
     * @Author Hxx
     * @Description //TODO 根据报修人查询他所提交的报修信息
     * @Date 10:39 2019/10/31
     * @Param [applicant]
     **/
    @Select("SELECT  * FROM repair WHERE applicant=#{applicant}")
    Repair  getByApplicant(@Param("applicant") String applicant);

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
     * @Author Hxx
     * @Description //TODO 根据报修人删除报修信息
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     * @return void
     **/
    @Delete("delete  from repair where applicant=#{applicant}")
    void deleteRepair(@Param("applicant") String applicant);


    /**
     * @Author Hxx
     * @Description //TODO 根据宿舍id删除报修信息
     * @Date 9:28 2019/11/7
     * @Param [roomId]
     * @return void
     **/
    @Delete("delete  from repair where room_id=#{roomId}")
    void deleteRepByRid(@Param("roomId") String roomId);
}
