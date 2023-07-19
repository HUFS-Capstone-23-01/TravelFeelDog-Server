package travelfeeldog.infra.aws.s3.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.global.file.domain.model.ImageFile;

@Getter
@NoArgsConstructor
public class S3Image extends ImageFile {

    public S3Image(MultipartFile file,String fileName,String folderName) {
        this.file = file;
        this.fileName = fileName;
        this.fileType = file.getContentType();
        this.fileSize = file.getSize();
        this.folderName = folderName;
    }
}
