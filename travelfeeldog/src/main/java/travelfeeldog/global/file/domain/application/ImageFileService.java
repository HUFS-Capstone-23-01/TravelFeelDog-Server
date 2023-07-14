package travelfeeldog.global.file.domain.application;

import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.global.file.domain.model.ImageFile;
@Service
public class ImageFileService {

    private final ImageFileHandle imageFileHandle;
    public ImageFileService(ImageFileHandle imageFileHandle){
        this.imageFileHandle = imageFileHandle;
    }

    public ImageFile uploadImageFile(MultipartFile file, String folderName) {
        ImageFile imageFile= null;
        try {
            imageFile = imageFileHandle.uploadImageFile(file,folderName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageFile;
    }
    public List<ImageFile> uploadImageFiles(MultipartFile[] files, String folderName) {
        return imageFileHandle.uploadImageFiles(files,folderName);
    }

    public void deleteImage(String fileName,String folderName) {
        imageFileHandle.deleteImage(fileName,folderName);
    }


}
