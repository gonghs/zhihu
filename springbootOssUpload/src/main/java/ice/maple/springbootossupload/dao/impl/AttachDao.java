package ice.maple.springbootossupload.dao.impl;


import ice.maple.springbootossupload.dao.IAttachDao;
import ice.maple.springbootossupload.model.Attach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttachDao implements IAttachDao {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Attach> getAttachs() {
        return mongoTemplate.findAll(Attach.class);
    }

    @Override
    public Attach getAttach(String id) {
        return mongoTemplate.findById(id,Attach.class);
    }

    @Override
    public void insert(Attach attach) {
        mongoTemplate.insert(attach);
    }

    @Override
    public void update(Attach attach) {
        Criteria criteria = Criteria.where("id").is(attach.getId());
        Query query = new Query(criteria);
        Update update = Update.update("url",attach.getUrl());
        mongoTemplate.updateMulti(query,update,Attach.class);
    }

    @Override
    public void delete(String id) {
        Criteria criteria = Criteria.where("id").is(id);
        Query query = new Query(criteria);
        mongoTemplate.remove(query,Attach.class);
    }

}
