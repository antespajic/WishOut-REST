package hr.asc.appic.service.image;

import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

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

    public static String uploadUrl(User u) {
        return userDir + u.getId() + "_" + new Date().getTime();
    }

    public static String uploadUrl(Wish wish) {
        return wishDir + wish.getId() + "_" + new Date().getTime();
    }

    public static String uploadUrl(Story story) {
        return storyDir + story.getId() + "_" + new Date().getTime();
    }

    public static String accessUrl(String destinationUrl) {
        return accessRoot + destinationUrl;
    }
}
