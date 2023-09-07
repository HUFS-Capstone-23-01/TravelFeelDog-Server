package travelfeeldog.global.file.domain.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;
import travelfeeldog.global.file.dto.ImageDtos.ImageDto;

@Entity
@Getter
@NoArgsConstructor
public class ImageFile {
    @Id
    Long id;
    @Transient
    protected MultipartFile file; // not in database
    protected String fileName;
    protected String fileType;
    protected Long fileSize;
    protected String folderName;

    public ImageFile(MultipartFile file, String fileName, String folderName) {
        this.fileName = fileName;
        this.fileType = file.getContentType();
        this.fileSize = file.getSize();
        this.folderName = folderName;
    }
    public ImageFile(ImageDto imageDto){
        this.fileName = imageDto.getFileName();
        this.fileType = imageDto.getFileType();
        this.fileSize = imageDto.getFileSize();
        this.folderName = imageDto.getFolderName();
    }
}
