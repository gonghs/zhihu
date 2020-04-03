package ice.maple.springbootossupload.model;

import org.springframework.data.annotation.Id;

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

    public Attach(String url) {
        this.url = url;
    }

    public Attach() {
    }
}
