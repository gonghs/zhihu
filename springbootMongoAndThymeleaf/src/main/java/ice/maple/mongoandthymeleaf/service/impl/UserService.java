package ice.maple.mongoandthymeleaf.service.impl;

import ice.maple.mongoandthymeleaf.dao.IUserDao;
import ice.maple.mongoandthymeleaf.model.User;
import ice.maple.mongoandthymeleaf.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserDao userDao;
    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }

    @Override
    public User getUser(String id) {
        return userDao.getUser(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void add(User user) {
        userDao.insert(user);
    }

    @Override
    public void delete(String id) {
        userDao.remove(id);
    }
}
