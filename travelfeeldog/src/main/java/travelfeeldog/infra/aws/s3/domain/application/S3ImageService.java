package travelfeeldog.infra.aws.s3.domain.application;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import travelfeeldog.global.file.domain.application.ImageFileHandle;
import travelfeeldog.global.file.domain.application.ImageFileNameUtil;
import travelfeeldog.global.file.domain.model.ImageFile;
import travelfeeldog.infra.aws.s3.domain.model.S3Image;

@Service
public class S3ImageService implements ImageFileHandle {
    private final AmazonS3 amazonS3;
    private final ImageFileNameUtil imageFileNameUtil;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3ImageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.imageFileNameUtil = new ImageFileNameUtil();
    }
    @Override
    public ImageFile uploadImageFile(MultipartFile file, String folderName) throws IOException {
        String fileUrl = uploadImageFileToS3(file,folderName).toString();
        String fileName = imageFileNameUtil.cutFullFileUrlIntoNameOnly(fileUrl);
        return new S3Image(file,fileName,folderName);
    }

    @Override
    public List<ImageFile> uploadImageFiles(MultipartFile[] files, String folderName) {
        if (doesNotExistFolder(folderName)) {
            createFolder(folderName);
        }
        return Arrays.stream(files)
            .map(file -> {
                try {
                    return uploadImageFile(file, folderName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            })
            .collect(Collectors.toList());
    }
    private URL uploadImageFileToS3(MultipartFile file, String folderName) throws IOException {
        if (doesNotExistFolder(folderName)) {
            createFolder(folderName);
        }

        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        String key = folderName + "/" + fileName+imageFileNameUtil.getLocalDateTimeMilliseconds();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, key);
    }

    private void createFolder(String folderName) {
        InputStream stream = null;
        try {
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(0);
            stream = new ByteArrayInputStream(new byte[0]);
            metadata.setContentType("application/x-directory");
            String key = folderName + "/";
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, stream, metadata);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean doesNotExistFolder(String folderName) {
        return amazonS3.listObjects(bucketName, folderName)
            .getObjectSummaries()
            .stream()
            .noneMatch(s -> s.getKey().startsWith(folderName + "/"));
    }
    @Override
    public void deleteImage(String fileName, String folderName) {
        String key = folderName + "/" + fileName;
        amazonS3.deleteObject(bucketName, key);
    }
}
