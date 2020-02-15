package com.hxx.demo.service;

import com.hxx.demo.dao.SpecialDao;
import com.hxx.demo.entity.Special;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecialService {
    @Autowired
    private SpecialDao specialDao;

    public List<Special> find(Integer departId) {
        return specialDao.find(departId);
    }
}
