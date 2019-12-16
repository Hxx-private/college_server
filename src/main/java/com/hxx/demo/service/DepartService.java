package com.hxx.demo.service;

import com.hxx.demo.dao.DepartDao;
import com.hxx.demo.entity.Depart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartService {
    @Autowired
    private DepartDao departDao;

    public List<Depart> find() {
        return departDao.find();
    }
}
