package travelfeeldog.global.file.domain.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import travelfeeldog.global.file.domain.model.ImageFile;
import travelfeeldog.global.file.dto.ImageDtos.ImageDto;
import travelfeeldog.global.file.infrastructure.GlobalImageRepository;

@Transactional
@Service
public class GlobalImageFileService {

    GlobalImageRepository globalImageRepository;

    ImageFileService imageFileService;

    @Transactional(readOnly = true)
    public ImageDto getGlobalImageFileById(Long id) {
        ImageFile imageFile = globalImageRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Image not found for id: " + id));
        return new ImageDto(imageFile);
    }

    public ImageDto uploadGlobalImageFile(MultipartFile file, String folderName) {
        String fileName = imageFileService.uploadImageFile(file,folderName);
        ImageFile imageFile = new ImageFile(file,fileName,folderName);
        globalImageRepository.save(imageFile);
        return new ImageDto(imageFile);
    }

    public List<ImageDto> uploadGlobalImageFiles(MultipartFile[] files, String folderName) {
        List<ImageDto> uploadImageFiles= imageFileService.uploadImageFiles(files,folderName);
        List<ImageFile> uploadedFiles = uploadImageFiles
                    .stream()
                    .map(ImageFile::new)
                    .toList();
        globalImageRepository.saveAll(uploadedFiles);
        return uploadImageFiles;
    }
}
