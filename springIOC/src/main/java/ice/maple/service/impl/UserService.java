package ice.maple.service.impl;

import ice.maple.dao.IUserDao;
import ice.maple.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao userDao;

    public List<User> getUsers(){
       return userDao.getUsers();
    }

    public UserService(IUserDao userDao) {
        this.userDao = userDao;
    }
//
//    public IUserDao getUserDao() {
//        return userDao;
//    }
//
//    public void setUserDao(IUserDao userDao) {
//        this.userDao = userDao;
//    }
}
