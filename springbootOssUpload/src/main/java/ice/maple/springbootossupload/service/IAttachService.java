package ice.maple.springbootossupload.service;



import ice.maple.springbootossupload.model.Attach;

import java.util.List;

public interface IAttachService {
    List<Attach> getAttachs();

    Attach getAttach(String id);

    void insert(Attach attach);

    void update(Attach attach);

    void delete(String id);
}
