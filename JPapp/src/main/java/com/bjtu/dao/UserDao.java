<<<<<<< HEAD
package com.bjtu.dao;

import com.bjtu.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import javax.swing.text.StyledEditorKit;
import java.util.List;

@Mapper
public interface UserDao {


    public void insert(User user);
    public void deleteByName(String username);
    public List<User> findAll();
    public User findByName(String username);
    String getEmail();
    Boolean modifyPassword(String username, String password);

}
=======
//package com.bjtu.dao;
//
//import com.bjtu.pojo.Admin;
//import com.bjtu.pojo.Teacher;
//import com.bjtu.pojo.User;
//import com.bjtu.pojo.Student;
//import org.apache.ibatis.annotations.Mapper;
//
//import java.util.List;
//
//@Mapper
//public interface UserDao {
//
////    插入
///*    public void insert(User user);
//
////    查询
//    public List<User> findAll();
//    public User findByNum(String num);
//    public void deleteByNum(String num);*/
//
//}
>>>>>>> main
