package travelfeeldog.global.file.domain.application;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class ImageFileNameUtil {

    public String cutFullFileUrlIntoNameOnly(String fileUrl) {
        int lastIndex = fileUrl.indexOf(".com/") + 5;
        if (lastIndex != -1 && lastIndex < fileUrl.length()) {
            return fileUrl.substring(lastIndex);
        }
        else{
            throw new IllegalArgumentException("fileUrl is Error");
        }
    }
    public String getLocalDateTimeMilliseconds() {
        LocalDateTime localDateTime = LocalDateTime.now();
        return Long.toString(localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
    }
}
