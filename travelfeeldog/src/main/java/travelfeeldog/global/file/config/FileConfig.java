package travelfeeldog.global.file.config;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import travelfeeldog.global.file.domain.application.ImageFileHandle;
import travelfeeldog.global.file.domain.application.ImageFileService;
import travelfeeldog.infra.aws.s3.domain.application.S3ImageService;

@Configuration
public class FileConfig {
    @Bean
    public ImageFileHandle s3FileUploader(AmazonS3 amazonS3) {
        return new S3ImageService(amazonS3);
    }

    @Bean
    public ImageFileService imageFileService(ImageFileHandle imageFileHandle) {
        return new ImageFileService(imageFileHandle);
    }
}
