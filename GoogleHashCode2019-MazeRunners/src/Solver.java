import java.util.*;

public class Solver {
    Hashtable<Integer, List<Photo>> hashtable;

    public Solver() {
        this.hashtable = new Hashtable<Integer, List<Photo>>();
    }

    public int calculateScore(Slide slide1, Slide slide2) {
        int common, onlyfirst, onlysecond, minscore, finalscore = 0;

        ArrayList<String> commontags = new ArrayList<String>(slide1.tags);
        commontags.retainAll(slide2.tags);
        common = commontags.size();
        onlyfirst = slide2.tags.size() - common;
        onlysecond = slide1.tags.size() - common;
        minscore = Math.min(common, Math.min(onlyfirst, onlysecond));
        finalscore += minscore;

        return finalscore;
    }

    public int calculateScore(List<Slide> slides) {
        List<Slide> slide = slides;
        int finalscore = 0;
        Slide slide1, slide2;

        int i = 1;
        while (i < slides.size()) {
            slide1 = slide.get(i - 1);
            slide2 = slide.get(i);
            finalscore += calculateScore(slide1, slide2);
//			slide.remove(0);
            i++;
        }
        return finalscore;
    }

    private static Photo getMostScroPhoto(List<Photo> photos, Photo photoR) {
        Photo p = null;
        int minCom = 0;
        for (Photo photo : photos) {
            if (!photo.used && photo.id != photoR.id) {
                int com = getCommon(photo,photoR);
                if (p == null || com < minCom) {
                    p = photo;
                    minCom = com;
                    if (minCom == 0) {
                        break;
                    }
                }
            }
        }
        return p;
    }

    private static int getCommon(Photo photo, Photo photoR) {
        int count =0;
        for (String tag : photo.getTags()) {
            if (photoR.getTags().contains(tag)) {
                count++;
            }
        }
        return count;
    }

    public List<Slide> solve(Main pb) {
        List<Slide> slides = new ArrayList<Slide>();

        List<List<Photo>> allPhotos = separateHorizontalAndVertical(pb.photos);
        List<Photo> horizontalPhotos = allPhotos.get(0);
        List<Photo> verticalPhotos = allPhotos.get(1);

//        int V = 0;
//        if (verticalPhotos.size() > 0) {
//            V = verticalPhotos.get(0).M;
//        }
//
//        Collections.sort(verticalPhotos, new Comparator<Photo>() {
//            public int compare(Photo s2, Photo s1) {
//                return s1.tags.size() - s2.tags.size();
//            }
//        });

        Collections.sort(horizontalPhotos, new Comparator<Photo>() {
            public int compare(Photo s2, Photo s1) {
                return s1.tags.size() - s2.tags.size();
            }
        });

        if (verticalPhotos.size() > 0) {
//            int limitIndex = 0;
//            Photo last = verticalPhotos.get(verticalPhotos.size() - 1);
//            for (int i = 0; i < verticalPhotos.size(); i++) {
//                Photo a = verticalPhotos.get(i);
//
//                if (a.tags.size() + last.tags.size() < V) {
//                    limitIndex = i;
//                    break;
//                }
//            }

//            for (int i = 0; i < limitIndex / 2; i++) {
//                Photo a = verticalPhotos.get(i);
//                Photo b = verticalPhotos.get(limitIndex - 1 - i);
//
//                slides.add(new Slide(a, b));
//            }
//
//            for (int i = limitIndex; i < verticalPhotos.size() / 2; i++) {
//                Photo a = verticalPhotos.get(i);
//                Photo b = verticalPhotos.get(verticalPhotos.size() - 1 - i);
//
//                slides.add(new Slide(a, b));
//            }

//            for (int i = 0; i < verticalPhotos.size(); i+=2) {
//                Photo a = verticalPhotos.get(i);
//                Photo b = verticalPhotos.get(i+1);
//                slides.add(new Slide(a, b));
//            }

            for (int i = 0; i < verticalPhotos.size(); i++) {
                Photo photo = verticalPhotos.get(i);
                if (!photo.used) {
                    Photo chosenPhoto = getMostScroPhoto(verticalPhotos, photo);
                    if (chosenPhoto != null) {
                        chosenPhoto.used = true;
                        photo.used = true;
                        slides.add(new Slide(photo, chosenPhoto));
                    }
                }
            }
        }

        for (int i = 0; i < horizontalPhotos.size(); i++) {
            Photo currentPhoto = horizontalPhotos.get(i);
            slides.add(Slide.horizontalToSlide(currentPhoto));
        }

        Collections.sort(slides, new Comparator<Photo>() {
            public int compare(Photo s2, Photo s1) {
//		    	return s1.tags.size() - s2.tags.size();
                if (s1.getTags().size() == s2.getTags().size()) {
                    return s1.mm.compareTo(s2.mm);
                } else {
                    return s1.tags.size() - s2.tags.size();
                }
            }
        });

        int W = 500;
        for (int i = 0; i < slides.size() - 1; i++) {
            Slide a = slides.get(i);

            int m = i + 1;
            int maxScore = -1;
            for (int j = 1; j < W && i + j < slides.size(); j++) {
                Slide b = slides.get(i + j);
                int score = calculateScore(a, b);
                if (score > maxScore) {
                    maxScore = score;
                    m = i + j;
                }
            }
            if (i % 1000 == 0) {
                System.out.print("Forming SlideShow: " + (((float) i / slides.size()) * 100) + "% complete\r");
            }
            Collections.swap(slides, i + 1, m);
        }

        System.out.println("SCORE:" + calculateScore(slides));
        return slides;
    }

    public List<List<Photo>> separateHorizontalAndVertical(List<Photo> photoList) {
        List<Photo> verticalPhotos = new ArrayList<Photo>();
        List<Photo> horizontalPhotos = new ArrayList<Photo>();
        List<List<Photo>> result = new ArrayList<List<Photo>>();
        for (Photo photo : photoList) {
            if (photo.type.equals("H")) {
                horizontalPhotos.add(photo);
            } else if (photo.type.equals("V")) {
                verticalPhotos.add(photo);
            }
        }
        result.add(horizontalPhotos);
        result.add(verticalPhotos);
        return result;
    }
}
