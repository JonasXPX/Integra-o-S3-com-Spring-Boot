package br.com.jonas.s3integrationst.file;

import br.com.jonas.s3integrationst.budget.BucketService;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    private BucketService bucketService;

    @PostMapping
    public String uploadFile(@RequestParam MultipartFile file) {
        PutObjectResult result = bucketService.saveFile(file);
        return result.getContentMd5();
    }
}
