package hr.asc.appic.service.image;

import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

public class ImagePaths {

    @Value("${aws-region}")
    private static String region;
    @Value("${aws-url}")
    private static String url;
    @Value("${aws-bucket-image}")
    private static String bucket;

    private static final String userDir = "user/";
    private static final String wishDir = "wish/";
    private static final String storyDir = "story/";
    private static final String accessRoot = region + "." + url + "/" + bucket + "/";

    public static String originForUser(User u) {
        return accessRoot + u.getProfilePicture();
    }

    public static String destinationForUser(User u) {
        return userDir + u.getId() + "_" + new Date().getTime();
    }

    public static String[] originsForWish(Wish wish) {
        return accessRootsFromList(wish.getPictures());
    }

    public static String destinationForWish(Wish wish) {
        return wishDir + wish.getId() + "_" + new Date().getTime();
    }

    public static String[] originsForStory(Story story) {
        return accessRootsFromList(story.getPictures());
    }

    public static String destinationForStory(Story story) {
        return storyDir + story.getId() + "_" + new Date().getTime();
    }

    private static String[] accessRootsFromList(Set<String> paths) {
        paths = paths
                .stream()
                .map(p -> accessRoot + p)
                .collect(Collectors.toSet());
        return paths.toArray(new String[]{});
    }
}
