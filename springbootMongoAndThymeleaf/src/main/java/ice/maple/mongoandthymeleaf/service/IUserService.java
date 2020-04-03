package ice.maple.mongoandthymeleaf.service;

import ice.maple.mongoandthymeleaf.model.User;

import java.util.List;

public interface IUserService {
    List<User> getUsers();

    User getUser(String id);

    void update(User user);

    void add(User user);

    void delete(String id);
}
