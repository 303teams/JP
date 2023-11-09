package com.bjtu.service.impl;

import com.bjtu.dao.AdminDao;
import com.bjtu.exception.ServiceException;
import com.bjtu.pojo.*;
import com.bjtu.service.AdminService;
import com.bjtu.util.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("adminService")
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public RspObject<User> login(Integer id, String password) {
        System.out.println(id + " " + password);
        Admin admin = adminDao.findByNum(id);
//        System.out.println(student);
        if (admin == null) {
            return RspObject.fail("该管理员不存在!");
        } else if (!admin.getPassword().equals(password)) {
            return RspObject.fail("密码错误!");
        } else {
            String token = TokenUtils.createToken(id.toString(),password);
            admin.setToken(token);
            return RspObject.success("登录成功！",admin);
        }
    }

    @Override
    public RspObject<Boolean> insert(Admin admin) {
        return null;
    }

    @Override
    public RspObject<List<Admin>> searchAll() {
        return null;
    }

    @Override
    public RspObject<Boolean> deleteOne(Integer id) {
        return null;
    }

    @Override
    public RspObject<String> modifyEmail(String email) {
        Admin admin = (Admin) TokenUtils.getCurrentUser();
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            adminDao.updateEmail(admin.getId(),email);
            return RspObject.success("密码修改成功！");
        }
    }

    @Override
    public RspObject<String> modifyPassword(Integer id, String password) {
        Admin admin = adminDao.findByNum(id);
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            adminDao.updatePassword(id,password);
            return RspObject.success("密码修改成功！");
        }
    }

    @Override
    public RspObject<String> modifyPassword(String password) {
        Admin admin = (Admin) TokenUtils.getCurrentUser();
        if(admin == null){
            throw new ServiceException(500,"用户不存在！");
        }else{
            adminDao.updatePassword(admin.getId(),password);
            return RspObject.success("密码修改成功！");
        }
    }

}
