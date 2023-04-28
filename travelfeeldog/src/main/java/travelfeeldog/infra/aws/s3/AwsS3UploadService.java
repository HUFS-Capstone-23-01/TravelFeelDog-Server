package travelfeeldog.infra.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.DeleteObjectRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
@RequiredArgsConstructor
public class AwsS3UploadService implements UploadService {

    private final AmazonS3 amazonS3;
    private final S3Component component;

    @Override
    public void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName) {
        amazonS3.putObject(new PutObjectRequest(component.getBucket(), fileName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
    }

    @Override
    public String getFileUrl(String fileName) {
        return amazonS3.getUrl(component.getBucket(), fileName).toString();
    }

//    @Override
//    public String deleteObject(String fileName) {
//        System.out.println("result:"+ fileName);
//        amazonS3.deleteObject(component.getBucket(), fileName);
//        return fileName;
//    }


    // @Async annotation ensures that the method is executed in a different background thread
    // but not consume the main thread.
    @Async @Override
    public String deleteObject(String fileName) {
        DeleteObjectRequest deleteObjectRequest = new DeleteObjectRequest(component.getBucket(),fileName);
        amazonS3.deleteObject(deleteObjectRequest);
        return fileName;
    }

}
