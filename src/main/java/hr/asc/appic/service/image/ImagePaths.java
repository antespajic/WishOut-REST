package hr.asc.appic.service.image;

import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;
import java.util.List;
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

    public static String accessUrl(User u) {
        return accessRoot + u.getProfilePicture();
    }

    public static String destinationUrl(User u) {
        return userDir + u.getId() + "_" + new Date().getTime();
    }

    public static List<String> accessUrls(Wish wish) {
        return accessUrlsFromList(wish.getPictures());
    }

    public static String destinationUrls(Wish wish) {
        return wishDir + wish.getId() + "_" + new Date().getTime();
    }

    public static List<String> accessUrls(Story story) {
        return accessUrlsFromList(story.getPictures());
    }

    public static String destinationUrls(Story story) {
        return storyDir + story.getId() + "_" + new Date().getTime();
    }

    public static String accessUrlFromDestinationUrl(String url) {
        return url.substring(accessRoot.length());
    }

    private static List<String> accessUrlsFromList(List<String> paths) {
        return paths
                .stream()
                .map(p -> accessRoot + p)
                .collect(Collectors.toList());
    }
}
