package travelfeeldog.infra.aws.s3.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.ByteArrayInputStream;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import travelfeeldog.infra.aws.s3.model.S3Image;
import travelfeeldog.infra.aws.s3.dao.AwsS3ImageRepository;
import travelfeeldog.infra.aws.s3.dto.AwsS3ImageDtos.ImageDto;

@Transactional(readOnly = true)
@Service
public class AwsS3ImageService {
    private final AmazonS3 amazonS3;
    private final AwsS3ImageRepository imageRepository;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    public AwsS3ImageService(AmazonS3 amazonS3, AwsS3ImageRepository imageRepository) {
        this.amazonS3 = amazonS3;
        this.imageRepository = imageRepository;
    }
    /**
     *
     * @param file
     * @param folderName
     * @return s3ImageURL
     * @throws IOException
     */
    public String uploadImageOnly(MultipartFile file, String folderName) throws IOException {
        if (doesNotExistFolder(folderName)) {
            createFolder(folderName);
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        long milliseconds = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        String key = folderName + "/" + fileName+milliseconds;
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, metadata);
        amazonS3.putObject(putObjectRequest);
        return amazonS3.getUrl(bucketName, key).toString();
    }
    /**
     *
     * @param files
     * @param folderName
     * @return List<ImageDto>
     * @throws IOException
     */
    public List<ImageDto> uploadImagesOnly(MultipartFile[] files, String folderName) {
        if (doesNotExistFolder(folderName)) {
            createFolder(folderName);
        }
        return Arrays.stream(files)
                .map(file -> {
                    try {
                        return uploadSingleImage(file, folderName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());
    }
    public ImageDto uploadSingleImage(MultipartFile file, String folderName) throws IOException {
        return new ImageDto(uploadMakeS3Image(file,folderName));
    }
    private S3Image uploadMakeS3Image(MultipartFile file, String folderName) throws IOException {
        String fileUrl = uploadImageOnly(file,folderName);
        String parsedFileUrl = parseUrlImageUrl(fileUrl);
        S3Image image = new S3Image();
        image.setFolderName(folderName);
        image.setFileName(parsedFileUrl);
        image.setFileSize(file.getSize());
        image.setFileType(file.getContentType());
        image.setFileUrl(fileUrl);
        return image;
    }

    private String parseUrlImageUrl(String fileUrl) {
        int lastIndex = fileUrl.indexOf(".com/") + 5; // Adding 5 to include ".com/"
        String parsedResult = "";
        if (lastIndex != -1 && lastIndex < fileUrl.length()) {
            parsedResult = fileUrl.substring(lastIndex);
        }
        else{
            throw new RuntimeException("fileUrl is Error");
        }
        return parsedResult;
    }

    @Transactional
    public ImageDto uploadImageFile(MultipartFile file, String folderName) throws IOException {
        S3Image image=uploadMakeS3Image(file,folderName);
        image = imageSave(image);
        return new ImageDto(image);
    }
    @Transactional
    public S3Image imageSave(S3Image image) {
        return imageRepository.save(image);
    }
    @Transactional
    public List<ImageDto> uploadImageFiles(MultipartFile[] files, String folderName) {
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
    @Transactional
    public void deleteImage(Long id) {
        S3Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found for id: " + id));
        amazonS3.deleteObject(bucketName, image.getFileName());
        imageRepository.delete(image);
    }
    public ImageDto getImageById(Long id) {
        S3Image image = imageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Image not found for id: " + id));
        return new ImageDto(image);
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
}
