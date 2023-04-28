package travelfeeldog.infra.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AwsS3DeleteService implements DeleteService{

    private final AmazonS3 amazonS3;
    private final S3Component component;

    @Override
    public void deleteFile(String imageUrl)
    {
        amazonS3.deleteObject(new DeleteObjectRequest(component.getBucket(), imageUrl));
    }

}
