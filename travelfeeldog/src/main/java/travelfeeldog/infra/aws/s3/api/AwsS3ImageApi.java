package travelfeeldog.infra.aws.s3.api;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.infra.aws.s3.dto.AwsS3ImageDtos.ImageDto;
import travelfeeldog.infra.aws.s3.service.AwsS3ImageService;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class AwsS3ImageApi {

    private final AwsS3ImageService awsS3ImageService;

    @PostMapping(value="/image/{folderName}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<ImageDto> uploadImage(@PathVariable String folderName,
                                                @RequestParam("file") MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(awsS3ImageService.uploadImageFile(file,folderName));
    }

    @PostMapping(value="/images/{folderName}",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<List<ImageDto>> uploadImages(@PathVariable String folderName,
                                                       @RequestParam("files") MultipartFile[] files) {
        List<ImageDto> imageDtos = awsS3ImageService.uploadImageFiles(files, folderName);
        return ResponseEntity.status(HttpStatus.CREATED).body(imageDtos);
    }

}

