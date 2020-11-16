package br.com.jonas.s3integrationst;

import br.com.jonas.s3integrationst.budget.BucketService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.assertj.core.api.Assertions;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class S3IntegrationStApplicationTests {

    @Autowired
    private BucketService bucketService;

    @MockBean
    private AmazonS3 mockedAmazonS3;

    @Test
    void textRegex() {
        String fileName = "arq.uivo-alguma.coisa definida.jpg";

        String[] finalName = fileName.split("\\.");

        assertThat(finalName).endsWith("jpg");
    }

    @Test
    void dateToString() {
        String dateString = DateTime.now().toString("YYYY_MM_dd_hh_mm_ss_sss");
        System.out.println(dateString);
        assertThat(dateString).isNotEmpty();
    }

    @Test
    void shouldSaveFile() {
        PutObjectResult result = new PutObjectResult();
        result.setContentMd5("test");

        when(mockedAmazonS3.putObject(anyString(), anyString(), (File) any())).thenReturn(result);

        MockMultipartFile mockMultipartFile = new MockMultipartFile("testefile.jpeg", (byte[]) null);
        PutObjectResult saveFile = bucketService.saveFile(mockMultipartFile);

        assertThat(saveFile).isNotNull();
        assertThat(saveFile.getContentMd5()).isEqualTo("test");
    }

}
