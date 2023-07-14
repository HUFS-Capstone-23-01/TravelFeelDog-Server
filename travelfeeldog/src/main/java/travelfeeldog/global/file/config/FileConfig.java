package travelfeeldog.global.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import travelfeeldog.global.file.domain.application.ImageFileHandle;

import travelfeeldog.infra.aws.s3.domain.application.S3ImageService;
@Configuration
public class FileConfig {

    private final S3ImageService s3ImageService;

    public FileConfig(S3ImageService s3ImageService) {
        this.s3ImageService = s3ImageService;
    }

    @Bean
    public ImageFileHandle imageFileHandle() {
            return s3ImageService;
    }
}
