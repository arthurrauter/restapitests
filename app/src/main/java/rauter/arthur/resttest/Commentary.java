package rauter.arthur.resttest;

/**
 * Created by Arthur on 18.09.2015.
 */
public class Commentary {

    String postID;
    String id;
    String name;
    String email;
    String body;

    public Commentary(String postID, String id, String name, String email, String body) {
        this.postID = postID;
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
