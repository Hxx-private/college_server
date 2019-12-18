package com.hxx.demo.service;

import com.hxx.demo.dao.SecurityHistoryDao;
import com.hxx.demo.entity.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SecurityHistoryService {
    @Autowired
    private SecurityHistoryDao historyDao;

    public int addhistory(Security securityHistory) {

        return historyDao.addhistory(securityHistory);
    }

    public List<Security> find() {
        return historyDao.find();

    }

    public int delete() {
        return historyDao.delete();
    }

    public int total() {
        return historyDao.total();
    }
}
