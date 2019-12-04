package com.hxx.demo.service;

import com.hxx.demo.entity.MsgContent;
import com.hxx.demo.entity.SysMsg;
import com.hxx.demo.entity.User;
import com.hxx.demo.mapper.SysMsgMapper;
import com.hxx.demo.mapper.UserMapper;
import com.hxx.demo.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by sang on 2018/2/2.
 */
@Service
public class SysMsgService {
    @Autowired
    private SysMsgMapper sysMsgMapper;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ROLE_sysadmin')")//只有管理员可以发送系统消息
    public boolean sendMsg(MsgContent msg) {
        int result = sysMsgMapper.sendMsg(msg);
        List<User> allUser = userService.getAllUser();
        int result2 = sysMsgMapper.addMsg2AllUser(allUser, msg.getId());
        return result2==allUser.size();
    }

    public List<SysMsg> getSysMsgByPage(Integer page, Integer size) {
        int start = (page - 1) * size;
        return sysMsgMapper.getSysMsg(start,size, UserUtils.getCurrentUser().getId());
    }

    public boolean markRead(Long flag) {
        if (flag != -1) {
            return sysMsgMapper.markRead(flag, UserUtils.getCurrentUser().getId())==1;
        }
        sysMsgMapper.markRead(flag,UserUtils.getCurrentUser().getId());
        return true;
    }
}
