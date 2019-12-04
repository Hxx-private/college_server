package com.hxx.demo.mapper;

import com.hxx.demo.entity.MsgContent;
import com.hxx.demo.entity.SysMsg;
import com.hxx.demo.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Hxx
 */
public interface SysMsgMapper {

    int sendMsg(MsgContent msg);

    int addMsg2AllUser(@Param("users") List<User> users, @Param("mid") Long mid);

    List<SysMsg> getSysMsg(@Param("start") int start, @Param("size") Integer size, @Param("userId") Long userId);

    int markRead(@Param("flag") Long flag, @Param("userId") Long userId);
}
