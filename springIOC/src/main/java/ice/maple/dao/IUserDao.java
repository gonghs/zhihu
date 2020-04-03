package ice.maple.dao;

import ice.maple.entity.User;

import java.util.List;

public interface IUserDao {
    List<User> getUsers();
}
