package br.com.jonas.s3integrationst.file;

import br.com.jonas.s3integrationst.budget.BucketService;
import com.amazonaws.services.s3.model.PutObjectResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("file")
@CrossOrigin
public class FileController {

    @Autowired
    private BucketService bucketService;

    @PostMapping
    public String uploadFile(@RequestParam MultipartFile file) {
        return bucketService.saveFile(file);
    }

    @GetMapping(
            value = {"/", ""},
            produces = {
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE
            }
    )
    public ResponseEntity<byte[]> findImage(@RequestParam("key") String id) throws IOException {
        return ResponseEntity.ok(bucketService.loadImage(id));
    }
}
