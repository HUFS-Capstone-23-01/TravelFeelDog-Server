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
    public ImageDto getImageById(Long id) {
        ImageFile imageFile = globalImageRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Image not found for id: " + id));
        return new ImageDto(imageFile);
    }

    public ImageDto uploadImageFile(MultipartFile file, String folderName) {
        ImageFile imageFile = imageFileService.uploadImageFile(file,folderName);
        globalImageRepository.save(imageFile);
        return new ImageDto(imageFile);
    }

    public List<ImageDto> uploadImageFiles(MultipartFile[] files, String folderName) {
        return imageFileService.uploadImageFiles(files,folderName)
            .stream()
            .map(ImageDto::new)
            .toList();
    }
}
