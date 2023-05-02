package travelfeeldog.infra.aws.s3.service;

import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import travelfeeldog.infra.aws.s3.dao.AwsS3ImageRepository;
import travelfeeldog.infra.aws.s3.dto.AwsS3ImageDtos.ImageDto;
import travelfeeldog.infra.aws.s3.model.S3Image;
@Transactional(readOnly = true)
@Service
public class AwsS3ImageService {
    private final AmazonS3 amazonS3;

    private final AwsS3ImageRepository imageRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    // Constructor injection of dependencies
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
        if (!folderExists(folderName)) {
            // Create a new folder in the S3 bucket
            createFolder(folderName);
        }
        String fileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        metadata.setContentType(file.getContentType());
        String key = folderName + "/" + fileName;
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
        if (!folderExists(folderName)) {
            // Create a new folder in the S3 bucket
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
        String fileName = file.getOriginalFilename();
        S3Image image = new S3Image();
        image.setFolderName(folderName);
        image.setFileName(fileName);
        image.setFileType(file.getContentType());
        image.setFileUrl(fileUrl);
        return image;
    }
    public ImageDto uploadImageFile(MultipartFile file, String folderName) throws IOException {
        S3Image image=uploadMakeS3Image(file,folderName);
        image = imageSave(image);
        return new ImageDto(image);
    }
    @Transactional
    public S3Image imageSave(S3Image image){
        return imageRepository.save(image);
    }
    @Transactional
    public List<ImageDto> uploadImageFiles(MultipartFile[] files, String folderName) {
        if (!folderExists(folderName)) {
            // Create a new folder in the S3 bucket
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

    // Delete image file from S3 bucket and object from database
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
            // Create an empty input stream
            stream = new ByteArrayInputStream(new byte[0]);
            // Set content type to directory
            metadata.setContentType("application/x-directory");

            // Set folder name as key
            String key = folderName + "/";

            // Put object with empty content to create folder
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
    private boolean folderExists(String folderName) {
        List<S3ObjectSummary> objects = amazonS3.listObjects(bucketName, folderName).getObjectSummaries();
        return objects.stream().anyMatch(s -> s.getKey().startsWith(folderName + "/"));
    }
}
