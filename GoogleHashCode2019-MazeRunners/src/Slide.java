import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Slide extends Photo {
    Photo a, b;


    public Slide(Photo a, Photo b) {
        super(a.id, a.type, a.M, a.tags);


        if (b != null) {
            Set<String> mergetags = new LinkedHashSet<String>();
            mergetags.addAll(a.tags);
            mergetags.addAll(b.tags);

            List<String> mergetagsLst = new ArrayList<String>();
            for (String t : mergetags) {
                mergetagsLst.add(t);
            }
            this.tags = mergetagsLst;
            this.M = this.tags.size();
        }

        this.a = a;
        this.b = b;
    }

    public static Slide horizontalToSlide(Photo photo) {
        if (photo.type.equals("H")) {
            return new Slide(photo, null);
        } else {
            return null;
        }
    }
}
