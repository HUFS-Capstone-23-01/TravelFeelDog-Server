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

    private final static int EMPTY_FILE_SIZE = 0;
    private final AmazonS3 amazonS3;
    private final ImageFileNameUtil imageFileNameUtil;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public S3ImageService(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
        this.imageFileNameUtil = new ImageFileNameUtil();
    }

    @Override
    public void deleteImage(String fileName, String folderName) {
        String key = folderName + "/" + fileName;
        amazonS3.deleteObject(bucketName, key);
    }

    @Override
    public ImageFile uploadImageFile(MultipartFile file, String folderName) {
        String fileUrl = sendImageFileToS3(file, folderName).toString();
        String fileName = imageFileNameUtil.cutFullFileUrlIntoNameOnly(fileUrl);
        return new S3Image(file, fileName, folderName);
    }

    @Override
    public List<ImageFile> uploadImageFiles(MultipartFile[] files, String folderName) {
        return Arrays.stream(files)
            .map(file -> uploadImageFile(file, folderName))
            .collect(Collectors.toList());
    }

    private URL sendImageFileToS3(MultipartFile file, String folderName) {
        if (checkFolderExistence(folderName)) {
            makeFolder(folderName);
        }
        String key = constructObjectForS3(file, folderName);
        return amazonS3.getUrl(bucketName, key);
    }

    private boolean checkFolderExistence(String folderName) {
        return amazonS3.listObjects(bucketName, folderName)
            .getObjectSummaries()
            .stream()
            .noneMatch(s -> s.getKey().startsWith(folderName + "/"));
    }

    private void makeFolder(String folderName) {
        InputStream inputStream = new ByteArrayInputStream(new byte[EMPTY_FILE_SIZE]);
        String key = folderName + "/";
        uploadObjectToS3(key, inputStream, EMPTY_FILE_SIZE, "application/x-directory");
    }

    private String constructObjectForS3(MultipartFile file, String folderName) {
        String fileName = file.getOriginalFilename();
        String key = folderName + "/" + fileName + imageFileNameUtil.getLocalDateTimeMilliseconds();
        try {
            InputStream inputStream = file.getInputStream();
            return uploadObjectToS3(key, inputStream, file.getSize(), file.getContentType());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String uploadObjectToS3(String key, InputStream inputStream, long size,
        String contentType) {
        ObjectMetadata metadata = createS3ObjectMetadata(size, contentType);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,
            metadata);
        amazonS3.putObject(putObjectRequest);
        return key;
    }

    private ObjectMetadata createS3ObjectMetadata(long size, String contentType) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(size);
        metadata.setContentType(contentType);
        return metadata;
    }
}
