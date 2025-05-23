package cn.nbmly.springbook.service.impl;

import cn.nbmly.springbook.mapper.DistributeBookMapper;
import cn.nbmly.springbook.mapper.UserMapper;
import cn.nbmly.springbook.service.DistributeBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistributeBookServiceImpl implements DistributeBookService {
    @Autowired
    private DistributeBookMapper distributeBookMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean DistributeBook() {
        return false;
    }
}
