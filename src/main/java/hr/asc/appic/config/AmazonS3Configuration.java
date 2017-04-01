package hr.asc.appic.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.S3ClientOptions;
import hr.asc.appic.service.image.AmazonS3ImageService;
import hr.asc.appic.service.image.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AmazonS3Configuration {

    @Value("${aws-bucket-image}")
    private String bucket;
    @Value("${aws-access-key}")
    private String accessKey;
    @Value("${aws-access-secret}")
    private String accessSecret;

    @Bean
    public AmazonS3 amazonS3() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, accessSecret);
        AmazonS3 amazonS3 = new AmazonS3Client(credentials);
        amazonS3.setS3ClientOptions(
                S3ClientOptions.builder()
                        .setPathStyleAccess(true).disableChunkedEncoding().build());
        log.info("Amazon S3 client connection established. Using bucket: " + bucket);
        return amazonS3;
    }

    @Bean
    public ImageService imageService() {
        return new AmazonS3ImageService();
    }
}
