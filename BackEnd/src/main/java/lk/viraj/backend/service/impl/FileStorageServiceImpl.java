package lk.viraj.backend.service.impl;

import lk.viraj.backend.service.FileStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private static final String ITEMS_DIR = "items\\";
    private static final String USERS_DIR = "users\\";

    private static final String FRONTEND_DIRECTORY = "assets\\sys\\";
    private static final String DEFAULT_DIRECTORY = "C:\\Users\\asus\\Desktop\\Digital Art Gallery\\FrontEnd\\" + FRONTEND_DIRECTORY;
    private static final String ITEM_UPLOAD_DIR = DEFAULT_DIRECTORY + ITEMS_DIR;
    private static final String USER_PROFILE_UPLOAD_DIR = DEFAULT_DIRECTORY + USERS_DIR;

    static {
        createIfNotExistDirectory(ITEM_UPLOAD_DIR);
        createIfNotExistDirectory(USER_PROFILE_UPLOAD_DIR);
    }

    @Override
    public String saveItemImage(MultipartFile image) {
        return saveImage(ITEM_UPLOAD_DIR, ITEMS_DIR, image);
    }

    @Override
    public String saveUserProfileImage(MultipartFile image) {
        return saveImage(USER_PROFILE_UPLOAD_DIR, USERS_DIR, image);
    }

    private String saveImage(String savingPath, String dir, MultipartFile image) {
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(savingPath + fileName);

        try {
            //save the image
            image.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return FRONTEND_DIRECTORY + dir + fileName;
    }

    static void createIfNotExistDirectory(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create directory if it doesn’t exist
        }
    }
}
