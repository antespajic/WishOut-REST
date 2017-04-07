package hr.asc.appic.service.image;

import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImagePaths {

    private String userDir;
    private String wishDir;
    private String storyDir;
    private String accessRoot;

    public ImagePaths(@Value("${aws-region}") String region,
                      @Value("${aws-url}") String url,
                      @Value("${aws-bucket-image}") String bucket,
                      @Value("${aws-image-user-dir}") String userDir,
                      @Value("${aws-image-wish-dir}") String wishDir,
                      @Value("${aws-image-story-dir}") String storyDir) {
        this.userDir = userDir;
        this.wishDir = wishDir;
        this.storyDir = storyDir;

        accessRoot = region + "." + url + "/" + bucket + "/";
    }

    public String accessUrl(String resourceUrl) {
        return accessRoot + resourceUrl;
    }

    // ======================================== User ======================================== //

    public String uploadUrl(User u) {
        return userDir + "/" + u.getId() + "_" + new Date().getTime();
    }

    public String deleteUrl(User u) {
        if (u.getProfilePicture() != null) {
            return u.getProfilePicture().substring(accessRoot.length());
        }
        throw new IllegalArgumentException("Image for user is not present");
    }

    // ======================================== Wish ======================================== //

    public String uploadUrl(Wish wish) {
        return wishDir + "/" + wish.getId() + "_" + new Date().getTime();
    }

    public String accessUrl(Wish wish, String imageName) {
        String fullImagePath = accessRoot + wishDir + "/" + imageName;
        if (wish.getPictures().contains(fullImagePath)) {
            return fullImagePath;
        }
        throw new IllegalArgumentException("Image for wish is not present: " + imageName);
    }

    public String deleteUrl(Wish wish, String imageName) {
        return accessUrl(wish, imageName).substring(accessRoot.length());
    }

    // ======================================== Story ======================================== //

    public String uploadUrl(Story story) {
        return storyDir + "/" + story.getId() + "_" + new Date().getTime();
    }

    public String accessUrl(Story story, String imageName) {
        String fullImagePath = accessRoot + storyDir + "/" + imageName;
        if (story.getPictures().contains(fullImagePath)) {
            return fullImagePath;
        }
        throw new IllegalArgumentException("Image for story is not present: " + imageName);
    }

    public String deleteUrl(Story story, String imageName) {
        return accessUrl(story, imageName).substring(accessRoot.length());
    }
}
