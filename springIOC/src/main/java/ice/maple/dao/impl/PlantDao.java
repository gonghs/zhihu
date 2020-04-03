package ice.maple.dao.impl;

import ice.maple.dao.IUserDao;
import ice.maple.entity.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("userDao")
public class PlantDao implements IUserDao {
    private String name = "maple";
    private String sex = "man";

    @Override
    public List<User> getUsers() {
        User u = new User(name,sex);
        List<User> l = new ArrayList();
        l.add(u);
        return l;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
