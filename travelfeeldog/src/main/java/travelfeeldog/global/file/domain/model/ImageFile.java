package travelfeeldog.global.file.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
public class ImageFile {
    @Id
    Long id;
    @Transient
    protected MultipartFile file; // not in database
    protected String fileName;
    protected String fileType;
    protected Long fileSize;
    protected String folderName;
}
