package br.com.jonas.s3integrationst;

import br.com.jonas.s3integrationst.config.S3Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class S3IntegrationStApplication {

    private final S3Configuration s3Configuration;

    public static void main(String[] args) {
        SpringApplication.run(S3IntegrationStApplication.class, args);
    }

    @Bean
    public AmazonS3 buildAmazonS3Configurations() {
        AWSCredentials credentials = new BasicAWSCredentials(
                s3Configuration.getClientKey(),
                s3Configuration.getSecretKey()
        );

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }


    @Bean
    public Bucket initBucket(@Autowired AmazonS3 amazonS3) {
        if(amazonS3.doesBucketExistV2(s3Configuration.getBucket())) {
            return getBucket(amazonS3);
        } else {
            return createBucket(amazonS3);
        }
    }


    private Bucket getBucket(AmazonS3 amazonS3) {
        return amazonS3.listBuckets().stream()
                .filter(bucket -> bucket.getName().equals(s3Configuration.getBucket()))
                .findFirst()
                .orElseThrow();
    }

    private Bucket createBucket(AmazonS3 amazonS3) {
        return amazonS3.createBucket(s3Configuration.getBucket());
    }
}
