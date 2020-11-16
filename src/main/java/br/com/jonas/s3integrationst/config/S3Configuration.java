package br.com.jonas.s3integrationst.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class S3Configuration {

    @Value("${amazon.s3.secret_key}")
    private String secretKey;

    @Value("${amazon.s3.client_key}")
    private String clientKey;

    @Value("${amazon.s3.bucket}")
    private String bucket;

    @Value("${amazon.s3.endpoint}")
    private String endpoint;

}
