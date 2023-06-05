package travelfeeldog.infra.aws.s3.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AwsS3ImageServiceTest {

    @Test
    void stirng_parse_test() {
        String url = "https://tavelfeeldog.s3.ap-northeast-2.amazonaws.com/place/cccmdmmmdmdmdmdmdmdmdmdmmdmdmdm.jpg1685954555706";
        int lastIndex = url.indexOf(".com/") + 5; // Adding 5 to include ".com/"
        String parsedResult = "";
        if (lastIndex != -1 && lastIndex < url.length()) {
            parsedResult = url.substring(lastIndex);
        }
        String expect = "place/cccmdmmmdmdmdmdmdmdmdmdmmdmdmdm.jpg1685954555706";

        assertEquals(parsedResult,expect);
    }
}


