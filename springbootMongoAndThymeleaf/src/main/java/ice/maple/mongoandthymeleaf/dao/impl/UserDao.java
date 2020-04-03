package ice.maple.mongoandthymeleaf.dao.impl;

import ice.maple.mongoandthymeleaf.dao.IUserDao;
import ice.maple.mongoandthymeleaf.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDao implements IUserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<User> getUsers() {
        return mongoTemplate.findAll(User.class,"test");
    }

    @Override
    public User getUser(String id) {
        return mongoTemplate.findOne(new Query(Criteria.where("id").is(id)), User.class);
    }

    @Override
    public void update(User user) {
        Criteria criteria = Criteria.where("id").is(user.get_id());
        Query query = new Query(criteria);
        Update update = Update.update("name",user.getName()).set("set",user.getSex());
        mongoTemplate.updateMulti(query,update,User.class);
    }

    @Override
    public void insert(User user) {
        mongoTemplate.insert(user);
    }

//    @Override
//    public void insert(UserDao user) {
//        mongoTemplate.insert(user);
//    }

    @Override
    public void remove(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        mongoTemplate.remove(query,User.class);
    }
}
