package travelfeeldog.global.file.domain.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ImageFileNameUtilTest {
    private final ImageFileNameUtil imageFileNameUtil = new ImageFileNameUtil();

    @Test
    void cutFullFileUrlIntoNameOnly_argument_test() {
        String givenFileUrl = "aba";
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> {imageFileNameUtil.cutFullFileUrlIntoNameOnly(givenFileUrl);});
    }

    @Test
    void getLocalDateTimeMilliseconds() {
    }

    @Test
    void image_url_parser(){
        String fileUrl = "aba.com/a.jpg";
        int lastIndex = fileUrl.indexOf(".com/") + 5;
        if (lastIndex != -1 && lastIndex < fileUrl.length()) {
            String tag = fileUrl.substring(lastIndex);
            String expectTag = "a.jpg";
            Assertions.assertEquals(tag,expectTag);
        }
        else{
            throw new IllegalArgumentException("fileUrl is Error");
        }
    }
}
