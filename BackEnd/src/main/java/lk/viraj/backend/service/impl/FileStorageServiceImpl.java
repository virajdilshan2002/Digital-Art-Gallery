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
        return saveImage(ITEM_UPLOAD_DIR, image);
    }

    @Override
    public String saveUserProfileImage(MultipartFile image) {
        return saveImage(PROFILE_UPLOAD_DIR, image);
    }

    private String saveImage(String path, MultipartFile image) {
        // Generate a unique filename
        String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
        Path filePath = Paths.get(path + fileName);

        try {
            //save the image
            image.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePath.toString();
    }

    @Override
    public Resource getProfileImage(String imagePath) {
        try {
            Path filePath = Paths.get(imagePath);
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Image not found: " + imagePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error loading image: " + imagePath, e);
        }
    }


    static void createIfNotExistDirectory(String directory) {
        File uploadDir = new File(directory);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Create directory if it doesn’t exist
        }
    }
}
