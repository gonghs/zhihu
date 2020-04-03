package ice.maple.springbootrest.model;

import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;

@Document(collection = "attach")
public class Attach {

    @Id
    private String id;

    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
