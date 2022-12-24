package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_course.dao.SysDictionaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author User
 * @date 2019/11/24
 * @description
 */
@Service
public class SysDictionaryService {

    @Autowired
    SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findByType(String type) {
        return sysDictionaryRepository.findBydType(type);
    }
}
