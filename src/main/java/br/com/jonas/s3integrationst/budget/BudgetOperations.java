package br.com.jonas.s3integrationst.budget;

import br.com.jonas.s3integrationst.config.S3Configuration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.PutObjectResult;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class BudgetOperations {

    private final AmazonS3 awsS3;
    private final S3Configuration s3Configuration;

    public Bucket createBudget(String budgetName) {
        if(awsS3.doesBucketExistV2(budgetName)) {
            throw new RuntimeException("cant create budget if already exists");
        }

        return awsS3.createBucket(budgetName);
    }

    public PutObjectResult saveFile(MultipartFile file) {
        File tempFile = null;
        try {
            String fileName = generateFileName(file);
            tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);

            file.transferTo(tempFile);

            return awsS3.putObject(s3Configuration.getBucket(), "images/" + fileName, tempFile);
        } catch (Exception e) {
            throw new RuntimeException("não foi possível completar a ação");
        } finally {
            if(tempFile != null) {
                try {
                    Files.delete(tempFile.toPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String generateFileName(MultipartFile file) throws IOException {
        String[] formatSplit = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String md5Hex = DigestUtils.md5Hex(file.getBytes());
        String formatName = formatSplit[formatSplit.length - 1];
        return md5Hex + "." + formatName;
    }
}
