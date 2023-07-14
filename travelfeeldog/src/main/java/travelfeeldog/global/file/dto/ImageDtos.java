package travelfeeldog.global.file.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelfeeldog.global.file.domain.model.ImageFile;

public class ImageDtos {
    @Data
    @NoArgsConstructor
    public static class ImageDto {
        private String fileName;
        private String fileType;
        private Long fileSize;
        private String folderName;

        public ImageDto(ImageFile image) {
            this.folderName = image.getFolderName();
            this.fileName = image.getFileName();
            this.fileType = image.getFileType();
            this.fileSize = image.getFileSize();
        }
    }
    @Data
    @NoArgsConstructor
    public static class ImagesResponseDto {
        private String fileName;
        public ImagesResponseDto(ImageDto imageDto) {
            this.fileName = imageDto.getFileName();
        }
    }
}
