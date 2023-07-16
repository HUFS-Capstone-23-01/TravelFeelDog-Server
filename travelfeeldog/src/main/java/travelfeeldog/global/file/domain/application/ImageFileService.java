package travelfeeldog.global.file.domain.application;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.global.file.domain.model.ImageFile;
import travelfeeldog.global.file.dto.ImageDtos.ImageDto;

@Service
public class ImageFileService {

    private final ImageFileHandle imageFileHandle;
    public ImageFileService(ImageFileHandle imageFileHandle){
        this.imageFileHandle = imageFileHandle;
    }

    public String uploadImageFile(MultipartFile file, String folderName) {
        return imageFileHandle.uploadImageFile(file,folderName).getFileName();
    }
    public List<ImageDto> uploadImageFiles(MultipartFile[] files, String folderName) {
        return imageFileHandle.uploadImageFiles(files,folderName).stream().map(ImageDto::new).toList();
    }

    public void deleteImage(String fileName,String folderName) {
        imageFileHandle.deleteImage(fileName,folderName);
    }


}
