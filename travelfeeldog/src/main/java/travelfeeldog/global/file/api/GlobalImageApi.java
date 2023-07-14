package travelfeeldog.global.file.api;

import java.io.IOException;
import java.util.List;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import travelfeeldog.global.common.dto.ApiResponse;
import travelfeeldog.global.file.domain.application.GlobalImageFileService;

import travelfeeldog.global.file.dto.ImageDtos.ImageDto;

@RestController
@RequestMapping("/file")
@AllArgsConstructor
public class GlobalImageApi {

    private final GlobalImageFileService globalImageFileService;

    @PostMapping(value = "/image/{folderName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<ImageDto> uploadImage(@PathVariable String folderName, @RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.success(
                HttpStatus.CREATED,
                globalImageFileService.uploadImageFile(file, folderName));
    }

    @PostMapping(value = "/images/{folderName}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<List<ImageDto>> uploadImages(@PathVariable String folderName, @RequestParam("files") MultipartFile[] files) {
        return ApiResponse.success(
                HttpStatus.CREATED,
                globalImageFileService.uploadImageFiles(files, folderName));
    }
}
