package travelfeeldog.infra.aws.s3.service;

import static org.junit.jupiter.api.Assertions.*;

import com.amazonaws.services.s3.AmazonS3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import travelfeeldog.infra.aws.s3.dao.AwsS3ImageRepository;

class AwsS3ImageServiceTest {
    private AmazonS3 amazonS3;
    private  AwsS3ImageRepository imageRepository;

    private String givenUrl = "https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/place/cccmdmmmdmdmdmdmdmdmdmdmmdmdmdm.jpg1685954555706";
    @Test
    void stirng_parse_test() {
        int lastIndex = givenUrl.indexOf(".com/") + 5; // Adding 5 to include ".com/"
        String parsedResult = "";
        if (lastIndex != -1 && lastIndex < givenUrl.length()) {
            parsedResult = givenUrl.substring(lastIndex);
        }
        String expect = "place/cccmdmmmdmdmdmdmdmdmdmdmmdmdmdm.jpg1685954555706";

        assertEquals(parsedResult,expect);
    }
    @Test
    void full_url_cut_test(){
        String expect = "place/cccmdmmmdmdmdmdmdmdmdmdmmdmdmdm.jpg1685954555706";
        AwsS3ImageService awsS3ImageService = new AwsS3ImageService(amazonS3,imageRepository);
        assertEquals(awsS3ImageService.cutFullFileUrlIntoNameOnly(givenUrl),expect);
    }
}


