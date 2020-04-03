package ice.maple.springbootossupload.service.impl;


import ice.maple.springbootossupload.dao.IAttachDao;
import ice.maple.springbootossupload.model.Attach;
import ice.maple.springbootossupload.service.IAttachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AttachService implements IAttachService {
    @Autowired
    private IAttachDao attachDao;

    @Override
    public List<Attach> getAttachs() {
        return attachDao.getAttachs();
    }

    @Override
    public Attach getAttach(String id) {
        return attachDao.getAttach(id);
    }

    @Override
    public void insert(Attach attach) {
        attachDao.insert(attach);
    }

    @Override
    public void update(Attach attach) {
        attachDao.update(attach);
    }

    @Override
    public void delete(String id) {
        attachDao.delete(id);
    }
}
