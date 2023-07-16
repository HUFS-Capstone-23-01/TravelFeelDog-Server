package travelfeeldog.global.file.domain.application;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import travelfeeldog.global.file.domain.model.ImageFile;

public interface ImageFileHandle {
    ImageFile uploadImageFile(MultipartFile file, String folderName);
    List<ImageFile> uploadImageFiles(MultipartFile[] file, String folderName);
    void deleteImage(String fileName, String folderName);
}
