package ice.maple.mongoandthymeleaf.dao;

import ice.maple.mongoandthymeleaf.dao.impl.UserDao;
import ice.maple.mongoandthymeleaf.model.User;

import java.util.List;

public interface IUserDao {
    List<User> getUsers();

    User getUser(String id);

    void update(User user);

    void insert(User user);

    void remove(String id);

//    public void insert(UserDao user);
}
