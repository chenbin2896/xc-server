package com.xuecheng.ucenter.service;

import com.xuecheng.framework.domain.ucenter.XcCompanyUser;
import com.xuecheng.framework.domain.ucenter.XcMenu;
import com.xuecheng.framework.domain.ucenter.XcUser;
import com.xuecheng.framework.domain.ucenter.ext.XcUserExt;
import com.xuecheng.ucenter.dao.XcCompanyUserRepository;
import com.xuecheng.ucenter.dao.XcMenuMapper;
import com.xuecheng.ucenter.dao.XcUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 **/
@Service
public class UserService {

    @Autowired
    XcUserRepository xcUserRepository;

    @Autowired
    XcCompanyUserRepository xcCompanyUserRepository;

    @Autowired
    XcMenuMapper xcMenuMapper;

    //根据账号查询xcUser信息
    public XcUser findXcUserByUsername(String username) {
        return xcUserRepository.findXcUserByUsername(username);
    }

    //根据账号查询用户信息
    public XcUserExt getUserExt(String username) {
        //根据账号查询xcUser信息
        XcUser xcUser = this.findXcUserByUsername(username);
        if (xcUser == null) {
            return null;
        }
        XcUserExt xcUserExt = getUserExtById(xcUser.getId());
        BeanUtils.copyProperties(xcUser, xcUserExt);
        return xcUserExt;
    }

    public XcUserExt getUserById(String userId) {
        //根据账号查询xcUser信息
        XcUser xcUser = xcUserRepository.findById(userId).orElse(new XcUser());

        XcUserExt xcUserExt = getUserExtById(userId);
        BeanUtils.copyProperties(xcUser, xcUserExt);
        return xcUserExt;
    }

    public XcUserExt getUserExtById(String userId) {
        //查询用户所有权限
        List<XcMenu> xcMenus = xcMenuMapper.selectPermissionByUserId(userId);

        //根据用户id查询用户所属公司id
        XcCompanyUser xcCompanyUser = xcCompanyUserRepository.findXcCompanyUserByUserId(userId);
        //取到用户的公司id
        String companyId = null;
        if (xcCompanyUser != null) {
            companyId = xcCompanyUser.getCompanyId();
        }
        XcUserExt xcUserExt = new XcUserExt();
        xcUserExt.setCompanyId(companyId);
        //设置权限
        xcUserExt.setPermissions(xcMenus);
        return xcUserExt;
    }
}
