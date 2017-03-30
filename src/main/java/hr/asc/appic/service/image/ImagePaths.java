package hr.asc.appic.service.image;

import hr.asc.appic.persistence.model.Story;
import hr.asc.appic.persistence.model.User;
import hr.asc.appic.persistence.model.Wish;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ImagePaths {

    @Value("${aws-region}")
    private String region;
    @Value("${aws-url}")
    private String url;
    @Value("${aws-bucket-image}")
    private String bucket;

    private String userDir = "user/";
    private String wishDir = "wish/";
    private String storyDir = "story/";

    public String uploadUrl(User u) {
        return userDir + u.getId() + "_" + new Date().getTime();
    }

    public String uploadUrl(Wish wish) {
        return wishDir + wish.getId() + "_" + new Date().getTime();
    }

    public String uploadUrl(Story story) {
        return storyDir + story.getId() + "_" + new Date().getTime();
    }

    public String accessUrl(String destinationUrl) {
        return region + "." + url + "/" + bucket + "/" + destinationUrl;
    }
}
