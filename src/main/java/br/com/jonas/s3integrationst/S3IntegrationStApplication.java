package br.com.jonas.s3integrationst;

import br.com.jonas.s3integrationst.budget.BudgetOperations;
import br.com.jonas.s3integrationst.config.S3Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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
}
