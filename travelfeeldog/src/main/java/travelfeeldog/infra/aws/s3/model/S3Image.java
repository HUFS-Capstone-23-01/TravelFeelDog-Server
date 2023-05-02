package travelfeeldog.infra.aws.s3.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.Id;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter @Setter
@NoArgsConstructor // using Lombok annotation for constructor
public class S3Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private MultipartFile file; // not in database

    private String fileName;
    private String folderName;

    private String fileType;

    private Long fileSize;

    private String fileUrl;

    public S3Image(String folderName, MultipartFile file) {
        this.folderName = folderName;
        this.file = file;
        this.fileName = file.getOriginalFilename();
        this.fileType = file.getContentType();
        this.fileSize = file.getSize();
    }
}
