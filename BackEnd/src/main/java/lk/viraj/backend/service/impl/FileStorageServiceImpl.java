package lk.viraj.backend.service.impl;

import lk.viraj.backend.service.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String DEFAULT_DIRECTORY = "C:\\Users\\asus\\Desktop\\Digital Art Gallery\\BackEnd\\src\\main\\resources\\static\\";
    private static final String DEFAULT_IMAGE_DIRECTORY = "C:\\Users\\asus\\Desktop\\Digital Art Gallery\\BackEnd\\src\\main\\resources\\static\\images\\";
    private static final String ITEM_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "items\\";
    private static final String PROFILE_UPLOAD_DIR = DEFAULT_IMAGE_DIRECTORY + "users\\";

    static {
        createIfNotExistDirectory(DEFAULT_DIRECTORY);
        createIfNotExistDirectory(DEFAULT_IMAGE_DIRECTORY);
        createIfNotExistDirectory(ITEM_UPLOAD_DIR);
        createIfNotExistDirectory(PROFILE_UPLOAD_DIR);
    }

    @Override
    public String saveItemImage(MultipartFile image) {
        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(ITEM_UPLOAD_DIR + fileName);

        // Save the file
        try {
            image.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return filePath.toString();
    }

    static void createIfNotExistDirectory(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // ✅ Create directory if it doesn’t exist
        }
    }
}
