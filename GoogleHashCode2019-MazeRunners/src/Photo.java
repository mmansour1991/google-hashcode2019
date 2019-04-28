import java.util.Collections;
import java.util.List;

public class Photo {

    String type;
    int M, id;
    String mm = "";
    List<String> tags;
    boolean used = false;

    public Photo(int id, String type, int M, List<String> tags) {
        this.id = id;
        this.type = type;
        this.M = M;
        this.tags = tags;

        Collections.sort(tags);
        for (String s : tags) {
            mm += s + " ";
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getM() {
        return M;
    }

    public void setM(int m) {
        M = m;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
