package br.com.jonas.s3integrationst.budget;

import br.com.jonas.s3integrationst.budget.exception.BucketException;
import br.com.jonas.s3integrationst.config.S3Configuration;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BucketService {

    private final AmazonS3 awsS3;
    private final S3Configuration s3Configuration;

    public String saveFile(MultipartFile file) throws BucketException {
        File tempFile = null;
        try {
            String fileName = generateFileName(file);
            tempFile = new File(System.getProperty("java.io.tmpdir"), fileName);

            file.transferTo(tempFile);

            awsS3.putObject(s3Configuration.getBucket(), "images/" + fileName, tempFile);

            return "images/" + fileName;
        } catch (Exception e) {
            throw new BucketException("não foi possível salvar");
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

    public byte[] loadImage(String id) throws IOException {
        S3Object object = awsS3.getObject(s3Configuration.getBucket(), id);
        return object.getObjectContent().readAllBytes();
    }

    private String generateFileName(MultipartFile file) throws IOException {
        String[] formatSplit = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String fileName = DateTime.now().toString("YYYY_MM_dd_hh_mm_ss_sss");
        String formatName = formatSplit[formatSplit.length - 1];
        return fileName + "." + formatName;
    }
}
