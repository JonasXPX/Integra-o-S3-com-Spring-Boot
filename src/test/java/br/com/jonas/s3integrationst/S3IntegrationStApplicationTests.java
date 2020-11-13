package br.com.jonas.s3integrationst;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class S3IntegrationStApplicationTests {

    @Test
    void textRegex() {
        String fileName = "arq.uivo-alguma.coisa definida.jpg";

        String[] finalName = fileName.split("\\.");

        Assertions.assertThat(finalName).endsWith("jpg");
    }

}
